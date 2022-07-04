//零战2.0
//优化了部分代码，更加简洁，性能更好
//增加了新特性，开了个线程，让玩家体力在不进行休息模式时也能缓慢恢复（1/s）
//休息模式下即为（10+1）/s

import java.util.*;
import java.io.*;
class Warrior{
	String name;
	int strength;
	int defind; 
    int health=100;
	int experience=0;//记录经验值用作升级
	int DouBle=1;//用于升级经验成长系数
	int level=1;
	int DoubleLevel=1;//修改这里可以改变升级属性增幅系数
	int Limit=20*DouBle;//修改这里可以改变升级所需经验,等级越高DouBle越高
	String[] skill;
	
	
	public static int sum=0;//记录宠物总数
	public Warrior(String name,int str,int defind)
	{
		this.name =name;
		strength=str;
		this.defind=defind;
		sum++;
	}
	
	void getSkill(int num,String...skill) {
		this.skill=new String[num];
		for(int i=0;i<num;i++)
		{this.skill[i]=new String(skill[i]);}
		
	}
	 
	void levelUp() {
		if(this.experience>=Limit) {//经验值达到Limit则升级
			this.level++;
			this.strength+=30*DoubleLevel;
			this.health+=50*DoubleLevel;
			this.defind+=20*DoubleLevel;
			DoubleLevel++;
			this.experience-=Limit;
			this.DouBle++;//升级后下一级所需经验变高
			System.out.println("恭喜！！"+this.name+"升级了！属性大幅度提升！！"+"当前等级"+level);
		}
		else {
			System.out.println("加油！！"+this.name+"还需"+(Limit-experience)+"点经验就能升级了");
		}
	}
	
	void battle(int EnermyStrength) {
		if((EnermyStrength-this.defind)<0)
	    	{health=health;
	    	System.out.println("敌人未能破防");}
	    else 
	    {
	    health-=(EnermyStrength-this.defind);}//采用血量扣除=敌方关机-自身防御，的机制
	    if(health<=0)//死亡不得经验
	    {
		System.out.println("很遗憾，战斗失败"+'\n'+"您的精灵"+this.name+"Lv."+level+"在战斗中战死,先去升级下属性吧");}
		else {
		System.out.println("加油！！"+this.name+"还剩下"+(health)+"点血量");
		int Exp=new Random().nextInt(Limit);
		this.experience+=Exp;
		System.out.println("恭喜你战斗胜利！"+this.name+"Lv."+level+"获得了"+Exp+"点经验");
		//升级功能
		 levelUp();
	}
	}
	void show(){
		System.out.println("名字   ："+name+'\t'+"血量  ："+health+'\n'
				+"攻击   ："+strength+'\t'+"防御  ："+defind+'\n'
				+"当前等级："+level+"     "+"经验值 ："+experience
				+'\n'+"技能  ：");
		for(String each:skill)
			System.out.println(each);
	}	
	
	void train(int Life) {
		int randadd=0;
		int L=Life;
		for(int i=0;i<(L/10);i++) {
	
			int rand=new Random().nextInt(30);
			this.experience+=rand;
			randadd+=rand;
			Life-=10;//记录获得的经验	
			System.out.println("恭喜您的"+name+"在训练中获得"+rand+"点经验,消耗玩家体力10,剩余体力："+Life);
			levelUp();
			
		}System.out.println('\n'+"恭喜您的"+name+"在训练中总共获得"+randadd+"点经验");	
	}
}



class Monster extends Warrior{
	
Monster(String name,int strength,int defind){
    super(name,strength,defind);
    this.health=300;
    
}	

void battle(int EnermyStreghth,int EnermyHealth,int Experience) {
	int exp=50*DouBle;
	if((EnermyStreghth-this.defind)<=0)
	{	health=health;
	}
    else 
    {
    	if(EnermyHealth>0)
    {health-=(EnermyStreghth-this.defind);}//对方打完活着才能扣血
   
       if (health>0) {  //boss的机制是死亡则升级
	System.out.println("加油！！"+this.name+"Lv."+level+"还剩下"+(health)+"点血量");
       }
	else {
	level++;
	health=500*DouBle;//升级属性增幅
	this.strength+=80*DouBle;
	DouBle++;
	System.out.println("恭喜成功击败"+(level-1)+"级"+this.name+"！！ 额外获取"+exp+"点经验"+'\n'+"BOSS等级提升，快去看看！");
    Experience+=exp;
         }
    }
}
void show() {	
	System.out.println("BOSS ："+name+'\t'+"血量  ："+health+'\n'
			+"攻击   ："+strength+'\t'+"防御  ："+defind+'\n'
			+"当前等级："+level+'\t'+"击败奖励经验"+experience+'\n'+"技能  ：");
	for(String each:skill)
		System.out.println(each);
}
}


//体力线程
class Life implements Runnable{
	int life=0;
	
	public void release() {
	 int time=0;
	  while(this.life<100) {
		 System.out.println("休息时间："+time+"s\t"+"当前体力："+life);
		try {
			Thread.currentThread().sleep(1000);//每秒恢复10体力
		}
		catch(Exception e) {}
		 this.life+=10;
		 time++;	 
  }
	  life=100;//将溢出部分去除
	}
	//体力在不选择休息功能时，也能自动“缓慢”增加
	 public void run() {
		 while(true) {
			 			try {
			             alwayLife();
			 		    Thread.sleep(1000);
			 			}catch(Exception e) {}
			 			}
			 	
	 }
			 	public synchronized void alwayLife() {
			 		if(this.life<100)
			 		{
			 			this.life++;  
			 		}
			 		
		
			 	}
	 }
	

public class ZeroWar {
public static void main(String[] args)throws IOException {
	
	var Life=new Life();
	new Thread(Life,"自动恢复体力").start();
	
	
	int flag=0;//用于退出游戏
	Warrior[] Have=new Warrior[5];//存放已有的宝贝龙，最多五只（可重复）
	int sum=0;//已有龙数
	

	//玩家体力自动恢复功能
	//初始化宝贝龙
	Warrior[] Baby= {new Warrior("火狐",70,20),new Warrior("海灵",60,30),
			         new Warrior("地龙",40,45),new Warrior("盈草飞",60,30),
			         new Warrior("鑫",55,40), new Warrior("耀天",65,40)};
	Baby[0].getSkill(2, "火球术","焰火九天");
	Baby[1].getSkill(2, "水枪泡击","深海幻影");
	Baby[2].getSkill(2, "地龙游","旋风破地");
	Baby[3].getSkill(2, "绿叶镖","缠木磐石");
	Baby[4].getSkill(2, "黄金拳","金光");
	Baby[5].getSkill(2, "逆光隐","极光闪");
	//初始化个怪物
	Monster monster=new Monster("霄鹰",50,30);
	monster.getSkill(1, "黑羽袭");
	

	System.out.print("");
	System.out.println("欢迎来到零战\n请选择你需要的功能\n1.查看游戏说明"+'\n'+"2.抽取你的精灵"+'\n'+
			"3.查看精灵数量"+'\n'+"4.查看精灵属性"
			+'\n'+"5.查看boss属性"+'\n'+"6.开始战斗"+'\n'
			+"7.训练精灵"+'\n'+"8.查看体力"+'\n'+"9.休息\na.退出游戏");
	//大循环开始
	while(flag==0) {

	Scanner scanner=new Scanner(System.in);
	String chose=scanner.next();
  
	if(sum==0&&(chose.equals("4")||chose.equals("3")||chose.equals("6")||chose.equals("7")))
	{System.out.println("您还没有精灵，快去抽取你的伙伴吧！！");}
	
	//正常情况
	else
	{		
	switch (chose) {
	case "1":System.out.println('\n'+"1.查看游戏说明"+'\n'+"2.抽取你的精灵"+'\n'+
			"3.查看精灵数量"+'\n'+"4.查看精灵属性"
			+'\n'+"5.查看boss属性"+'\n'+"6.开始战斗"+'\n'
			+"7.训练精灵"+'\n'+"8.查看体力"+'\n'+"9.休息\na.退出游戏"
			+'\n'+"游戏开始时，玩家可以通过按钮获取属于自己的精灵，最多3只（可重复）"
			+'\n'+"精灵的经验达到升级所需经验可以升级，并获得大量属性提升，可以通过挑"
			+'\n'+"战boss可以获得经验，需要获胜才能获得经验，击败bosss还有额外获得对应"
			+'\n'+"经验，可以通过面板查看boss，训练也可以获取巨量经验，但是需要消耗玩家"
			+'\n'+"体力。玩家体力初始100点，消耗完可以通过休息模式恢复体力");
	break;  
	case "2": 
		if(sum!=4) {
		int rand=new Random().nextInt(6);//随机抽
		System.out.println("恭喜你获得一只\""+Baby[rand].name+"\"精灵，快去看它的属性吧");	
		Have[sum]=Baby[rand];//从宠物库随机获得一只宠物
		sum++;//拥有数+1	
	     }
	    else {	
	System.out.println("您拥有的精灵数量已达上限");	
	     }
	
	case "3":
		System.out.println("您拥有"+sum+"只精灵");break;
	
	case "4":
		for(int j=0;j<sum;j++)
		{Have[j].show();
		System.out.println('\t');}
		break;
		
	case "5":
		monster.show();break;
	
	case "6":
		for(int j=0;j<sum;j++)
		{Have[j].battle(monster.strength);//System.out.println('\n'+"回车键返回菜单");
		monster.battle(Have[j].strength,Have[j].health,Have[j].experience);
		System.out.println('\t');}
		break;
	case "7":
		if(Life.life==0) {
	   System.out.println("对不起，您的体力已耗尽，请先去休息恢复体力吧");}
		else {
		for(int i=0;i<sum;i++) {
		Have[i].train(Life.life);}
		Life.life=0;
		System.out.println("训练完成，玩家当前体力点剩余0");}
	    break;
	case "8":
		System.out.println("当前体力："+Life.life);
		break;
		
	case "9":
	Life.release();
	System.out.println("休息完成，玩家当前体力点剩余"+Life.life);
	break;
	case "a":
		flag=1;break;//退出游戏（循环）标识位
	default:
		//直接输出错误提示即可，回到循环开始处重新输入chose
		System.out.println("输入错误，请重新输入");
	}
	}
	}
}

}
