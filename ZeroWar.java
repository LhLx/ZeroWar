//��ս2.0
//�Ż��˲��ִ��룬���Ӽ�࣬���ܸ���
//�����������ԣ����˸��̣߳�����������ڲ�������ϢģʽʱҲ�ܻ����ָ���1/s��
//��Ϣģʽ�¼�Ϊ��10+1��/s

import java.util.*;
import java.io.*;
class Warrior{
	String name;
	int strength;
	int defind; 
    int health=100;
	int experience=0;//��¼����ֵ��������
	int DouBle=1;//������������ɳ�ϵ��
	int level=1;
	int DoubleLevel=1;//�޸�������Ըı�������������ϵ��
	int Limit=20*DouBle;//�޸�������Ըı��������辭��,�ȼ�Խ��DouBleԽ��
	String[] skill;
	
	
	public static int sum=0;//��¼��������
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
		if(this.experience>=Limit) {//����ֵ�ﵽLimit������
			this.level++;
			this.strength+=30*DoubleLevel;
			this.health+=50*DoubleLevel;
			this.defind+=20*DoubleLevel;
			DoubleLevel++;
			this.experience-=Limit;
			this.DouBle++;//��������һ�����辭����
			System.out.println("��ϲ����"+this.name+"�����ˣ����Դ������������"+"��ǰ�ȼ�"+level);
		}
		else {
			System.out.println("���ͣ���"+this.name+"����"+(Limit-experience)+"�㾭�����������");
		}
	}
	
	void battle(int EnermyStrength) {
		if((EnermyStrength-this.defind)<0)
	    	{health=health;
	    	System.out.println("����δ���Ʒ�");}
	    else 
	    {
	    health-=(EnermyStrength-this.defind);}//����Ѫ���۳�=�з��ػ�-����������Ļ���
	    if(health<=0)//�������þ���
	    {
		System.out.println("���ź���ս��ʧ��"+'\n'+"���ľ���"+this.name+"Lv."+level+"��ս����ս��,��ȥ���������԰�");}
		else {
		System.out.println("���ͣ���"+this.name+"��ʣ��"+(health)+"��Ѫ��");
		int Exp=new Random().nextInt(Limit);
		this.experience+=Exp;
		System.out.println("��ϲ��ս��ʤ����"+this.name+"Lv."+level+"�����"+Exp+"�㾭��");
		//��������
		 levelUp();
	}
	}
	void show(){
		System.out.println("����   ��"+name+'\t'+"Ѫ��  ��"+health+'\n'
				+"����   ��"+strength+'\t'+"����  ��"+defind+'\n'
				+"��ǰ�ȼ���"+level+"     "+"����ֵ ��"+experience
				+'\n'+"����  ��");
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
			Life-=10;//��¼��õľ���	
			System.out.println("��ϲ����"+name+"��ѵ���л��"+rand+"�㾭��,�����������10,ʣ��������"+Life);
			levelUp();
			
		}System.out.println('\n'+"��ϲ����"+name+"��ѵ�����ܹ����"+randadd+"�㾭��");	
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
    {health-=(EnermyStreghth-this.defind);}//�Է�������Ų��ܿ�Ѫ
   
       if (health>0) {  //boss�Ļ���������������
	System.out.println("���ͣ���"+this.name+"Lv."+level+"��ʣ��"+(health)+"��Ѫ��");
       }
	else {
	level++;
	health=500*DouBle;//������������
	this.strength+=80*DouBle;
	DouBle++;
	System.out.println("��ϲ�ɹ�����"+(level-1)+"��"+this.name+"���� �����ȡ"+exp+"�㾭��"+'\n'+"BOSS�ȼ���������ȥ������");
    Experience+=exp;
         }
    }
}
void show() {	
	System.out.println("BOSS ��"+name+'\t'+"Ѫ��  ��"+health+'\n'
			+"����   ��"+strength+'\t'+"����  ��"+defind+'\n'
			+"��ǰ�ȼ���"+level+'\t'+"���ܽ�������"+experience+'\n'+"����  ��");
	for(String each:skill)
		System.out.println(each);
}
}


//�����߳�
class Life implements Runnable{
	int life=0;
	
	public void release() {
	 int time=0;
	  while(this.life<100) {
		 System.out.println("��Ϣʱ�䣺"+time+"s\t"+"��ǰ������"+life);
		try {
			Thread.currentThread().sleep(1000);//ÿ��ָ�10����
		}
		catch(Exception e) {}
		 this.life+=10;
		 time++;	 
  }
	  life=100;//���������ȥ��
	}
	//�����ڲ�ѡ����Ϣ����ʱ��Ҳ���Զ�������������
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
	new Thread(Life,"�Զ��ָ�����").start();
	
	
	int flag=0;//�����˳���Ϸ
	Warrior[] Have=new Warrior[5];//������еı������������ֻ�����ظ���
	int sum=0;//��������
	

	//��������Զ��ָ�����
	//��ʼ��������
	Warrior[] Baby= {new Warrior("���",70,20),new Warrior("����",60,30),
			         new Warrior("����",40,45),new Warrior("ӯ�ݷ�",60,30),
			         new Warrior("��",55,40), new Warrior("ҫ��",65,40)};
	Baby[0].getSkill(2, "������","������");
	Baby[1].getSkill(2, "ˮǹ�ݻ�","���Ӱ");
	Baby[2].getSkill(2, "������","�����Ƶ�");
	Baby[3].getSkill(2, "��Ҷ��","��ľ��ʯ");
	Baby[4].getSkill(2, "�ƽ�ȭ","���");
	Baby[5].getSkill(2, "�����","������");
	//��ʼ��������
	Monster monster=new Monster("��ӥ",50,30);
	monster.getSkill(1, "����Ϯ");
	

	System.out.print("");
	System.out.println("��ӭ������ս\n��ѡ������Ҫ�Ĺ���\n1.�鿴��Ϸ˵��"+'\n'+"2.��ȡ��ľ���"+'\n'+
			"3.�鿴��������"+'\n'+"4.�鿴��������"
			+'\n'+"5.�鿴boss����"+'\n'+"6.��ʼս��"+'\n'
			+"7.ѵ������"+'\n'+"8.�鿴����"+'\n'+"9.��Ϣ\na.�˳���Ϸ");
	//��ѭ����ʼ
	while(flag==0) {

	Scanner scanner=new Scanner(System.in);
	String chose=scanner.next();
  
	if(sum==0&&(chose.equals("4")||chose.equals("3")||chose.equals("6")||chose.equals("7")))
	{System.out.println("����û�о��飬��ȥ��ȡ��Ļ��ɣ���");}
	
	//�������
	else
	{		
	switch (chose) {
	case "1":System.out.println('\n'+"1.�鿴��Ϸ˵��"+'\n'+"2.��ȡ��ľ���"+'\n'+
			"3.�鿴��������"+'\n'+"4.�鿴��������"
			+'\n'+"5.�鿴boss����"+'\n'+"6.��ʼս��"+'\n'
			+"7.ѵ������"+'\n'+"8.�鿴����"+'\n'+"9.��Ϣ\na.�˳���Ϸ"
			+'\n'+"��Ϸ��ʼʱ����ҿ���ͨ����ť��ȡ�����Լ��ľ��飬���3ֻ�����ظ���"
			+'\n'+"����ľ���ﵽ�������辭���������������ô�����������������ͨ����"
			+'\n'+"սboss���Ի�þ��飬��Ҫ��ʤ���ܻ�þ��飬����bosss���ж����ö�Ӧ"
			+'\n'+"���飬����ͨ�����鿴boss��ѵ��Ҳ���Ի�ȡ�������飬������Ҫ�������"
			+'\n'+"���������������ʼ100�㣬���������ͨ����Ϣģʽ�ָ�����");
	break;  
	case "2": 
		if(sum!=4) {
		int rand=new Random().nextInt(6);//�����
		System.out.println("��ϲ����һֻ\""+Baby[rand].name+"\"���飬��ȥ���������԰�");	
		Have[sum]=Baby[rand];//�ӳ����������һֻ����
		sum++;//ӵ����+1	
	     }
	    else {	
	System.out.println("��ӵ�еľ��������Ѵ�����");	
	     }
	
	case "3":
		System.out.println("��ӵ��"+sum+"ֻ����");break;
	
	case "4":
		for(int j=0;j<sum;j++)
		{Have[j].show();
		System.out.println('\t');}
		break;
		
	case "5":
		monster.show();break;
	
	case "6":
		for(int j=0;j<sum;j++)
		{Have[j].battle(monster.strength);//System.out.println('\n'+"�س������ز˵�");
		monster.battle(Have[j].strength,Have[j].health,Have[j].experience);
		System.out.println('\t');}
		break;
	case "7":
		if(Life.life==0) {
	   System.out.println("�Բ������������Ѻľ�������ȥ��Ϣ�ָ�������");}
		else {
		for(int i=0;i<sum;i++) {
		Have[i].train(Life.life);}
		Life.life=0;
		System.out.println("ѵ����ɣ���ҵ�ǰ������ʣ��0");}
	    break;
	case "8":
		System.out.println("��ǰ������"+Life.life);
		break;
		
	case "9":
	Life.release();
	System.out.println("��Ϣ��ɣ���ҵ�ǰ������ʣ��"+Life.life);
	break;
	case "a":
		flag=1;break;//�˳���Ϸ��ѭ������ʶλ
	default:
		//ֱ�����������ʾ���ɣ��ص�ѭ����ʼ����������chose
		System.out.println("�����������������");
	}
	}
	}
}

}
