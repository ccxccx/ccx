package c.cx900;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.widget.*;
import android.app.*;
import android.os.*;
public class sy extends Activity implements View.OnClickListener
{
	TextView t;EditText e;Button b,b2;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//LinearLayout l=new LinearLayout(this);r.addView(l);l.setOrientation(LinearLayout.VERTICAL);
		//l.addView(t=new TextView(this));
		/*l.addView(e=new EditText(this));
		e.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			public boolean onEditorAction(TextView tv,int i,KeyEvent k)
			{
				t.setText(tv.getText());
				return true;
			}
		});*/
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		r.addView(b=new Button(this),-1,-1);
		b.setText("陈超星ccx123");
		b.setTextColor(0xff0000ff);b.setOnClickListener(this);
		r.addView(t=new TextView(this),-1,-1);
		t.setBackgroundColor(0x44ff0000);
		t.setBackgroundColor(0);
		//r.addView(b2=new Button(this),-1,-1);
		//b2.setBackgroundColor(0x44ff0000);
		//b2.setBackgroundColor(0);
	}
	public void onClick(View v)
	{
		b.setText("56479");
	}
}