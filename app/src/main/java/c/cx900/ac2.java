package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//外部存储的写权限太难获取了，以后再想办法，小心!!!!!!!!!!!
//es文件浏览器的选择文件的打开方式的功能，百度了很久也不知道怎么实现，以后再想办法，小心!!!!!!!!!!!
public class ac2 extends Activity implements View.OnClickListener
{
	List<String>l=new ArrayList<>(),l2,l6;TreeSet<Integer>h=new TreeSet<>();
	TextView t,t2;int j,w,n,n2,d;ba a;Button b,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;
	LinearLayout l3,l4,l5,l7,l8;boolean d2;String f2,s2;EditText e,e2;GridView g;
	ImageView i2;static String f;
	void t(String s){t.setText(++j+"："+s);}
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//若想获取文件列表，则必须要有以下的权限：<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
		//而且READ_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		//若想重命名文件，则必须要有以下的权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
		//而且WRITE_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{
		w=getWindowManager().getDefaultDisplay().getWidth();
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		r.addView(t2=new TextView(this),-1,-2);t2.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams p6=new RelativeLayout.LayoutParams(-1,-2);p6.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		r.addView(b12=new Button(this),p6);b12.setText("<");b12.setOnClickListener(this);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);
		int i=1;t2.setId(i);p4.addRule(RelativeLayout.BELOW,1);b12.setId(i=2);p4.addRule(RelativeLayout.ABOVE,2);
		l7=new LinearLayout(this);r.addView(l7,p4);l7.setOrientation(LinearLayout.VERTICAL);
		l7.addView(t=new TextView(this));t.setGravity(Gravity.CENTER);t.setTextColor(0xffff0000);
		l7.addView(l8=new LinearLayout(this));l8.setOrientation(LinearLayout.VERTICAL);
		LinearLayout l9=new LinearLayout(this);l8.addView(l9);
		LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(0,-2,1);
		l9.addView(e=new EditText(this),new LinearLayout.LayoutParams(0,-2,4));
		e.setSingleLine();
		//当,在输入框内按下换行符时，执行自定义的代码
		e.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			public boolean onEditorAction(TextView tv,int i,KeyEvent k)
			{
				onClick(b11);
				return true;
			}
		});
		l9.addView(b11=new Button(this),p2);b11.setText("搜索");b11.setOnClickListener(this);
		l9=new LinearLayout(this);l8.addView(l9);
		l9.addView(b9=new Button(this),p2);b9.setText("新建文件");b9.setOnClickListener(this);
		l9.addView(b10=new Button(this),p2);b10.setText("新建文件夹");b10.setOnClickListener(this);
		g=new GridView(this);l7.addView(g);g.setAdapter(a=new ba(this));g.setNumColumns(5);
		RelativeLayout.LayoutParams p5=new RelativeLayout.LayoutParams(-1,-1);p5.addRule(RelativeLayout.BELOW,1);
		r.addView(e2=new EditText(this),p5);e2.setVisibility(View.INVISIBLE);e2.setGravity(Gravity.TOP);
		r.addView(i2=new ImageView(this),p5);i2.setVisibility(View.INVISIBLE);
		if(f==null)f=Environment.getExternalStorageDirectory()+"";
		f(f);
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(-1,-2);p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		l3=new LinearLayout(this);r.addView(l3,p);
		l3.addView(b=new Button(this),p2);b.setText("复制");b.setOnClickListener(this);
		l3.addView(b2=new Button(this),p2);b2.setText("剪切");b2.setOnClickListener(this);
		l3.addView(b3=new Button(this),p2);b3.setText("删除");b3.setOnClickListener(this);
		l3.addView(b4=new Button(this),p2);b4.setText("重命名");b4.setOnClickListener(this);
		l3.setVisibility(View.INVISIBLE);
		l4=new LinearLayout(this);r.addView(l4,p);
		l4.addView(b5=new Button(this),p2);b5.setText("粘贴");b5.setOnClickListener(this);
		l4.addView(b6=new Button(this),p2);b6.setText("取消");b6.setOnClickListener(this);
		l4.setVisibility(View.INVISIBLE);
		RelativeLayout.LayoutParams p3=new RelativeLayout.LayoutParams(-1,-2);
		p3.addRule(RelativeLayout.BELOW,1);
		r.addView(l5=new LinearLayout(this),p3);
		l5.addView(b7=new Button(this),p2);b7.setText("全选");b7.setOnClickListener(this);
		l5.addView(b8=new Button(this),p2);b8.setText("区间选择");b8.setOnClickListener(this);
		l5.setVisibility(View.INVISIBLE);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//f(Environment.getExternalStorageDirectory()+"/00");
	}
	void cp(String a,String b)
	{
		File i=new File(a),j=new File(b);
		if(i.isDirectory())
		{
			j.mkdirs();String s;
			for(File f:i.listFiles())cp(s=f+"",s.replace(a,b));
		}
		else
		{try{
			FileChannel f=new FileInputStream(i).getChannel();
			new FileOutputStream(j).getChannel().transferFrom(f,0,f.size());
		}catch(Exception e){e.printStackTrace();}}
	}
	public void onClick(View v)
	{
		if(v==b||v==b2)
		{
			d2=v==b;
			f2=t2.getText()+"/";l2=new ArrayList<>();
			for(int i:h)l2.add(l.get(i));
			onBackPressed();
			l4.setVisibility(View.VISIBLE);
		}
		else if(v==b3)
		{
			new AlertDialog.Builder(this).setTitle("您确定要删除这些文件吗？")
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{
							String f=t2.getText()+"/";
							for(int i:h)d(new File(f+l.get(i)));
							f(t2.getText()+"");onBackPressed();
						}
					}).show();
		}
		else if(v==b4)
		{
			if(h.size()==1)
			{
				final String f=t2.getText()+"/",s=l.get(h.first());
				final EditText e=new EditText(this);e.setText(s);
				new AlertDialog.Builder(this).setTitle("请输入新文件名").setView(e)
						.setPositiveButton("确定",new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								if("".equals(e.getText()+""))
								{
									t("重命名失败！文件名不能为空！");
								}
								else
								{
									if(!new File(f+s).renameTo(new File(f+e.getText())))
									{
										t("重命名失败！可能是权限不够或文件名不合法或该文件名已被使用");
									}
									f(t2.getText()+"");
								}
								onBackPressed();
							}
						}).show();
			}
			else
			{
				final EditText e=new EditText(this);e.setHint("请输入新文件名");
				final String f=t2.getText()+"/";
				new AlertDialog.Builder(this).setTitle("请输入新文件名").setView(e)
						.setPositiveButton("确定",new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								File f2=new File(f+e.getText()+"1");
								f2.getParentFile().mkdirs();
								int j=0,k;String s;
								for(int i:h)
								{
									s=l.get(i);k=s.lastIndexOf(".");
									new File(f+s).renameTo(new File(f+e.getText()+ ++j+(k==-1?"":s.substring(k))));
								}
								f(f2.getParent());onBackPressed();
							}
						}).show();
			}
		}
		else if(v==b5)
		{
			l4.setVisibility(View.INVISIBLE);
			if(d2)
			{
				for(String s:l2)
				{
					cp(f2+s,t2.getText()+"/"+s);
				}
			}
			else
			{
				for(String s:l2)
				{
					new File(f2+s).renameTo(new File(t2.getText()+"/"+s));
				}
			}
			f(t2.getText()+"");
		}
		else if(v==b6)
		{
			l4.setVisibility(View.INVISIBLE);
		}
		else if(v==b7)
		{
			if(b7.getText().equals("全选"))
			{
				b7.setText("全不选");
				for(int i=0;i<l.size();i++)h.add(i);
				b.setEnabled(true);b2.setEnabled(true);b3.setEnabled(true);b4.setEnabled(true);
				a.notifyDataSetChanged();
			}
			else
			{
				b7.setText("全选");
				h.clear();
				b.setEnabled(false);b2.setEnabled(false);b3.setEnabled(false);b4.setEnabled(false);
				a.notifyDataSetChanged();
			}
		}
		else if(v==b8)
		{
			if(!h.isEmpty())
			{
				for(int i=h.first()+1;i<h.last();i++)h.add(i);
				a.notifyDataSetChanged();
			}
		}
		else if(v==b9)
		{
			final EditText e=new EditText(this);
			new AlertDialog.Builder(this).setTitle("请输入新文件的名称").setView(e)
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{try{
							if(new File(t2.getText()+"/"+e.getText()).createNewFile())f(t2.getText()+"");
							else t("新建文件失败！可能是权限不够或文件名不合法或该文件名已被使用");
						}catch(Exception e){e.printStackTrace();}}
					}).show();
		}
		else if(v==b10)
		{
			final EditText e=new EditText(this);
			new AlertDialog.Builder(this).setTitle("请输入新文件夹的名称").setView(e)
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{try{
							if(new File(t2.getText()+"/"+e.getText()).mkdirs())f(t2.getText()+"");
							else t("新建文件夹失败！可能是权限不够或文件名不合法或该文件名已被使用");
						}catch(Exception e){e.printStackTrace();}}
					}).show();
		}
		else if(v==b11)
		{
			l=new ArrayList<>();
			String s=e.getText()+"";
			int i;
			for(i=0;i<n2;i++)if(l6.get(i).contains(s))l.add(l6.get(i));
			n=l.size();
			for(i=n2;i<l6.size();i++)if(l6.get(i).contains(s))l.add(l6.get(i));
			a.notifyDataSetChanged();
		}
		else if(v==b12)
		{
			if(d==0)f(new File(t2.getText()+"").getParent());
			else if(d==1)
			{
				d=0;
				l3.setVisibility(View.INVISIBLE);l5.setVisibility(View.INVISIBLE);l8.setVisibility(View.VISIBLE);
				h.clear();a.notifyDataSetChanged();
			}
			else if(d==2)
			{
				//这里不能用e2.getText().equals(s2)或s2.equals(e2.getText())
				//必须用s2.equals(e2.getText()+"")，易错难发现!!!!!!!!!!!!!小心!!!!!!!!!!!!!
				//为了防止出错，以后统一用s.equals(b.getText()+"")，记!!!!!!!!!!!!!小心!!!!!!!!!!!!!
				if(s2.equals(e2.getText()+""))
				{
					d=0;f(new File(t2.getText()+"").getParent());
					e2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);
				}
				else
				{
					new AlertDialog.Builder(this).setTitle("是否保存？")
							.setPositiveButton("是",new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog,int which)
								{
									s2f(e2.getText()+"",t2.getText()+"");
									d=0;f(new File(t2.getText()+"").getParent());
									e2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);
								}
							}).setNegativeButton("否",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{
							d=0;f(new File(t2.getText()+"").getParent());
							e2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);
						}
					}).show();
				}
			}
			else if(d==3)
			{
				d=0;f(new File(t2.getText()+"").getParent());
				i2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);
			}
		}
	}
	//删除文件和文件夹
	void d(File f)
	{
		if(f.isDirectory())for(File i:f.listFiles())d(i);
		f.delete();
	}
	void s2f(String s,String f)
	{try{
		PrintStream o=new PrintStream(f);o.print(s);o.close();
	}catch(Exception e){e.printStackTrace();}}
	String f2s(String f)
	{File fi=new File(f);byte[]b=new byte[(int)fi.length()];try{
		InputStream i=new FileInputStream(fi);i.read(b);i.close();
	}catch(Exception e){e.printStackTrace();}return new String(b);}
	//进入文件夹或文件
	void f(String s)
	{try{
		//如果是根目录，则f.getParent()=null，所以需判断!!!!!!!
		//小心!!!!!!!!!!!!!!!!!!!!!!
		if(s!=null)
		{
			//根目录下的某些"文件"既不是文件，也不是文件夹，所以千万别用isFile()，小心!!!!!!!!!
			File f=new File(s);
			if(f.isDirectory())
			{
				t2.setText(s);l.clear();List<String>l2=new ArrayList<>();
				//如果是/storage/emulated，则f.listFiles()会返回null
				//，所以要判断防止秒退，小心!!!!!!!!!!!!!
				if(f.listFiles()!=null)for(File i:f.listFiles())
				{
					(i.isDirectory()?l:l2).add(i.getName());
				}
				Collections.sort(l);Collections.sort(l2);
				n2=n=l.size();l.addAll(l2);l6=l;
				//更新数据后必须调用这个，否则控件不会立马更新
				//，而是会在做某些动作后才会更新!!!!!
				//，而且滑动还会秒退!!!!!!!!!!!易错!!!!!!!!!小心!!!!!!!!
				a.notifyDataSetChanged();
			}
			else
			{
				int i=f.getName().lastIndexOf(".");
				String t=i==-1?"":f.getName().substring(i+1);
				if(t.equals("")||t.equals("txt"))
				{
					t2.setText(s);l7.setVisibility(View.INVISIBLE);
					d=2;e2.setText(s2=f2s(s));e2.setVisibility(View.VISIBLE);
				}
				else if(t.equals("png")||t.equals("jpg")||t.equals("bmp"))
				{
					t2.setText(s);l7.setVisibility(View.INVISIBLE);
					d=3;i2.setVisibility(View.VISIBLE);i2.setImageBitmap(BitmapFactory.decodeFile(s));
				}
				else
				{
				
				}
			}
		}
		else
		{
			t("根目录无法后退！");
		}
	}catch(Exception e){e.printStackTrace();}}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l.size();}
		public Object getItem(int i){return 0;}
		public long getItemId(int i){return 0;}
		class i{Button b;TextView t;i(Button a,TextView c){b=a;t=c;}}
		public View getView(final int i,View v,ViewGroup g)
		{
			FrameLayout f=(FrameLayout)v;i a;
			if(f==null)
			{
				f=new FrameLayout(c);
				f.setTag(a=new i(new Button(c),new TextView(c)));
				f.addView(a.b);f.addView(a.t);
				//本来各个控件会参差不齐，超级难看!!!!!!
				//但利用setHeight(180)设置高度后控件就会变整齐!!!!!!!
				//记方法!!!!!!!!!!!!小心!!!!!!!!!!!!!!
				a.b.setHeight(180);
				//本来里面的文本的空间利用率不高，而且有些字的下半部分看不见，超级难看!!!!!!
				//用setPadding(10,0,10,20);可使文本更好看!!!!!!记方法!!!!!!!!!!!!
				a.b.setPadding(10,0,10,20);
			}
			else a=(i)f.getTag();
			final String s=l.get(i);
			a.b.setText(s);
			if(i<n)a.b.setTextColor(0xff0000ff);
			else a.b.setTextColor(0xff000000);
			if(h.contains(i))a.t.setBackgroundColor(0x44ff0000);
			else a.t.setBackgroundColor(0);
			a.b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					if(d==1)
					{
						if(!h.add(i))
						{
							h.remove(i);
							if(h.isEmpty())
							{
								b.setEnabled(false);b2.setEnabled(false);b3.setEnabled(false);b4.setEnabled(false);
							}
						}
						else if(h.size()==1)
						{
							b.setEnabled(true);b2.setEnabled(true);b3.setEnabled(true);b4.setEnabled(true);
						}
						notifyDataSetChanged();
					}
					else
					{
						String s2=t2.getText()+"";
						f(s2+(s2.endsWith("/")?"":"/")+s);
					}
				}
			});
			a.b.setOnLongClickListener(new View.OnLongClickListener()
			{
				public boolean onLongClick(View v)
				{
					l3.setVisibility(View.VISIBLE);l5.setVisibility(View.VISIBLE);l8.setVisibility(View.INVISIBLE);
					d=1;
					h.add(i);
					if(h.size()==1)
					{
						b.setEnabled(true);b2.setEnabled(true);b3.setEnabled(true);b4.setEnabled(true);
					}
					notifyDataSetChanged();
					return false;
				}
			});
			return f;
		}
	}
}