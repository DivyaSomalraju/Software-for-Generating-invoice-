package invoicecreating;

import invoicedatabase.InvoiceDatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SystemManager {
	JTextField changePassword;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	public SystemManager(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "System Manager Settings"));
		
		JLabel titleLabel = new JLabel("SYTEM MANGER");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		saveButton.setBounds(250, 300, 100, 30);		
		myFrame.add(saveButton);
		
		titleLabel = new JLabel("Select Type");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		String roleLabels[] = {"Accountant","Project Manager","Developer"};
		final JComboBox roleComboBox = new JComboBox(roleLabels);	    
	    //projectNumberComboBox.setMaximumRowCount(7);
		roleComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		roleComboBox.setSelectedIndex(0);
		roleComboBox.setBounds(250, 148, 250, 25);
	    myFrame.add(roleComboBox);
		
		titleLabel = new JLabel("Change Password");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 120, 25);
		myFrame.add(titleLabel);
		
		changePassword = new JTextField();
		changePassword.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		changePassword.setBounds(250, 178, 250, 25);
		myFrame.add(changePassword);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(changePassword!=null && changePassword.getText().trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.savepassworddata(((String)(roleComboBox.getItemAt(roleComboBox.getSelectedIndex()))), changePassword.getText());
						JOptionPane.showMessageDialog(null, "Successfully changed password");
						new SystemManager(myFrame,userRole,userType,userID);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please enter change password");
				}
			}
		});
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
    }
}
