import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public  class ServerDemo extends JFrame{
	public static ArrayList<UserMessage> list=new ArrayList();
	public static int newGame = 0;
	public static boolean gameStart = true;
	public static double Gpoint = 0;
	public static double Sum = 0;
	public static int turns = 0;
	public static List <Double>G_List = new ArrayList<Double>();  
	public static ViewFrame vf;
	public static String getAllUsersList(){
		StringBuffer buffer=new StringBuffer("USER");
		for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			buffer.append(":").append(user.getName());
		}
		return buffer.toString();
	}
	public static void sendAllUsersList(){
		PrintWriter pw=null;
		String listUsers=getAllUsersList();
	//	System.out.println (listUsers);
	   for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			if(user.getName().equals("admin"))
				continue;
			try {
				pw=new PrintWriter(user.getSocket().getOutputStream());
			    pw.println(listUsers);
			    pw.flush();
    
			}
			catch (Exception ex) {
			}
		    
	   }
	} 
	public static boolean addNewClient(UserMessage userNew){
		String name=userNew.getName();
		boolean flag=false;
		for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			   if((user.getName()).equals(name)){
			   		flag=true;
			   		break;
			   }
		}
		if(!flag){
			list.add(userNew);
			//System.out.println (userNew.getName()+" 进入聊天室");
			return true;
		}
		else
			return false;
			
	}
	
	public static void sayToAllUsers(String msg){
		//System.out.println ("群聊");
		for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			//System.out.println(user.getName());
			if(user.getName().equals("admin"))
				continue;
			Socket sok=user.getSocket();
			sentMessageToClient(sok,msg);
		}
		
	}
	
	public static void sentMessageToClient(Socket sok,String msg){
    	try {
    		PrintWriter pw=new PrintWriter(sok.getOutputStream());
            pw.println(msg);
            pw.flush();
		}
		catch (Exception ex) {
		}
    }
	public static void addsubmit( double d){
		Sum += d;
		System.out.println("Sum "+ Sum + " "+ turns);
	}
	
	public static void startGame(){
		turns = 0;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
              System.out.println("一次又一次");
              if(newGame == 0){
            	if(turns >= 10){
                	this.cancel();
            		System.out.println("end?!");
              	}
          		for (int i = 0; i<list.size(); i++){
        			UserMessage user=(UserMessage)list.get(i);
        			Socket sok=user.getSocket();
        			sentMessageToClient(sok,"START");
        			}
          		newGame = 1;
              }
              
              else {
          			turns++;           	  
            	  	System.out.println("turns = "+turns);
            	  	newGame = 0;
            	  	
            	  	//计算G点
            	  	Gpoint = Sum/list.size()*0.618;
            	  	System.out.println("Now Gpoint = "+Gpoint);
            	  	
            	  	//计算每个玩家分数  第一名加分  最后一面的减分
            	  	double xxx = 10000,max = 0,min = 100000;
            	  	int best=0,sb=0;
            		for (int i = 0; i<list.size(); i++){
            			UserMessage user=(UserMessage)list.get(i);
            			  xxx = Math.abs(user.submit -  Gpoint);
            			  System.out.println(user.getName()+" "+user.submit+"-"+Gpoint);
            			  if(xxx > max){
            				  sb =i;
            				  max = xxx;
            			  }
            			  if(xxx < min){
            				  best =i;
            				  min = xxx;
            			  }
            		}
            		UserMessage sbuser=(UserMessage)list.get(sb);
            		UserMessage bestuser=(UserMessage)list.get(best);
            		sbuser.score -= 1;
            		bestuser.score += 10;
            		
	        		StringBuffer buffer=new StringBuffer("");
	        		StringBuffer Ubuffer=new StringBuffer("用户        分数");
              		for (int j = 0; j<list.size(); j++){
            			UserMessage userx=(UserMessage)list.get(j);
            			buffer.append(":"+userx.getName()+"    "+userx.score);
            			Ubuffer.append("\n"+userx.getName()+"     "+userx.score);
              		}
              		
              		for (int i = 0; i<list.size(); i++){
            			UserMessage user=(UserMessage)list.get(i);
            			Socket sok=user.getSocket();
            			sentMessageToClient(sok,"GPOINT"+":"+turns+":"+Gpoint+buffer.toString());
            		}
	        		String src=vf.JTextArea_mess.getText();
	        		if(src.equals("")){
	        			vf.JTextArea_mess.setText("第"+turns+"轮Gpoint是"+Gpoint);
	        		}else{
	        			vf.JTextArea_mess.setText(src+"\n"+"第"+turns+"轮Gpoint是"+Gpoint);
	        		}
    	        	vf.JTextArea_user.setText(Ubuffer.toString());            		
            		Gpoint = 0;
            		Sum = 0;
            		System.out.println("end turns = "+turns);
              }
            }
          };
          timer.schedule(task, 0, 500);
	}
	
	public static void main (String[] args) {
		ServerSocket server = null;
		Socket socket = null;


   		vf = new ViewFrame();
		vf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vf.setSize(400,400);
		vf.setVisible(true);
		
		try {
			server=new ServerSocket(6000);
			socket=new Socket();
			System.out.println ("Server is beginning......");
			while(gameStart){
				socket = server.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String userMess = br.readLine();
				System.out.println(userMess);
				String[] mess = userMess.split(":");
				System.out.println(userMess);
				if(mess[0].equals("LOGIN")){
					File fl = new File("test.xls");
					Excel excel = new Excel(fl);
					System.out.println("some one enter");
					Date currentTime = new Date();
	        		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	        		String dateString = formatter.format(currentTime);
					
	        		//登录
					if(mess[3].equals(excel.getUserPassword(mess[1]))){
						UserMessage userNew=new UserMessage(mess[1],mess[2],socket);
						if(addNewClient(userNew)){
					    	PrintWriter pw=new PrintWriter(socket.getOutputStream());
					    	pw.println("NORMAL");
					    	pw.flush();
					    
					    	ServerThread thread=new ServerThread(userNew);
					    	thread.start();
					    	System.out.println("电脑|" + dateString + "|登录成功|" + mess[1]);
					    }
					    else{
					    	PrintWriter pw=new PrintWriter(socket.getOutputStream());
							pw.println("ON");
							pw.flush();
							System.out.println("电脑|" + dateString + "|登录失败|" + mess[1]);
					    }
					}
					else{
						PrintWriter pw=new PrintWriter(socket.getOutputStream());
						pw.println("NO");
						pw.flush();
						System.out.println("电脑|" + dateString + "|登录失败|" + mess[1]);
					}
				}
			}    
		}
		catch (Exception ex) {
		}
    }
}