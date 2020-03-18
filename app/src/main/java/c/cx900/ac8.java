/*
s→id2 = bool与s→id2 = s有冲突：
如i·=1不知道下一步状态怎么转移，所以必须改成：
s→id2 = s2，s2→id2 = s2，s2→bool
记方法!!!!!!!!!!!小心!!!!!!!!!!!!!!!

unary→i1 ∫ [ bool , bool ] w0 bool D ( id )不行!!!!!!!
因为id没初始化，所以会报错!!!!!!!!!!!!!必须改成：
unary→i1 ∫ [ bool , bool ] w0 D ( id ) i2 ( bool )
易错!!!!!!!!!!!!!小心!!!!!!!!!!!!!

s→bool不行!!!如a=1-2：a=(1-2)和(a=1)(-2)(即s s)冲突
易错难发现!!!!!!!!!!小心!!!!!!!!!!!!

S→S S | ε会有冲突，必须化成：
Ss→Ss S
Ss→ε
记!!!!!!!!!小心!!!!!!!!!!!!!!!

a&&b&&c=a&&(b&&c)
所以&&和||是右结合的，所以老师给的文法：
bool→bool || join
join→join && equality
是左结合的，不行!!!!!必须改成：
bool→join || bool
join→equality && join
记原理!!!!!!!!!!!小心!!!!!!!!!!!!!!!!!!

s→i s
s→s ;
有冲突!!!!!!!因为：
I0：
s'→·s，$
s→·s ;，$
s→·i s，$
s→·s ;，;
s→·i s，;

I0--i->I1：
s→i·s，$ ;
s→·s ;，$ ;
s→·i s，$ ;

I1--s->I2：
s→i s·，$ ;
s→s·;，$ ;
遇到;就有了归约和移进的冲突!!!!小心!!!!!!

s→i s
s→i s e s
也有冲突!!!!!!!!!也不行，小心!!!!!!!!!!!!因为：
I6：
s→i s ·，$
s→i s ·e s ，$
s→i s ·，e
s→i s ·e s ，e
遇到e就有冲突了，如i s i s e s就有冲突!!!!小心!!!!!!!
所以要改成：
s→s1
s→s2
s1→i s
s1→i s2 e s1
s2→i s2 e s2
s2→id + id
s2→...
其中s2代表i e已匹配的，s1代表任意情况

上面那个太麻烦了，所以遇到冲突的情况时
，把输出的状态转换表改一下即可!!!!!!!!!!

我写的翻译规则里面的注意点：
里面的||和&&不是短路与或，小心!!!!!!!!!
里面的lb,ln,lg,log,E,D,yy,bb,u等是关键字，小心!!!!!!!!!!!
里面的自然常数是E而不是e，小心!!!!!!!!!
里面的积分无法积∫[-10,10]D(x)(E^(-x^2))等
，因为E^-100太小，E^100太大!!!!!!记原理!!!!小心!!!!!!
也无法积区间长度太长的，因为精度会下降!!!!!!小心!!!!!!!
积分变量不能是前面出现过的变量，不然会被删掉，小心!!!
里面的正态分布表只到Φ(2.4)=0.9918，小心!!!!!!!!!!
没有的：
Φ(2.5)=0.9938，Φ(3.0)=0.9987，Φ(3.5)=0.9998
其他几乎用不到，是因为精度不够，记原理!!!!!!!小心!!!!!!!：
如Φ(3.5)=0.9998，但Φ(3.485..3.61)都=0.9998
*/
package c.cx900;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.*;import android.widget.*;
import android.view.*;

import java.nio.channels.FileChannel;
import java.util.*;import java.io.*;
import java.util.regex.*;
public class ac8 extends Activity
{
	EditText e,e2;String f;
	protected void onCreate(Bundle bu)
	{try{
		super.onCreate(bu);
		f=getFilesDir()+"/";
		if(!new File(f+"1输入.txt").exists())
		{
			AssetManager a=getAssets();
			for(String i:a.list(""))if(i.endsWith(".txt"))
			{
				cp(a.open(i),f+i);
			}
		}
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(e=new EditText(this));e.setMaxLines(8);e.setTextSize(10);e.setText("a=");
		//设置光标位置
		e.setSelection(2);
		Button b=new Button(this);l.addView(b);
		//直接用b.setHeight(8);改不了按钮大小，为啥？小心!!!!
		b.setText("计算值");
		b.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				f(e.getText()+"");
			}
		});
		l.addView(e2=new EditText(this));e2.setMaxLines(8);e2.setTextSize(10);
		TextView t=new TextView(this);l.addView(t);
		t.setText("下面的是测试例子，点击按钮就会粘贴测试例子并计算值");
		ListView lv=new ListView(this);l.addView(lv);
		lv.setAdapter(new ba(this));du();
	}catch(Exception e){e.printStackTrace();}}
	void cp(InputStream i,String j)
	{try{
		FileOutputStream o=new FileOutputStream(j);
		int l;byte[]b=new byte[8192];
		for(;(l=i.read(b))!=-1;)o.write(b,0,l);
		o.close();
	}catch(Exception e){e.printStackTrace();}}
	class ba extends BaseAdapter
	{
		List<String>l=new ArrayList<>();Context c;
		ba(Context a)
		{
			c=a;
			String s;for(int i=1;;i++)
			{
				s=f+i+"输入.txt";
				if(new File(s).exists())l.add(f2s(s));
				else break;
			}
		}
		//必须重载返回数组长度，小心!!!!!!!!!
		public int getCount(){return l.size();}
		public Object getItem(int i){return 0;}
		public long getItemId(int i){return 0;}
		//当滚动屏幕时，新进屏幕的位置会自动调用getView()
		//返回的值会被显示到对应位置
		//i是新进来的位置，v是新出去的视图，可以把它显示到
		//新进来的位置上，这样能提高效率，记原理!!!!!!小心!!!!!!!!
		public View getView(final int i,View v,ViewGroup g)
		{
			//当没有视图时，生成新视图，设置不变的东西
			Button b;
			if(v==null)
			{
				b=new Button(c);b.setGravity(Gravity.LEFT);
				b.setTextSize(10);
			}
			else b=(Button)v;
			//更新视图
			final String s=l.get(i);b.setText(s);
			b.setOnClickListener(new View.OnClickListener()
			{public void onClick(View v){e.setText(s);f(s);}});
			return b;
		}
	}
	static double[]aa={.5,.502,.504,.506,.508,.51,.512
			,.514,.516,.5179,.5199,.5219,.5239,.5259,.5279
			,.5299,.5319,.5339,.5359,.5378,.5398,.5418,.5438
			,.5458,.5478,.5497,.5517,.5537,.5557,.5576,.5596
			,.5616,.5636,.5655,.5675,.5695,.5714,.5734,.5753
			,.5773,.5793,.5812,.5832,.5851,.5871,.5890,.5910
			,.5929,.5948,.5968,.5987,.6006,.6026,.6045,.6064
			,.6083,.6103,.6122,.6141,.6160,.6179,.6198,.6217
			,.6236,.6255,.6274,.6293,.6312,.6331,.6350,.6368
			,.6387,.6406,.6424,.6443,.6462,.6480,.6499,.6517
			,.6536,.6554,.6573,.6591,.6609,.6628,.6646,.6664
			,.6682,.6700,.6718,.6736,.6754,.6772,.6790,.6808
			,.6826,.6844,.6862,.6879,.6897,.6915,.6932,.6950
			,.6967,.6985,.7002,.7019,.7037,.7054,.7071,.7088
			,.7106,.7123,.7140,.7157,.7174,.7190,.7207,.7224
			,.7241,.7257,.7274,.7291,.7307,.7324,.7340,.7357
			,.7373,.7389,.7405,.7422,.7438,.7454,.7470,.7486
			,.7502,.7517,.7533,.7549,.7565,.7580,.7596,.7611
			,.7627,.7642,.7658,.7673,.7688,.7704,.7719,.7734
			,.7749,.7764,.7779,.7794,.7808,.7823,.7838,.7852
			,.7867,.7881,.7896,.7910,.7925,.7939,.7953,.7967
			,.7981,.7995,.8009,.8023,.8037,.8051,.8065,.8079
			,.8092,.8106,.8119,.8133,.8146,.8159,.8173,.8186
			,.8199,.8212,.8225,.8238,.8251,.8264,.8277,.8289
			,.8302,.8315,.8327,.8340,.8352,.8365,.8377,.8389
			,.8401,.8413,.8426,.8438,.8449,.8461,.8473,.8485
			,.8497,.8508,.8520,.8531,.8543,.8554,.8566,.8577
			,.8588,.8599,.8610,.8621,.8632,.8643,.8654,.8665
			,.8676,.8686,.8697,.8708,.8718,.8729,.8739,.8749
			,.8760,.8770,.8780,.8790,.8800,.8810,.8820,.8830
			,.8840,.8849,.8859,.8869,.8878,.8888,.8897,.8907
			,.8916,.8925,.8934,.8944,.8953,.8962,.8971,.8980
			,.8988,.8997,.9006,.9015,.9023,.9032,.9041,.9049
			,.9057,.9066,.9074,.9082,.9091,.9099,.9107,.9115
			,.9123,.9131,.9139,.9147,.9154,.9162,.9170,.9177
			,.9185,.9192,.9200,.9207,.9215,.9222,.9229,.9236
			,.9244,.9251,.9258,.9265,.9272,.9279,.9285,.9292
			,.9299,.9306,.9312,.9319,.9325,.9332,.9338,.9345
			,.9351,.9357,.9364,.9370,.9376,.9382,.9388,.9394
			,.9400,.9406,.9412,.9418,.9424,.9429,.9435,.9441
			,.9446,.9452,.9458,.9463,.9468,.9474,.9479,.9484
			,.9490,.9495,.9500,.9505,.9510,.9515,.9520,.9525
			,.9530,.9535,.9540,.9545,.9550,.9554,.9559,.9564
			,.9568,.9573,.9577,.9582,.9586,.9591,.9595,.9599
			,.9604,.9608,.9612,.9616,.9621,.9625,.9629,.9633
			,.9637,.9641,.9645,.9649,.9652,.9656,.9660,.9664
			,.9667,.9671,.9675,.9678,.9682,.9686,.9689,.9693
			,.9696,.9699,.9703,.9706,.9710,.9713,.9716,.9719
			,.9723,.9726,.9729,.9732,.9735,.9738,.9741,.9744
			,.9747,.9750,.9753,.9756,.9759,.9761,.9764,.9767
			,.9770,.9773,.9775,.9778,.9780,.9783,.9786,.9788
			,.9791,.9793,.9796,.9798,.9801,.9803,.9805,.9808
			,.9810,.9812,.9815,.9817,.9819,.9821,.9824,.9826
			,.9828,.9830,.9832,.9834,.9836,.9838,.9840,.9842
			,.9844,.9846,.9848,.9850,.9852,.9854,.9856,.9857
			,.9859,.9861,.9863,.9864,.9866,.9868,.9870,.9871
			,.9873,.9875,.9876,.9878,.9879,.9881,.9882,.9884
			,.9885,.9887,.9888,.9890,.9891,.9893,.9894,.9896
			,.9897,.9898,.9900,.9901,.9902,.9904,.9905,.9906
			,.9907,.9909,.9910,.9911,.9912,.9913,.9915,.9916
			,.9917,.9918};
	//运算符或分割符或其他关键字c
	static String[]c={"if","else","while","(",")","[","]","{","}"
			,"√","π","sin","cos","tan","asin","acos","atan","++"
			,"--","+=","-=","*=","/=","%=","==","!=",">=","<=",">"
			,"<","=","!!","!","+","-","*","/","%","&&","||",";",",","lb","ln"
			,"lg","log","E","D","^","yy","bb","∫","Φ","u"};
	List<Map<String,String>>z=new ArrayList<>();
	List<List<String>>g=new ArrayList<>();
	List<String>g2=new ArrayList<>();
	//读文法
	void du()
	{try{
		//读文法：规则数组g,g2
		String s,u[],w[];List<String>l3;
		BufferedReader in=new BufferedReader
				(new FileReader(f+"文法.txt"));
		for(;(s=in.readLine())!=null&&!s.equals("");)
		{
			l3=new ArrayList<>();u=s.split("→");l3.add(u[0]);
			for(String p:u[1].split(" "))if(!p.equals(""))l3.add(p);
			g.add(l3);g2.add(s);
		}
		//读状态转换表z
		Map<String,String>h;
		in=new BufferedReader(new FileReader
				(f+"状态转换表.txt"));
		for(;(s=in.readLine())!=null&&!s.equals("");)
		{
			//每次必须要new一个新的h，小心!!!!!!!!!!!!!!
			h=new HashMap<>();for(String p:s.split("，"))
			{w=p.split("→");h.put(w[0],w[1]);}
			z.add(h);
		}
	}catch(Exception e){e.printStackTrace();}}
	//计算值
	void f(String q)
	{try{
		//读输入到l
		List<String[]>l=new ArrayList<>();int i,j,k,n;String s,t;
		//去掉注释的
		s=q.replaceAll("//.*","").replaceAll("(?s)/\\*.*?\\*/","");
		Matcher m;String o="";for(;!s.equals("");)
		{
			//跳过空白符
			t=s.substring(0,1);if(t.matches("\\s"))s=s.substring(1);
			//如果是数字常数，如0,12.3e4,0.120等
		else if(t.matches("[0-9]"))
		{
			(m=Pattern.compile("\\d+\\.?\\d*(e-?\\d+)?")
					.matcher(s)).find();
			l.add(new String[]{t=m.group(),"num"});
			s=s.substring(t.length());
		}
		else
		{
			//如果是运算符或分割符或其他关键字c
			for(i=0;i<c.length;i++)if(s.startsWith(c[i]))
			{
				l.add(new String[]{c[i],c[i]});
				s=s.substring(c[i].length());break;
			}
			//如果是id
			if(i==c.length)
			{
				(m=Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*")
						.matcher(s)).find();
				l.add(new String[]{t=m.group(),"id"});
				s=s.substring(t.length());
			}
		}
		}
		//变量名及其所对应的值v
		Map<String,Double>v=new TreeMap<>();
		//处理过程，状态栈x，符号栈y，输入l
		Stack<Integer>x=new Stack<>();
		class c
		{
			//输入s，变量名x，值v
			String s[],x;Double v;
			c(String a[],String b,Double d){s=a;x=b;v=d;}
		}
		Stack<c>y=new Stack<>();
		x.push(0);l.add(new String[]{"$","$"});
		String t2,xx="";double b=1;for(i=0;;)
		{
			t=z.get(n=x.peek()).get(t2=l.get(i)[1]);
			//如果匹配失败
			if(t==null){o="匹配失败！输入有错\n";break;}
			//如果是状态转换
			else if(t.startsWith("s"))
			{
				x.push(Integer.valueOf(t.substring(1)));
				y.push(new c(l.get(i++),null,null));
			}
			//如果是归约
			else if(t.startsWith("r"))
			{
				//按SDD翻译
				k=Integer.valueOf(t.substring(1));j=y.size()-1;
				Double d=null,d2,y1=null,y2=null,y3=null,y4=null;
				String s0="",s1="",x0="",x1="",x2="";c c;
				if(j>=0){c=y.get(j);d=c.v;s0=c.s[0];x0=c.x;}
				if(j>=1){c=y.get(j-1);y1=c.v;s1=c.s[0];x1=c.x;}
				if(j>=2){c=y.get(j-2);y2=c.v;x2=c.x;}
				if(j>=3)y3=y.get(j-3).v;if(j>=4)y4=y.get(j-4).v;
				//bool→join || bool
				if(k==15)d=(y2==0&&d==0)?0:1d;
				//join→equality && join
				else if(k==17)d=(y2!=0&&d!=0)?1:0d;
				//equality→equality == rel
				else if(k==19)d=y2.equals(d)?1:0d;
				//equality→equality != rel
				else if(k==20)d=y2.equals(d)?0:1d;
				//rel→expr < expr
				else if(k==22)d=y2<d?1:0d;
				//rel→expr <= expr
				else if(k==23)d=y2<=d?1:0d;
				//rel→expr >= expr
				else if(k==24)d=y2>=d?1:0d;
				//rel→expr > expr
				else if(k==25)d=y2>d?1:0d;
				//expr→expr + term
				else if(k==27)d=y2+d;
				//expr→expr - term
				else if(k==28)d=y2-d;
				//term→term * unary
				else if(k==30)d=y2*d;
				//term→term / unary
				else if(k==31)d=y2/d;
				//term→term % unary
				else if(k==32)d=y2%d;
				//unary→! unary
				else if(k==34)d=d.equals(0)?1:0d;
				//unary→- unary
				else if(k==35)d=-d;
				//factor→( bool )
				else if(k==37)d=y1;
				//factor→num
				else if(k==39)d=Double.valueOf(s0);
				//factor→π
				else if(k==40)d=Math.PI;
				//factor→E
				else if(k==41)d=Math.E;
				//unary→√ unary
				else if(k==42)d=Math.sqrt(d);
				//unary→[ bool ] √ unary
				else if(k==43)d=Math.pow(d,1/(y.get(j-3).v));
				//unary→factor √ unary
				else if(k==44)d=y2*Math.sqrt(d);
				//unary→factor ^ unary
				else if(k==45)d=Math.pow(y2,d);
				//factor→sin ( bool )
				else if(k==46)d=Math.sin(y1);
				//factor→cos ( bool )
				else if(k==47)d=Math.cos(y1);
				//factor→tan ( bool )
				else if(k==48)d=Math.tan(y1);
				//factor→asin ( bool )
				else if(k==49)d=Math.asin(y1);
				//factor→acos ( bool )
				else if(k==50)d=Math.acos(y1);
				//factor→atan ( bool )
				else if(k==51)d=Math.atan(y1);
				//factor→lb ( bool )
				else if(k==52)d=Math.log(y1)/Math.log(2);
				//factor→ln ( bool )
				else if(k==53)d=Math.log(y1);
				//factor→lg ( bool )
				else if(k==54)d=Math.log(y1)/Math.log(10);
				//factor→log [ bool ] ( bool )
				else if(k==55)d=Math.log(y1)/Math.log(y4);
				//factor→factor !
				else if(k==56){for(d=y1,d2=y1-1;d2>1;d2--)d*=d2;}
				//factor→factor !!
				else if(k==57){for(d=y1,d2=y1-2;d2>1;d2-=2)d*=d2;}
				//factor→yy ( bool , bool )
				else if(k==58)d=yy(y3,y1);
				//factor→bb ( bool , bool )
				else if(k==59)d=y3*y1/yy(y3,y1);
				//控制语句和循环语句的方法是精华!!!!!!!!记!!!!!!
				//恢复整个语句的可运行性
				//stmt→i0 i stmt
				else if(k==60)b=y2;
				//恢复整个语句的可运行性
				//stmt→i0 i stmt e stmt
				else if(k==61)b=y4;
				//保存整个语句的可运行性
				//i0→ε
				else if(k==62)d=b;
				//这2步最精华，记!!!!!!!!!易错!!!!!!!!!!小心!!!!!!!!!!!
				//整个语句可运行时，判断if()后的语句能否运行
				//必须判断整个语句是否可运行，小心!!!!!!!!!
				//i→if ( bool )
				else if(k==63&&b!=0)b=y1;
				//整个语句可运行时，则else后面语句的可运行性
				//为当前状态取反
				//这里判断整个语句是否可运行必须用i0的值，小心!!!!!!!!!
				//e→else//stmt→i0 i stmt e stmt
				else if(k==64&&y3!=0)b=b==0?1:0;
				//当前语句不可运行时，恢复整个语句的可运行性
				//当前语句可运行时，跳回前面保存的位置，重新判断
				//stmt→i0 w0 w stmt
				else if(k==65){if(b==0)b=y3;else i=y2.intValue();}
				//保存当前位置
				//w0→ε
				else if(k==66)d=i+0d;
				//整个语句可运行时，判断while()后的语句能否运行
				//w→while ( bool )
				else if(k==67&&b!=0)b=y1;
				//算积分的也是精华!!!!!!!!!!!!!!!!!记方法!!!!!!!!!!!!!!!!
				//如果算完积分则返回值
				//factor→∫ [ bool , bool ] i1 w0 ii
				else if(k==69)d*=(y.get(j-4).v-y.get(j-6).v)/2;
				//初始化
				//i1→ε
				else if(k==70)d=6d;
				//初始化积分值
				//ii→ε
				else if(k==71)d=0d;
				//ii→ii D ( id ) i2 ( bool )//∫ [ bool , bool ] i1 w0 ii
				else if(k==72)
				{
					double i1=y.get(j-10).v,d3;
					if(i1==5||i1==4)d3=0.1713245;
					else if(i1==3||i1==2)d3=0.3607616;
					else d3=0.4679139;
					d=y.get(j-8).v+y1*d3;
					//没算完积分时，本次算完后跳回前面保存的位置
					if(i1>0)i=y.get(j-9).v.intValue();
					//算完积分则把id从v中去掉
					else v.remove(y.get(j-5).s[0]);
				}
				//为id赋值
				//i2→ε//∫ [ bool , bool ] i1 w0 ii D ( id ) i2 ( bool )
				else if(k==73)
				{
					//ε不占栈的空间，易错难发现!!!!!!!!!小心!!!!!!!!!!!!!!!
					double a0=y.get(j-10).v,b0=y.get(j-8).v
							,i1=--y.get(j-6).v;
					if(i1==5)d=0.9324695;
					else if(i1==4)d=-0.9324695;
					else if(i1==3)d=0.6612094;
					else if(i1==2)d=-0.6612094;
					else if(i1==1)d=0.2386192;
					else if(i1==0)d=-0.2386192;
					v.put(s1,(b0-a0)/2*d+(a0+b0)/2);
				}
				//factor→Φ ( unary )
				else if(k==74)
				{
					if(y1>=0)d=aa[(int)(y1*200)];
					else d=1-aa[(int)(-y1*200)];
				}
				//factor→u [ unary ]
				else if(k==75)
				{if(y1>=0.5)d=find(y1)/200d;else d=-find(1-y1)/200d;}
				//factor→id2
				else if(k==38)d=v.get(x0);
				//id2→id
				else if(k==76)xx=s0;
				//id2→id2 [ bool ]
				else if(k==77)xx=y.get(j-3).x+"["+y1.intValue()+"]";
				//当语句可运行时
				if(b!=0)
				{
					//s→id2 = bool或s→id2 = s
					if(k==7||k==68)v.put(x2,d);
					//s→id2 += bool
					else if(k==8)v.put(x2,d=v.get(x2)+d);
					//s→id2 -= bool
					else if(k==9)v.put(x2,d=v.get(x2)-d);
					//s→id2 *= bool
					else if(k==10)v.put(x2,d=v.get(x2)*d);
					//s→id2 /= bool
					else if(k==11)v.put(x2,d=v.get(x2)/d);
					//s→id2 %= bool
					else if(k==12)v.put(x2,d=v.get(x2)%d);
					//s→id2 ++
					else if(k==13)v.put(x1,d=v.get(x1)+1);
					//s→id2 --
					else if(k==14)v.put(x1,d=v.get(x1)-1);
				}
				//从1开始要改回从0开始，小心!!!!!!!!!!!
				//若归约规则右边的字符数为n个，则x,y都出栈n个
				//如果是A→ε的归约，则不用出栈，小心!!!!!!!!!!
				if(!g.get(--k).get(1).equals("ε"))
					for(j=0,n=g.get(k).size()-1;j<n;j++){x.pop();y.pop();}
				//否则出栈0个，要改n，因为要输出!!!!!!!!小心!!!!!!
				else n=0;
				//再y入栈1个，然后状态转换入栈到x
				y.push(new c(new String[]{t2=g.get(k).get(0),t2},xx,d));
				x.push(Integer.valueOf(z.get(k=x.peek()).get(t2)));
			}
			//如果acc，则结束
			else break;
		}
		//输出每个变量的值
		for(String p:v.keySet())o+=p+"="+v.get(p)+"\n";
		e2.setText(o);
	}catch(Exception e)
	{
		e2.setText("输入有错，如：有变量未初始化"
				+"、有中文或日文等不合法的变量名、...");
	}}
	//2分法找最接近的值
	static int find(double k){return f(0,aa.length-1,k);}
	static int f(int i,int j,double k)
	{
		if(i==j)return i;if(i==j-1)return 2*k<aa[i]+aa[j]?i:j;
		int m=(i+j)/2;if(k<aa[m])return f(i,m,k);return f(m,j,k);
	}
	//算公因数
	double yy(double i,double j)
	{if(j==0)return i;return yy(j,i%j);}
	String f2s(String s)
	{File f=new File(s);byte[]b=new byte[(int)f.length()];try{
		InputStream i=new FileInputStream(f);i.read(b);i.close();
	}catch(Exception e){e.printStackTrace();}return new String(b);}
}