package c.cx900;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

public class ac11 extends Activity implements View.OnClickListener
{
	Button b,b2;ImageView i;String f,f2;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(b=new Button(this));b.setText("拍照");b.setOnClickListener(this);
		l.addView(b2=new Button(this));b2.setText("查看照片");b2.setOnClickListener(this);
		l.addView(i=new ImageView(this),-1,-1);
		new File(f=getExternalFilesDir("")+"/照片/").mkdir();
		
		

		
		
		
	}
	public void onClick(View v)
	{
		if(v==b)
		{
			startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT,FileProvider.getUriForFile(this
					,"ccx900",new File(f2=f+DateFormat.format("yyyy.MM.dd HH.mm.ss",Calendar.getInstance())+".png"))),0);
		}
		else if(v==b2)
		{
			ac2.f=f;startActivity(new Intent(this,ac2.class));
		}
	}
	protected void onActivityResult(int requestCode, int resultCode,Intent data)
	{
		i.setImageBitmap(BitmapFactory.decodeFile(f2));
	}
}