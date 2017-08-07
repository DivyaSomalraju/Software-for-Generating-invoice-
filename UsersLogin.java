package invoicecreating;



import invoicedatabase.InvoiceDatabase;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UsersLogin implements ActionListener{
	JButton btnNewFrame;
	JTextField userText;
	JPasswordField passwordText;	
	JFrame thisFrame;
	public UsersLogin(JFrame myFrame){
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "EAGLE CONSULTANCY - INVOICE GENERATOR"));		
		myFrame.getContentPane().removeAll();		
		JLabel titleLabel = new JLabel("Login id");
		titleLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		titleLabel.setBounds(360, 180, 100, 30);
		myFrame.add(titleLabel);
		
		userText = new JTextField();
		userText.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		userText.setBounds(500, 180, 250, 30);
		myFrame.add(userText);
		
		titleLabel = new JLabel("Password");
		titleLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		titleLabel.setBounds(360, 230, 100, 30);
		myFrame.add(titleLabel);
		
		passwordText = new JPasswordField();
		passwordText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		passwordText.setBounds(500, 230, 250, 30);
		myFrame.add(passwordText);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(500, 325, 78, 35);
		loginButton.addActionListener(this);
		myFrame.add(loginButton);		
		myFrame.getContentPane().repaint();
		thisFrame=myFrame;
	}
	public void actionPerformed(ActionEvent e) {
		if(userText.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Enter User Name");
		}else if(passwordText.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Enter Passowrd");
		}else{
			InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
			String[] resultMessage=invoiceDatabase.userloginAuthentication(userText.getText(), passwordText.getText());
			if(resultMessage!=null && resultMessage.length==4){
				new UserOptions(thisFrame,resultMessage[3],userText.getText(),resultMessage[3]);
			}else{
				JOptionPane.showMessageDialog(null, "Please enter correct user name and password");
			}
		}
    } 
}
