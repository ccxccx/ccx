package c.cx900;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;
public class ac extends Activity
{
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);GridView g=new GridView(this);
		setContentView(g);g.setNumColumns(5);//g.setAdapter(new ba(this));
		
	}
	/*class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l7.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{EditText e,e2;Button b,b2;i(EditText a,EditText c,Button d,Button f){e=a;e2=c;b=d;b2=f;}}
		public View getView(int i,View v,ViewGroup g)
		{
			LinearLayout l=(LinearLayout)v;final i a;
			if(l==null)
			{
				l=new LinearLayout(c);
				l.setTag(a=new i(new EditText(c),new EditText(c),new Button(c),new Button(c)));
				l.addView(a.e,p);
				l.addView(a.e2,p);
				l.addView(a.b,p);a.b.setText("更新信息");
				l.addView(a.b2,p);a.b2.setText("删除用户");a.b2.setTextColor(0xffff0000);
			}
			else a=(i)l.getTag();
			final String[]s=l7.get(i);
			a.e.setText(s[0]);
			a.e2.setText(s[1]);
			a.b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					d.execSQL("update t set 用户名='"+a.e.getText()+"',密码='"+a.e2.getText()+"'where 用户名='"+s[0]+"'");
					t10.setText(++i2+"：用户信息更新成功！");
				}catch(Exception e3)
				{
					t10.setText(++i2+"：用户名（"+a.e.getText()+"）已存在！");
				}
					g();
				}
			});
			a.b2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					d.execSQL("delete from t where 用户名='"+s[0]+"'");
					g();
					t10.setText(++i2+"：用户删除成功！");
				}catch(Exception e){e.printStackTrace();}}
			});
			return l;
		}
	}*/
}