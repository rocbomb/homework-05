import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class ClientDemo extends JFrame implements ActionListener {
	JPanel JPanel_north=new JPanel();
	JPanel JPanel_south=new JPanel(); 
	JPanel JPanel_center = new JPanel();
	public JLabel JLabel_name;
	public JTextField JTextField_name;
	//public JButton JButton_login;
	public JButton JButton_quit;

	public JTextArea JTextArea_mess;
	public JTextArea JTextArea_user;
	public JScrollPane JScrollPane_mess;
	public JScrollPane JScrollPane_user;
	public Socket socket;
	private static boolean flag=false;
	public static String username;
	public static double gpoint = 942;
	public ClientThread thread;
	Random random1 = new Random(System.currentTimeMillis());
	public ClientDemo(Socket socket, String username){
		super("Gpoint游戏");
		this.setResizable(false);
		this.username = username;
		this.getContentPane().add(JPanel_north,BorderLayout.NORTH);
		this.getContentPane().add(JPanel_south,BorderLayout.SOUTH);
		this.getContentPane().add(JPanel_center,BorderLayout.CENTER);
		JLabel_name=new JLabel("当前用户："+username);
		JLabel JLabel_blank = new JLabel("            ");
		//JTextField_name=new JTextField(8);
		//JButton_login=new JButton("登陆");
		JButton_quit=new JButton("退出");

		JPanel_north.add(JLabel_name);
		JPanel_north.add(JLabel_blank);
		//JPanel_north.add(JTextField_name);
		//JPanel_north.add(JButton_login);
		JPanel_north.add(JButton_quit);
		//JScrollPane和JPanel等都属于中间容器
		JTextArea_mess=new JTextArea(10,20);
		JTextArea_user=new JTextArea(10,10);
		JScrollPane_mess=new JScrollPane(JTextArea_mess);
		JScrollPane_user=new JScrollPane(JTextArea_user);
		JPanel_center.add(JScrollPane_mess);
		JPanel_center.add(JScrollPane_user);


		JTextArea_mess.setEditable(false);
		JTextArea_user.setEditable(false);
		
		JScrollPane_mess.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane_user.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTextArea_mess.setLineWrap(true);
		JTextArea_user.setLineWrap(true);
		
		//JButton_login.addActionListener(this);

		JButton_quit.addActionListener(this);
		
		WindowListener wl = new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				sendMessageToServer(getMessage("QUIT",ClientDemo.username,null ));
				System.exit(0);
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		this.addWindowListener(wl);
		
		KeyListener ka = new KeyListener(){


			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		JButton_quit.addKeyListener(ka);
		//JComboBox_users.addKeyListener(ka);
		//JTextArea_mess.addKeyListener(ka);
		//JTextArea_user.addKeyListener(ka);
		//JButton_send.addKeyListener(ka);
		//JScrollPane_mess.addKeyListener(ka);
		//JScrollPane_user.addKeyListener(ka);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocation(300,200);
		this.pack();
		this.setVisible(true);
		
		this.socket = socket;
		
		thread=new ClientThread(socket,this);
    	thread.start();
		
	}

	public static void main (String[] args) {
		ClientDemoStart lf = new ClientDemoStart();
    } 
    public void actionPerformed(ActionEvent e){
    	String name=e.getActionCommand();
    	if(name.equals("登陆")){
	    	String username=JTextField_name.getText();	
	    	if(username.equals(""))
	    		JOptionPane.showMessageDialog(null, "用户名不能为空", "alert", JOptionPane.ERROR_MESSAGE);   
	    		flag=true; 
	        try {
	        	String ip=InetAddress.getLocalHost().getHostAddress();   	
	        	socket=new Socket(ip,6000);  
	        	sendMessageToServer(getMessage("LOGIN",username,ip ));
	        
	        	ClientThread thread=new ClientThread(socket,this);
	        	thread.start();
			}
			catch (Exception ex) {
			}
    	}
    	if(name.equals("退出Game")){
    		thread.flag = false;
    		sendMessageToServer(getMessage("QUIT",username,null ));
    		this.setVisible(false);
    		ClientDemoStart lf = new ClientDemoStart();
    		return;
    	}
    }
	public void sendGpoint(){
		try {
			sendMessageToServer(getMessage("SUBMIT",username,random1.nextFloat()*10 + ""));
		}
		catch (Exception ex) {
		} 
	}
    public String getMessage(String head,String username,String mess){		
    	StringBuffer buffer=new StringBuffer(head+":");
        buffer.append(username).append(":").append(mess);
        return buffer.toString();
    }
    
    public void sendMessageToServer(String message){
    	try {
    		PrintWriter pw=new PrintWriter(socket.getOutputStream());   	
        	pw.println(message);
        	pw.flush();
		}
		catch (Exception ex) {
		}
    }
	
}

class ClientDemoStart extends JFrame implements ActionListener{
	public JButton JButton_login = new JButton("登录");
	public JTextField JTextField_name = new JTextField(12);
	public JPasswordField JPasswordField_password = new JPasswordField(12);
	public JLabel JLabel_name = new JLabel("用户名：");
	public JLabel JLabel_password = new JLabel("密    码：");
	public JPanel JPanel1 = new JPanel();
	public JPanel JPanel2 = new JPanel();
	public JPanel JPanel3 = new JPanel();
	public static String serverip; 
	public Socket socket;
	
	public ClientDemoStart(){
		super("登录Game");
		
		try {
			serverip = InetAddress.getLocalHost().getHostAddress();
			System.out.println(serverip);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.getContentPane().add(JPanel1,BorderLayout.NORTH);
		this.getContentPane().add(JPanel2,BorderLayout.CENTER);
		this.getContentPane().add(JPanel3,BorderLayout.SOUTH);
		
		JPanel1.add(JLabel_name);
		JPanel1.add(JTextField_name);
		JPanel2.add(JLabel_password);
		JPanel2.add(JPasswordField_password);
		JPanel3.add(JButton_login);

		
		JButton_login.addActionListener(this);

		
		KeyListener ka = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					login();
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		};
		JTextField_name.addKeyListener(ka);
		JPasswordField_password.addKeyListener(ka);
		
		this.setLocation(300,200);
		this.pack();
		this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e){
    	String name=e.getActionCommand();
    	if(name.equals("登录")){
    		System.out.println("点击登录");
    		login();
    	}
    }
	
	public void login(){
		String username=JTextField_name.getText();
		String password=String.valueOf(JPasswordField_password.getPassword());
		if(username.equals(""))
			JOptionPane.showMessageDialog(null, "用户名不能为空", "alert", JOptionPane.ERROR_MESSAGE);  
		else if(username.length() > 10)
			JOptionPane.showMessageDialog(null, "用户名长度不能超过10", "alert", JOptionPane.ERROR_MESSAGE); 
		else if(password.equals(""))
			JOptionPane.showMessageDialog(null, "密码不能为空", "alert", JOptionPane.ERROR_MESSAGE); 
		else if(password.length() > 10)
			JOptionPane.showMessageDialog(null, "密码长度不能超过10", "alert", JOptionPane.ERROR_MESSAGE); 
		//flag=true; 
		else{
			try {

				String ip = InetAddress.getLocalHost().getHostAddress();   	
				socket = new Socket(serverip,6000);
				sendMessageToServer(getMessage("LOGIN",username,ip)+":"+password);

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	        	String msg=br.readLine();

	        	if(msg.equals("NORMAL")){
	        		this.setVisible(false);
					ClientDemo client = new ClientDemo(socket, username);
	        	}
	        	else if(msg.equals("ON")){
	        		JOptionPane.showMessageDialog(null, "用户已在线！", "alert", JOptionPane.ERROR_MESSAGE); 
	        	}
			}
			catch (Exception ex) {
			}
		}
	}
    public String getMessage(String head,String username,String mess){		
    	StringBuffer buffer=new StringBuffer(head+":");
        buffer.append(username).append(":").append(mess);
        return buffer.toString();
    }
    
    public void sendMessageToServer(String message){
    	try {
    		PrintWriter pw=new PrintWriter(socket.getOutputStream());   	
        	pw.println(message);
        	pw.flush();
		}
		catch (Exception ex) {
		}
    }
	
}

