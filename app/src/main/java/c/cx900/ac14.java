package c.cx900;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;
public class ac14 extends Activity implements View.OnClickListener
{
	Button b,b2;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(b=new Button(this));b.setOnClickListener(this);
		l.addView(b2=new Button(this));b2.setOnClickListener(this);
		
	}
	public void onClick(View v)
	{
		if(v==b)
		{
		
		}
		else if(v==b2)
		{
		
		}
	}
}