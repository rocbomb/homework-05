import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientThread extends Thread{
	private Socket socket;
	private ClientDemo client;
	public boolean flag = true;
	
	public ClientThread(Socket socket,ClientDemo client){
		this.socket=socket;
		this.client=client;		
	}
	public void run(){
		while(flag){
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        	String msg=br.readLine();
	        	String mess[]=msg.split(":");
	        	if(mess[0].equals("START")){
	        		client.sendGpoint();
	        	}
	        	
	        	if(mess[0].equals("GPOINT")){
	        		client.gpoint = Double.parseDouble(mess[1]);
	        		StringBuffer buffer=new StringBuffer("第");
	        		
	        		buffer.append(mess[1]).append("轮").append("Gpoint是").append(mess[2]);
	        		
	        		String src=client.JTextArea_mess.getText();
	        		if(src.equals("")){
	        			client.JTextArea_mess.setText(buffer.toString());
	        		}else{
	        			client.JTextArea_mess.setText(src+"\n"+buffer.toString());
	        		}
	        		StringBuffer ubuffer=new StringBuffer("用户      分数 \n");
	        		for(int i=3; i<mess.length; i++)	
	        			ubuffer.append(mess[i]+"\n");		
	        		client.JTextArea_user.setText(ubuffer.toString());
	        	}

	        	
	        	if(mess[0].equals("STATE")){
	    			StringBuffer buffer2 = new StringBuffer(mess[2]);
	    			if(mess[1].equals("UP")){
	    				buffer2.append(" 上线了 @" + mess[3] + ":" + mess[4] + ":" + mess[5]);
	    				if(mess[6].equals("M"))
	    					buffer2.append("  [手机]");
	    			}
	    			else{
	    				buffer2.append(" 下线了 @" + mess[3] + ":" + mess[4] + ":" + mess[5]);
	    				if(mess[6].equals("M"))
	    					buffer2.append("  [手机]");
	    			}
	    			
	    			String src=client.JTextArea_mess.getText();
	        		if(src.equals("")){
	        			client.JTextArea_mess.setText(buffer2.toString());
	        		}
	        		else{
	        			client.JTextArea_mess.append("\n"+buffer2.toString());
	        			Point p= new Point(0,client.JTextArea_mess.getLineCount()*20);
	        			client.JScrollPane_mess.getViewport().setViewPosition(p);
	        		}
	    		}
			}
			catch (Exception ex) {
			}
		
		}
	}
}

