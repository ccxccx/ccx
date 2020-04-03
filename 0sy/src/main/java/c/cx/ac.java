package c.cx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ac extends Activity{
    
    
    private Button b,b2;
    
    private static final int REQUEST_CODE = 1000;
    
    private int mScreenWidth;
    private int mScreenHeight;
    private int mScreenDensity;
    
    /** 是否已经开启视频录制 */
    private boolean isStarted = false;
    /** 是否为标清视频 */
    private boolean isVideoSd = true;
    /** 是否开启音频录制 */
    private boolean isAudio = true;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
        
        l.addView(b=new Button(this));b.setText("button_control");b.setText("开始录制");
        l.addView(b2=new Button(this));b2.setText("button_contro2");
    
    
        
        
        b.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isStarted) {
    
                    Intent service = new Intent(ac.this, se.class);
                    stopService(service);
                    isStarted = !isStarted;
                    b.setText("开始录制");
                } else {
                    MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                    Intent permissionIntent = mediaProjectionManager.createScreenCaptureIntent();
                    startActivityForResult(permissionIntent, REQUEST_CODE);
                }
            }
        });
    
    
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mScreenDensity = metrics.densityDpi;
    }
    
    
    
    
    
    
    
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // 获得权限，启动Service开始录制
        Intent service = new Intent(this, se.class);
        service.putExtra("code", resultCode);
        service.putExtra("data", data);
        service.putExtra("audio", isAudio);
        service.putExtra("width", mScreenWidth);
        service.putExtra("height", mScreenHeight);
        service.putExtra("density", mScreenDensity);
        service.putExtra("quality", isVideoSd);
        startService(service);
        // 已经开始屏幕录制，修改UI状态
        isStarted = !isStarted;
        b.setText("停止录制");
    }
}