package c.cx900;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.widget.*;
import android.app.*;
import android.os.*;
public class sy extends Activity
{
	TextView t;EditText e;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);LinearLayout l=new LinearLayout(this);
		setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(t=new TextView(this));
		l.addView(e=new EditText(this));
		e.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			public boolean onEditorAction(TextView tv,int i,KeyEvent k)
			{
				t.setText(tv.getText());
				return true;
			}
		});
	}
}