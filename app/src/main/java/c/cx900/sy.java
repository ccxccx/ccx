package c.cx900;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.widget.*;
import android.app.*;
import android.os.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class sy extends Activity implements View.OnClickListener
{
	TextView t;EditText e;Button b,b2;
	protected void onCreate(Bundle bu)
	{try{
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
		
	}catch(Exception e){e.printStackTrace();}}
	public void onClick(View v)
	{
		b.setText("56479");
	}
}