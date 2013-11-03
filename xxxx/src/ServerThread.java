import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class ServerThread extends Thread{
	private UserMessage user;
	private Socket socket=null;
	private ArrayList list=null;
	private boolean flag=true;
	public ServerThread(UserMessage user){
		this.user=user;
		list=ServerDemo.list;
        socket=user.getSocket();
	}
    public void sentMessageToClient(Socket sok,String msg){
    	try {
    		PrintWriter pw=new PrintWriter(sok.getOutputStream());
            pw.println(msg);
            pw.flush();
		}
		catch (Exception ex) {
		}
    }
	
	public void sayToAllUsers(String msg){
		//System.out.println ("ÈºÁÄ");
		for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			//System.out.println(user.getName());
			if(user.getName().equals("admin"))
				continue;
			Socket sok=user.getSocket();
			sentMessageToClient(sok,msg);
		}
		
	}
	
	public void sayToSpecialUser(String msg,String to){
		//System.out.println ("Ë½ÁÄ");
		for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			String name=user.getName();
			if(name.equals(to)){
				Socket sok=user.getSocket();
			    sentMessageToClient(sok,msg);
			    break;
			}
		}
	}
	
	public void quitFromChatRoom(String quitName){
		for (int i = 0; i<list.size(); i++){
			UserMessage user=(UserMessage)list.get(i);
			if(user.getName().equals(quitName)){
				list.remove(user);
				break;
			}
		}
		ServerDemo.sendAllUsersList();	
	}
	
	public void run(){
		while(flag){
			try {
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));	
        	String msg=br.readLine();
        	String mess[]=msg.split(":");
        	
        	
        	if(mess[0].equals("SUBMIT")){
        		ServerDemo.addsubmit(Double.parseDouble(mess[2]));
        		user.submit = Double.parseDouble(mess[2]);
			}
			
        	else if(mess[0].equals("QUIT")){
				Date currentTime = new Date();
        		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        		String dateString = formatter.format(currentTime);
				quitFromChatRoom(mess[1]);
				System.out.println("µçÄÔ|" + dateString + "|ÍË³ö|" + mess[1]);
				sayToAllUsers("STATE:DOWN:" + mess[1] + ":" + dateString + ":" + "D");
				flag=false;
			}
			
        	else if(mess[0].equals("GETALL")){
				File fl = new File("test.xls");
				Excel excel = new Excel(fl);
				Socket sok=user.getSocket();
			    sentMessageToClient(sok,excel.getAll());
			}
			}
			catch (Exception ex) {
			}	
		}
		
	}
}


