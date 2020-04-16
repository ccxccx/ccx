package c.cx900;
import android.Manifest;
import android.content.*;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;
import androidx.core.app.ActivityCompat;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

//外部存储的写权限太难获取了，以后再想办法，小心!!!!!!!!!!!
//es文件浏览器的选择文件的打开方式的功能，百度了很久也不知道怎么实现，以后再想办法，小心!!!!!!!!!!!
public class ac2 extends Activity implements View.OnClickListener
{
	List<String>l=new ArrayList<>(),l2,l6;TreeSet<Integer>h=new TreeSet<>();
	TextView t,t2;int j,w,n,n2,d,i3;ba a;Button b,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14;
	LinearLayout l3,l4,l5,l7,l8,l10;boolean d2;String f2,f3,f4,s2;EditText e,e2;GridView g;
	ImageView i2;static String f;ListView l11;ba2 a2;List<String>l12=new LinkedList<>();
	LinearLayout.LayoutParams p;
	void t(String s){t2.setText(++j+"："+s);}
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
	{try{
		w=getWindowManager().getDefaultDisplay().getWidth();
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		r.addView(t=new TextView(this),-1,-2);t.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams p6=new RelativeLayout.LayoutParams(-1,-2);p6.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		r.addView(b12=new Button(this),p6);b12.setText("<");b12.setOnClickListener(this);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);int i=1;t.setId(i);p4.addRule(RelativeLayout.BELOW,1);
		r.addView(l8=new LinearLayout(this),p4);
		p=new LinearLayout.LayoutParams(0,-2,1);
		l8.addView(b9=new Button(this),p);b9.setText("新建文件");b9.setOnClickListener(this);
		l8.addView(b10=new Button(this),p);b10.setText("新建文件夹");b10.setOnClickListener(this);
		l8.addView(b13=new Button(this),p);b13.setOnClickListener(this);
		RelativeLayout.LayoutParams p9=new RelativeLayout.LayoutParams(-1,-2);l8.setId(i=3);p9.addRule(RelativeLayout.BELOW,3);
		r.addView(l7=new LinearLayout(this),p9);l7.setOrientation(LinearLayout.VERTICAL);
		LinearLayout l9=new LinearLayout(this);l7.addView(l9);
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
		l9.addView(b11=new Button(this),p);b11.setText("搜索");b11.setOnClickListener(this);
		l7.addView(t2=new TextView(this));t2.setGravity(Gravity.CENTER);t2.setTextColor(0xffff0000);
		RelativeLayout.LayoutParams p8=new RelativeLayout.LayoutParams(-1,-2);
		l7.setId(i=4);p8.addRule(RelativeLayout.BELOW,4);b12.setId(i=2);p8.addRule(RelativeLayout.ABOVE,2);
		r.addView(g=new GridView(this),p8);g.setAdapter(a=new ba(this));g.setNumColumns(5);
		RelativeLayout.LayoutParams p5=new RelativeLayout.LayoutParams(-1,-1);p5.addRule(RelativeLayout.BELOW,1);p5.addRule(RelativeLayout.ABOVE,2);
		r.addView(e2=new EditText(this),p5);e2.setVisibility(View.INVISIBLE);e2.setGravity(Gravity.TOP);
		r.addView(i2=new ImageView(this),p5);i2.setVisibility(View.INVISIBLE);
		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-2);p2.addRule(RelativeLayout.ABOVE,2);
		l3=new LinearLayout(this);r.addView(l3,p2);
		l3.addView(b=new Button(this),p);b.setText("复制");b.setOnClickListener(this);
		l3.addView(b2=new Button(this),p);b2.setText("剪切");b2.setOnClickListener(this);
		l3.addView(b3=new Button(this),p);b3.setText("删除");b3.setOnClickListener(this);
		l3.addView(b4=new Button(this),p);b4.setText("重命名");b4.setOnClickListener(this);
		l3.setVisibility(View.INVISIBLE);
		l4=new LinearLayout(this);r.addView(l4,p2);
		l4.addView(b5=new Button(this),p);b5.setText("粘贴");b5.setOnClickListener(this);
		l4.addView(b6=new Button(this),p);b6.setText("取消");b6.setOnClickListener(this);
		l4.setVisibility(View.INVISIBLE);
		RelativeLayout.LayoutParams p3=new RelativeLayout.LayoutParams(-1,-2);
		p3.addRule(RelativeLayout.BELOW,1);
		r.addView(l5=new LinearLayout(this),p3);l5.setVisibility(View.INVISIBLE);
		l5.addView(b7=new Button(this),p);b7.setText("全选");b7.setOnClickListener(this);
		l5.addView(b8=new Button(this),p);b8.setText("区间选择");b8.setOnClickListener(this);
		RelativeLayout.LayoutParams p7=new RelativeLayout.LayoutParams(-1,-2);p7.addRule(RelativeLayout.BELOW,3);
		r.addView(l10=new LinearLayout(this),p7);l10.setOrientation(LinearLayout.VERTICAL);l10.setVisibility(View.INVISIBLE);
		l10.addView(b14=new Button(this));b14.setText("+");b14.setOnClickListener(this);
		l10.addView(l11=new ListView(this));l11.setAdapter(a2=new ba2(this));
		f4=Environment.getExternalStorageDirectory()+"";
		if(!new File(getFilesDir()+"/2.txt").exists())
		{
			l12=new ArrayList<>();l12.add(f3=f4);i3=0;
		}
		else
		{
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(getFilesDir()+"/2.txt"));
			l12=(List<String>)in.readObject();f3=in.readUTF();i3=in.readInt();
			in.close();
		}
		if(f!=null)
		{
			f3=f;f=null;
			if(l12.contains(f3))i3=l12.indexOf(f3);
			else l12.add(i3=0,f3);
		}
		f(f3);b13.setText(l12.size()+"");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//f(Environment.getExternalStorageDirectory()+"/00");
	}catch(Exception e){e.printStackTrace();}}
	protected void onPause()
	{try{
		super.onPause();
		if(f3!=null)
		{
			ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/2.txt"));
			o.writeObject(l12);o.writeUTF(f3);o.writeInt(i3);
			o.close();
		}
	}catch(Exception e){e.printStackTrace();}}
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
			f2=f3+"/";l2=new ArrayList<>();
			for(int i:h)l2.add(l.get(i));
			b();
			l4.setVisibility(View.VISIBLE);
		}
		else if(v==b3)
		{
			new AlertDialog.Builder(this).setTitle("您确定要删除这些文件吗？")
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{
							String f=f3+"/";
							for(int i:h)d(new File(f+l.get(i)));
							f(f3);b();
						}
					}).show();
		}
		else if(v==b4)
		{
			if(h.size()==1)
			{
				final String f=f3+"/",s=l.get(h.first());
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
									f(f3);
								}
								b();
							}
						}).show();
			}
			else
			{
				final EditText e=new EditText(this);e.setHint("请输入新文件名");
				final String f=f3+"/";
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
								f(f2.getParent());b();
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
					cp(f2+s,f3+"/"+s);
				}
			}
			else
			{
				for(String s:l2)
				{
					new File(f2+s).renameTo(new File(f3+"/"+s));
				}
			}
			f(f3);
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
							if(new File(f3+"/"+e.getText()).createNewFile())f(f3);
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
							if(new File(f3+"/"+e.getText()).mkdirs())f(f3);
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
			b();
		}
		else if(v==b13)
		{
			l10.setVisibility(l10.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
		}
		else if(v==b14)
		{
			l12.add(i3=0,f4);f(f4);a2.notifyDataSetChanged();b13.setText(l12.size()+"");
			l10.setVisibility(View.INVISIBLE);
		}
	}
	void b()
	{
		if(d==0)f(new File(f3).getParent());
		else if(d==1)
		{
			d=0;
			l3.setVisibility(View.INVISIBLE);l5.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);l8.setVisibility(View.VISIBLE);
			h.clear();a.notifyDataSetChanged();
		}
		else if(d==2)
		{
			//这里不能用e2.getText().equals(s2)或s2.equals(e2.getText())
			//必须用s2.equals(e2.getText()+"")，易错难发现!!!!!!!!!!!!!小心!!!!!!!!!!!!!
			//为了防止出错，以后统一用s.equals(b.getText()+"")，记!!!!!!!!!!!!!小心!!!!!!!!!!!!!
			if(s2.equals(e2.getText()+""))
			{
				d=0;f(new File(f3).getParent());
				e2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);l8.setVisibility(View.VISIBLE);
			}
			else
			{
				new AlertDialog.Builder(this).setTitle("是否保存？")
						.setPositiveButton("是",new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								s2f(e2.getText()+"",f3);
								d=0;f(new File(f3).getParent());
								e2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);l8.setVisibility(View.VISIBLE);
							}
						}).setNegativeButton("否",new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,int which)
					{
						d=0;f(new File(f3).getParent());
						e2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);l8.setVisibility(View.VISIBLE);
					}
				}).show();
			}
		}
		else if(d==3)
		{
			d=0;f(new File(f3).getParent());
			i2.setVisibility(View.INVISIBLE);l7.setVisibility(View.VISIBLE);l8.setVisibility(View.VISIBLE);
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
				t.setText(f3=s);l.clear();List<String>l2=new ArrayList<>();
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
				l12.set(i3,f3);a2.notifyDataSetChanged();
			}
			else
			{
				int i=f.getName().lastIndexOf(".");
				String u=i==-1?"":f.getName().substring(i+1);
				if(u.equals("")||u.equals("txt"))
				{
					t.setText(s);l7.setVisibility(View.INVISIBLE);l8.setVisibility(View.INVISIBLE);
					d=2;e2.setText(s2=f2s(s));e2.setVisibility(View.VISIBLE);
				}
				else if(u.equals("png")||u.equals("jpg")||u.equals("bmp"))
				{
					t.setText(s);l7.setVisibility(View.INVISIBLE);l8.setVisibility(View.INVISIBLE);
					d=3;i2.setVisibility(View.VISIBLE);i2.setImageBitmap(BitmapFactory.decodeFile(s));
				}
				else if(u.equals("mp3"))
				{
					List<String>a=new ArrayList<>();a.add(s);
					ac10.a=a;ac10.j=0;
					startActivity(new Intent(this,ac10.class));
				}
				else if(u.equals("mp4"))
				{
					startActivity(new Intent(this,ac5.class).setData(Uri.parse("file://"+s)));
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
				a.b.setHeight(w*2/9);
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
						f(f3+(f3.endsWith("/")?"":"/")+s);
					}
				}
			});
			a.b.setOnLongClickListener(new View.OnLongClickListener()
			{
				public boolean onLongClick(View v)
				{
					l3.setVisibility(View.VISIBLE);l5.setVisibility(View.VISIBLE);l7.setVisibility(View.INVISIBLE);l8.setVisibility(View.INVISIBLE);l10.setVisibility(View.INVISIBLE);
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
	class ba2 extends BaseAdapter
	{
		Context c;
		ba2(Context a){c=a;}
		public int getCount(){return l12.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{Button b,b2;i(Button a,Button c){b=a;b2=c;}}
		public View getView(final int i,View v,ViewGroup g)
		{
			i a;LinearLayout l=(LinearLayout)v;
			if(l==null)
			{
				l=new LinearLayout(c);
				l.setTag(a=new i(new Button(c),new Button(c)));
				l.addView(a.b,new LinearLayout.LayoutParams(0,-2,7));
				l.addView(a.b2,p);a.b2.setText("✕");
			}
			else a=(i)l.getTag();
			final String f=l12.get(i);
			if(i==i3)l.setBackgroundColor(0xffff7777);
			else l.setBackgroundColor(0xffcccccc);
			a.b.setText(f);
			a.b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(i!=i3)
					{
						i3=i;f(f);notifyDataSetChanged();
					}
					l10.setVisibility(View.INVISIBLE);
				}catch(Exception e){e.printStackTrace();}}
			});
			a.b2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(l12.size()==1)
					{
						Toast.makeText(c,"最后1个不能删除！",Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(i==i3)f(l12.get(i==l12.size()-1?i3=i-1:i+1));
						l12.remove(i);notifyDataSetChanged();b13.setText(l12.size()+"");
					}
				}catch(Exception e){e.printStackTrace();}}
			});
			return l;
		}
	}
}