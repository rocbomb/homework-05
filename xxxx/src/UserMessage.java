import java.net.Socket;

public class UserMessage{
	private String name;
	private String ip;
	private Socket socket;
	public int score;
	public double submit;
	public UserMessage(String name,String ip,Socket socket){
		this.name=name;
        this.ip=ip;
        this.socket=socket;
        this.score = 0;
        this.submit = 0;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setIP(String ip){
		this.ip=ip;
	}
	public void setSocket(Socket socket){
		this.socket=socket;
	}
	public String getName(){
		return this.name;
	}
	public String getIP(){
		return this.ip;
	}
	public Socket getSocket(){
		return this.socket;
	}
}