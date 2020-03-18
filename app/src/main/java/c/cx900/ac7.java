/*
有冲突时可以直接改状态转换表，但改之后
一定要备份一下!!!!!!!小心!!!!!!!!!

S→Ss
Ss→Ss S
Ss→ε
不行!!!!!!有冲突，因为⇔Ss→Ss Ss | ε，必须改成：
S→{ Ss }
Ss→Ss S
Ss→ε
记方法!!!!!!!!!!!小心!!!!!!!!!!!!!

unary→- unary
unary→unary √ unary
有冲突!!!!!!!!!!!如-3√2：(-3)√2和-(3√2)冲突
所以要改成：
unary→- unary
unary→factor √ unary
易错!!!!!!!!!!小心!!!!!!!!!!!

unary→√ unary
unary→unary id
有冲突!!!!!!!如√2a：(√2)a和√(2a)冲突
要改成：
unary→√ unary
unary→factor id
易错!!!!!!!!!!小心!!!!!!!!!!!
同理unary→unary !等也要改成unary→factor !
易错!!!!!!!!!!小心!!!!!!!!!!!记方法!!!!!!!!

unary→factor id不行!!!!!!
因为i=1 j=2：(i=1)(j=2)和i=(1 j)=2冲突!!!!!!!
*/
package c.cx900;
import android.app.Activity;
import android.os.*;import android.widget.*;
import java.util.*;import java.io.*;
public class ac7 extends Activity
{
	TextView tv;String ms;
	Handler hd=new Handler()
	{
		public void handleMessage(Message m)
		{tv.setText(ms);}
	};
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		new Thread()
		{
			public void run()
			{
				gx();
			}
		}.start();
		tv=new TextView(this);l.addView(tv);
	}
	String f="/storage/emulated/0/0编程/0java/0app/0已完成/219计算器,编译器/0java实验/";
	//自动机某项的某条规则，如A→B·C，d e
	class ii
	{
		List<String>l=new ArrayList<>(),l2=new ArrayList<>();
		Set<String>s=new HashSet<>();
		public int hashCode()
		{return l.hashCode()+l2.hashCode()+s.hashCode();}
		public boolean equals(Object o)
		{
			ii i=(ii)o;if(l.equals(i.l)&&l2.equals(i.l2)&&s.equals(i.s))
			return true;
			return false;
		}
	}
	Set<String>A;Map<String,Set<String>>ft;
	List<List<String>>g;
	//更新状态转换表
	void gx()
	{try{
//读文法：起始符S,非终结符集A,终结符a,规则数组g
		String s,t,S,u[],w[];int i,j,k,n;A=new HashSet<>();
		Set<String>a=new HashSet<>();g=new ArrayList<>();
		List<String>l3=new ArrayList<>();
		PrintWriter o=new PrintWriter(f+"状态转换表.txt")
				,o2=new PrintWriter(f+"自动机各项.txt")
				,o3=new PrintWriter(f+"冲突项.txt");
		BufferedReader in=new BufferedReader
				(new FileReader(f+"文法.txt"));
		u=(s=in.readLine()).split("→");A.add(S=t=u[0]);
		l3.add(t);w=u[1].split(" ");
		for(j=0;j<w.length;j++)if(!w[j].equals(""))
		{l3.add(w[j]);a.add(w[j]);}
		g.add(l3);
		for(;(s=in.readLine())!=null&&!s.equals("");)
		{
			u=s.split("→");A.add(t=u[0]);
			l3=new ArrayList<>();l3.add(t);w=u[1].split(" ");
			for(j=0;j<w.length;j++)if(!w[j].equals(""))
			{l3.add(w[j]);a.add(w[j]);}
			g.add(l3);
		}
//要去掉ε，小心!!!!!!!!!!!
		a.removeAll(A);a.remove("ε");
//算first()集ft
		ft=new HashMap<>();for(String p:A)first(p);
//判断是第几个规则的Map
		Map<List<String>,Integer>h4=new HashMap<>();
		for(i=0;i<g.size();i++)
		{
			//A→·和A→ε的情况，易错!!!!!!!!!小心!!!!!!!
			//必须复制一份，因为不能改g，易错!!!!!!!!小心!!!!!!!
			l3=new ArrayList<String>(g.get(i));
			if(l3.get(1).equals("ε"))l3.remove(1);
			h4.put(l3,i);
		}
//构造自动机ii和状态转换表z
		List<List<ii>>ii=new ArrayList<>();
		List<ii>l1=new ArrayList<>(),l12,l13;ii l2=new ii(),l22;
		List<Map<String,String>>z=new ArrayList<>();
		Map<String,String>h;
		Map<String,List<ii>>h2=new HashMap<>();
		l2.l.add(S+"'");l2.l2.add(S);l2.s.add("$");
		l1.add(l2);bb(l1);ii.add(l1);
//判断是第几个自动机状态的Map
		Map<List<ii>,Integer>h5=new HashMap<>();
		h5.put(l1,0);String t2;for(k=0;k<ii.size();k++)
		{
			l1=ii.get(k);ms=k+"";hd.sendEmptyMessage(0);
			//输出自动机各项
			for(o2.println("I"+k+"："),i=0;i<l1.size();i++)
			{
				l2=l1.get(i);o2.print(l2.l.get(0)+"→");
				for(j=1;j<l2.l.size();j++)o2.print(l2.l.get(j)+" ");
				o2.print("·");
				for(j=0;j<l2.l2.size();j++)o2.print(l2.l2.get(j)+" ");
				o2.print("，");for(String p:l2.s)o2.print(p+" ");
				o2.println();
			}
			o2.println("\n");z.add(h=new HashMap<>());
			//h2用来暂时缓存结果，所以每次都必须清空!!!!!
			//易错!!!!!!!!!!!!!!小心!!!!!!!!!!!!!!!!!!!
			h2.clear();for(i=0;i<l1.size();i++)
		{
//求归约项
			if((l2=l1.get(i)).l2.size()==0)
			{
				//输出归约项时+1，这样文法翻译时比较不容易错!!!!!!
				if(h4.containsKey(l2.l))t="r"+(h4.get(l2.l)+1);
				else t="acc";
				for(String p:l2.s)
				{
//如果该项没有元素或旧值与现在的新值相同
					if(!h.containsKey(p)||h.get(p).equals(t))h.put(p,t);
					else o3.println("("+k+","+p+")：旧："+h.get(p)
							+"，新："+t+"，冲突。不改");
				}
			}
//求状态转换项，先把所有·后面第1个串相同的放在一起
			else
			{
				if((l12=h2.get(t=l2.l2.get(0)))==null)
				{l12=new ArrayList<>();h2.put(t,l12);}
				l22=new ii();l22.l=new ArrayList<String>(l2.l);
				l22.l2=new ArrayList<String>(l2.l2);
				l22.s=new HashSet<String>(l2.s);
				l22.l.add(l22.l2.remove(0));l12.add(l22);
			}
		}
			//求状态转换项
			for(i=0;i<l1.size();i++)if((l2=l1.get(i)).l2.size()!=0)
			{
//求闭包，然后判断状态转换到哪
				bb(l13=h2.get(t=l2.l2.get(0)));
				if(h5.containsKey(l13))n=h5.get(l13);
//如果是新状态，则加到ii里和h5里，必须在这里加
//易错!!!!!!!!小心!!!!!!!!!
				else{n=ii.size();h5.put(l13,n);ii.add(l13);}
				if(A.contains(t))t2=n+"";else t2="s"+n;
//如果该项有元素且旧值与现在的新值不同，即有冲突
				if(h.containsKey(t)&&!h.get(t).equals(t2))
				{
					o3.println("("+k+","+t+")：旧："+h.get(t)
							+"，新："+t2+"，冲突。改成了"+t2);
				}
//移进与归约冲突时，一般保留移进
//如s→i s和s→i s e s的冲突，记原理!!!!!!!!!小心!!!!!!!!!!
				h.put(t,t2);
			}
		}
//输出状态转换表简单版
		for(i=0;i<z.size();i++,o.println())
		{
			h=z.get(i);
			for(String p:h.keySet())o.print(p+"→"+h.get(p)+"，");
		}
		o.close();o2.close();o3.close();
		ms+="，结束";hd.sendEmptyMessage(0);
	}catch(Exception e){ms="";for(StackTraceElement p:e.getStackTrace())ms+=p+"\n";hd.sendEmptyMessage(0);}}
	//对规则求闭包
	void bb(List<ii>l)
	{
//避免出现相同规则
		Set<ii>s=new HashSet<ii>(l);
		ii l2,l22,l23;String t;int i,j,k;List<String>l3;
//为了把相同的合并，如S→·，a和S→·，b
		Map<List<List<String>>,Integer>h=new HashMap<>();
		List<List<String>>l4;for(i=0;i<l.size();i++)
	{
		l4=new ArrayList<>();l4.add(l.get(i).l);
		l4.add(l.get(i).l2);h.put(l4,i);
	}
//如果·后面是非终结符，则求闭包
		for(i=0;i<l.size();i++)if((l22=l.get(i)).l2.size()>0
				&&A.contains(t=l22.l2.get(0)))
			for(j=0;j<g.size();j++)if((l3=g.get(j)).get(0).equals(t))
			{
				l2=new ii();l2.l.add(t);
				for(k=1;k<l3.size();k++)l2.l2.add(l3.get(k));
				//A→·ε要化成A→·，小心!!!!!!!!!!!
				if(l2.l2.get(0).equals("ε"))l2.l2.remove(0);
				//必须复制一份再remove(0)，小心!!!!!!!!!!
				l3=new ArrayList<String>(l22.l2);l3.remove(0);
				//算first()
				l2.s=first(l3,l22.s);
				//没跟前面的重复时才能加进来
				//否则如A→·A，a就会无限循环，小心!!!!!!!!!!!!!!!
				if(!s.contains(l2))
				{
//判断合并S→·，a和S→·，b
					l4=new ArrayList<>();l4.add(l2.l);l4.add(l2.l2);
					if(h.containsKey(l4))
					{
						//必须要先删，再更新，再重新加，小心!!!!!!!!!!!!
						s.remove(l23=l.get(h.get(l4)));
						l23.s.addAll(l2.s);s.add(l23);
					}
//不能合并才能加进l，同时也要加进s和h，小心!!!!!!!!!!
					else{h.put(l4,l.size());l.add(l2);s.add(l2);}
				}
			}
	}
	Map<List<String>,Set<String>>
			h3=new HashMap<>();
	//算串的first()，同时加进h3
	Set<String>first(List<String>l,Set<String>s)
	{
		Set<String>t;String u;if(h3.containsKey(l))t=h3.get(l);
	else
	{
		t=new HashSet<>();for(int i=0;i<l.size();i++)
	{
		if(A.contains(u=l.get(i)))t.addAll(ft.get(u));
		else{t.add(u);break;}
		if(t.contains("ε"))t.remove("ε");else break;
	}
		h3.put(l,t);
	}
//必须复制1个新的Set，易错难发现!!!!!!!!!!小心!!!!!!!!!!!!!!
//超级易错!!!!!!!!!!!!!超难发现!!!!!!!!!!!小心!!!!!!!!!!!!
		t=new HashSet<String>(t);
//如果最后t中还有ε或t空，则把s全部加进t
//小心t空的情况!!!!!!!!!!!易错难发现!!!!!!!!!小心!!!!!!!!!!!!
		if(t.contains("ε")||t.size()==0){t.remove("ε");t.addAll(s);}
		return t;
	}
	//算单个符号的first()集ft
	Set<String>first(String s)
	{
//如果已经算过了
		if(ft.containsKey(s))return ft.get(s);
		Set<String>t=new HashSet<>();List<String>l;int i,j;
//如果是终结符或ε
		if(!A.contains(s))t.add(s);
//如果是非终结符
		else
		{
			for(i=0;i<g.size();i++)
				if((l=g.get(i)).get(0).equals(s))for(j=1;j<l.size();j++)
				{
//第1次先跳过左递归的情况
					if(!l.get(j).equals(s))t.addAll(first(l.get(j)));else break;
//如A→BC，如果ε∈first(B)，则first(A)=first(B)-ε
//∪first(C)∪..
					if(t.contains("ε")){if(j!=l.size()-1)t.remove("ε");}
					else break;
				}
			//处理左递归的情况
			if(t.contains("ε"))
			{
				for(t.remove("ε"),i=0;i<g.size();i++)
					if((l=g.get(i)).get(0).equals(s))for(j=1;j<l.size();j++)
						if(!l.get(j).equals(s))
						{
							t.addAll(first(l.get(j)));
							if(t.contains("ε"))t.remove("ε");else break;
						}
				t.add("ε");
			}
		}
		ft.put(s,t);return t;
	}
}