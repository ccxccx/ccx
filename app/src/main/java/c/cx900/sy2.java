package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sy2 extends Activity implements View.OnClickListener
{
	//文件夹a，文件a2
	List<String>l=new ArrayList<>();TreeSet<Integer>h=new TreeSet<>();
	TextView t,t2;int j,w,n;ba a;Button b,b2,b3,b4;
	LinearLayout l3;boolean d;
	void t(String s){t.setText(++j+"："+s);}
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//若想获取文件列表，则必须要有以下的权限：<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
		//而且READ_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		//若想重命名文件，则必须要有以下的权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
		//而且WRITE_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
				,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{
		w=getWindowManager().getDefaultDisplay().getWidth();
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		LinearLayout l=new LinearLayout(this);r.addView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(t=new TextView(this));t.setGravity(Gravity.CENTER);t.setTextColor(0xffff0000);
		l.addView(t2=new TextView(this));t2.setGravity(Gravity.CENTER);
		GridView g=new GridView(this);l.addView(g);g.setAdapter(a=new ba(this));g.setNumColumns(5);
		f(Environment.getExternalStorageDirectory()+"");
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(-1,-2);p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		l3=new LinearLayout(this);r.addView(l3,p);
		LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(0,-2,1);
		l3.addView(b=new Button(this),p2);b.setText("复制");b.setOnClickListener(this);
		l3.addView(b2=new Button(this),p2);b2.setText("剪切");b2.setOnClickListener(this);
		l3.addView(b3=new Button(this),p2);b3.setText("删除");b3.setOnClickListener(this);
		l3.addView(b4=new Button(this),p2);b4.setText("重命名");b4.setOnClickListener(this);
		l3.setVisibility(View.INVISIBLE);
		
		
		
//		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
//			StorageManager sm = (StorageManager)getSystemService(Context.STORAGE_SERVICE);
//			StorageVolume volume = sm.getPrimaryStorageVolume();
//			System.out.println(sm.getStorageVolumes().size());
//			volume = sm.getStorageVolumes().get(1);
//			Intent intent = volume.createAccessIntent(Environment.DIRECTORY_PICTURES);
//			startActivityForResult(intent, 0);
//		}
		final Intent intent = new Intent();
		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 19) {
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}
		else {
			intent.setAction(Intent.ACTION_GET_CONTENT);
		}
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/msword,application/pdf,text/plain");  //Text, DOC, PDF
		startActivityForResult(intent,0);
		
		
		
		
		
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode==Activity.RESULT_OK&&data!=null){
			//Get and Store the URI in
			
			final int takeFlags=data.getFlags()
					&(Intent.FLAG_GRANT_READ_URI_PERMISSION
					|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			getContentResolver().takePersistableUriPermission(data.getData(),takeFlags);
			
			File f=new File("/storage/999D-BE20/0/5");
			System.out.println(f.exists()+" "+f.canWrite());
		}
	}
	public void onClick(View v)
	{
		if(v==b)
		{
		
		}
		else if(v==b2)
		{
		
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
								File fi=new File(f+e.getText());
								if(!fi.getParentFile().mkdirs()||!new File(f+s).renameTo(fi))
								{
									t("重命名失败！权限不够或该文件名已被使用！");
								}
								f(fi.getParent());onBackPressed();
							}
						}).show();
			}
			else
			{
			
			}
		}
	}
	//删除文件和文件夹
	void d(File f)
	{
		if(f.isDirectory())for(File i:f.listFiles())d(i);
		f.delete();
	}
	//进入文件夹
	void f(String s)
	{try{
		//如果是根目录，则f.getParent()=null，所以需判断!!!!!!!
		//小心!!!!!!!!!!!!!!!!!!!!!!
		if(s!=null)
		{
			//根目录下的某些"文件"既不是文件，也不是文件夹，所以千万别用isFile()，小心!!!!!!!!!
			File f=new File(s);if(f.isDirectory())
			{
				t2.setText(s);l.clear();List<String>l2=new ArrayList<>();
				//如果是/storage/emulated，则f.listFiles()会返回null
				//，所以要判断防止秒退，小心!!!!!!!!!!!!!
				if(f.listFiles()!=null)for(File i:f.listFiles())
				{
					(i.isDirectory()?l:l2).add(i.getName());
				}
				Collections.sort(l);Collections.sort(l2);
				n=l.size();l.addAll(l2);
				//更新数据后必须调用这个，否则控件不会立马更新
				//，而是会在做某些动作后才会更新!!!!!
				//，而且滑动还会秒退!!!!!!!!!!!易错!!!!!!!!!小心!!!!!!!!
				a.notifyDataSetChanged();
			}
		}
		else
		{
			t("根目录无法后退！");
		}
	}catch(Exception e){e.printStackTrace();}}
	public void onBackPressed()
	{
		if(d)
		{
			d=false;
			l3.setVisibility(View.INVISIBLE);
			h.clear();a.notifyDataSetChanged();
		}
		else f(new File(t2.getText()+"").getParent());
	}
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
			if(i<n)
			{
				a.b.setTextColor(0xff0000ff);
				a.b.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						if(d)
						{
							if(!h.add(i))h.remove(i);
							notifyDataSetChanged();
						}
						else
						{
							String s2=t2.getText()+"";
							f(s2+(s2.equals("/")?"":"/")+s);
						}
					}
				});
			}
			else
			{
				a.b.setTextColor(0xff000000);
			}
			if(h.contains(i))a.t.setBackgroundColor(0x44ff0000);
			else a.t.setBackgroundColor(0);
			a.b.setOnLongClickListener(new View.OnLongClickListener()
			{
				public boolean onLongClick(View v)
				{
					l3.setVisibility(View.VISIBLE);
					d=true;
					h.add(i);notifyDataSetChanged();
					return false;
				}
			});
			return f;
		}
	}
}