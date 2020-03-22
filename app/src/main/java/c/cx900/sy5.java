package c.cx900;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;
public class sy5 extends Activity implements View.OnClickListener
{
	Button b;ListView l2;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(b=new Button(this));b.setText("开始录音");b.setOnClickListener(this);
		l.addView(l2=new ListView(this));
		
		
		
		
		
		
		
		
	}
	public void onClick(View v)
	{
		if(v==b)
		{
			if("开始录音".equals(b.getText()+""))
			{
				b.setText("停止录音");
				
				
			}
			else
			{
				b.setText("开始录音");
				
				
			}
		}
	}
}