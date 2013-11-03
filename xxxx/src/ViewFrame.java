

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;


public class ViewFrame extends JFrame implements ActionListener {

	JPanel JPanel_north=new JPanel();
	JPanel JPanel_south=new JPanel(); 
	JPanel JPanel_center = new JPanel();
	public JButton JButton_start;
	public JTextArea JTextArea_mess;
	public JTextArea JTextArea_user;
	public JScrollPane JScrollPane_mess;
	public JScrollPane JScrollPane_user;

	public ViewFrame(){
		super("服务器UI");

		JButton_start = new JButton("开始游戏");


		JPanel_north.add(JButton_start);

		JButton_start.addActionListener(this);
		this.getContentPane().add(JPanel_north,BorderLayout.NORTH);
		this.getContentPane().add(JPanel_south,BorderLayout.SOUTH);
		this.getContentPane().add(JPanel_center,BorderLayout.CENTER);
		JTextArea_mess=new JTextArea(15,20);
		JTextArea_user=new JTextArea(15,10);
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
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name=e.getActionCommand();
    	if(name.equals("开始游戏")){
    		ServerDemo.startGame();
    		ServerDemo.gameStart = false;
    	}
	}
}


