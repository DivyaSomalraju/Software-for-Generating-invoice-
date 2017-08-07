package invoicecreating;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class UserOptions implements ActionListener{
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	public UserOptions(final JFrame myFrame,final String userRole,final String userType,final String userID){
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", userRole+" Data"));		
		myFrame.getContentPane().removeAll();
		
		JLabel titleLabel = new JLabel(userRole+"("+userType+")");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(800, 5, 300, 30);
		myFrame.add(titleLabel);
		
		JButton signoutButton = new JButton("Signout");
		signoutButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		signoutButton.setBounds(975, 50, 100, 35);
		signoutButton.addActionListener(this);
		myFrame.add(signoutButton);
		
		if(userRole!=null && userRole.equalsIgnoreCase("ACCOUNTANT")){
			String accountantLabels[] = {"Maintain","company data", "client data", "project data", "employee data"};
		    final JComboBox accountantComboBox = new JComboBox(accountantLabels);	    
		    accountantComboBox.setMaximumRowCount(7);
		    accountantComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		    accountantComboBox.setSelectedIndex(0);
		    accountantComboBox.setBounds(5, 0, 150, 35);
		    accountantComboBox.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 if(accountantComboBox.getSelectedIndex() != -1) {                     
		        		if(accountantComboBox.getItemAt(accountantComboBox.getSelectedIndex()).equals("Maintain")){
		        			JOptionPane.showMessageDialog(null, "Please select any one of Maintain Menu option.");
			     		}
		     			if(accountantComboBox.getItemAt(accountantComboBox.getSelectedIndex()).equals("company data")){
		     				new Companydata(myFrame,userRole,userType,userID);
		     			}
		     			if(accountantComboBox.getItemAt(accountantComboBox.getSelectedIndex()).equals("client data")){
		     				new Clientdata(myFrame,userRole,userType,userID);
		     			}
		     			if(accountantComboBox.getItemAt(accountantComboBox.getSelectedIndex()).equals("project data")){
		     				new Projectdata(myFrame,userRole,userType,userID);
		     			}
		     			if(accountantComboBox.getItemAt(accountantComboBox.getSelectedIndex()).equals("employee data")){
		     				new Employeedata(myFrame,userRole,userType,userID);
		     			}
		     		}
		         }
			});	    
		    myFrame.add(accountantComboBox);
		    
		    JButton assignButton = new JButton("Assign");
		    assignButton.setBounds(160, 0, 150, 35);
		    assignButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		    assignButton.addActionListener(this);
			myFrame.add(assignButton);		
			myFrame.getContentPane().repaint();
			
			
			String invoiceLabels[] = {"Invoice","Generate Invoice", "Display Hours"};
		    final JComboBox invoiceComboBox = new JComboBox(invoiceLabels);
		    invoiceComboBox.setMaximumRowCount(4);
		    invoiceComboBox.setSelectedIndex(0);
		    invoiceComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		    invoiceComboBox.setBounds(315, 0, 150, 35);
		    invoiceComboBox.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 if(invoiceComboBox.getSelectedIndex() != -1) { 
		        		if(invoiceComboBox.getItemAt(invoiceComboBox.getSelectedIndex()).equals("Schedule")){
		        			JOptionPane.showMessageDialog(null, "Please select any one of Schedule Menu option.");
				     	}
		        		if(invoiceComboBox.getItemAt(invoiceComboBox.getSelectedIndex()).equals("Generate Invoice")){
		        			new Invoicedata().generateInvoice(myFrame, userRole, userType, userID);
			     		}
		        		if(invoiceComboBox.getItemAt(invoiceComboBox.getSelectedIndex()).equals("Display Hours")){
		        			new Invoicedata().displayHours(myFrame,userRole,userType,userID);
			     		}
		     		}
		         }
			});	    
		    myFrame.add(invoiceComboBox);
			
		    String reportsLabels[] = {"Reports","Budget", "Hours"};
			final JComboBox reportsComboBox = new JComboBox(reportsLabels);
			reportsComboBox.setMaximumRowCount(5);
			reportsComboBox.setSelectedIndex(0);
			reportsComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			reportsComboBox.setBounds(470, 0, 150, 35);
			reportsComboBox.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 if(reportsComboBox.getSelectedIndex() != -1) {   
		        		if(reportsComboBox.getItemAt(reportsComboBox.getSelectedIndex()).equals("Reports")){
		        			JOptionPane.showMessageDialog(null, "Please select any one of Reports Menu option.");
					    }
		        		if(reportsComboBox.getItemAt(reportsComboBox.getSelectedIndex()).equals("Budget")){
		        			new Reportsdata().budgetReport(myFrame, userRole, userType, userID);
		        		}
		        		if(reportsComboBox.getItemAt(reportsComboBox.getSelectedIndex()).equals("Hours")){
		        			new Reportsdata().hoursReport(myFrame,userRole,userType,userID);
		        		}
		     		}
		         }
			});	    
			myFrame.add(reportsComboBox);
			
			JButton systemButton = new JButton("System");
			systemButton.setBounds(630, 0, 150, 35);
			systemButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		    systemButton.addActionListener(this);
			myFrame.add(systemButton);		
			myFrame.getContentPane().repaint();
		}
		
		
		if(userRole!=null && userRole.equalsIgnoreCase("PROJECT MANAGER")){
			JButton projectdataButton = new JButton("Project data");
			projectdataButton.setBounds(5, 0, 150, 35);
			projectdataButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			projectdataButton.addActionListener(this);
			myFrame.add(projectdataButton);
			
			JButton assignButton = new JButton("Assign");
			assignButton.setBounds(160, 0, 150, 35);
			assignButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			assignButton.addActionListener(this);
			myFrame.add(assignButton);
			
			JButton viewHoursButton = new JButton("Approve Hours");
			viewHoursButton.setBounds(315, 0, 150, 35);
			viewHoursButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			viewHoursButton.addActionListener(this);
			myFrame.add(viewHoursButton);
			
			JButton enterHoursButton = new JButton("Enter Hours");
			enterHoursButton.setBounds(475, 0, 150, 35);
			enterHoursButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			enterHoursButton.addActionListener(this);
			myFrame.add(enterHoursButton);		
		}
		
		if(userRole!=null && userRole.equalsIgnoreCase("DEVELOPER")){
			JButton developerButton = new JButton("Enter Hours");
			developerButton.setBounds(5, 0, 150, 35);
			developerButton.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			developerButton.addActionListener(this);
			myFrame.add(developerButton);
		}
		
		myFrame.getContentPane().repaint();
		myFrame.repaint();
		thisFrame=myFrame;
	}
	public void actionPerformed(ActionEvent event){
		if(event.getActionCommand().equalsIgnoreCase("Signout")){
			new UsersLogin(thisFrame);
		}
		if(event.getActionCommand().equalsIgnoreCase("Project data")){
			new Projectdata(thisFrame,thisUserRole,thisUserType,thisUserId);
		}
		if(event.getActionCommand().equalsIgnoreCase("Assign")){
			new AssignEmployee(thisFrame,thisUserRole,thisUserType,thisUserId);
		}
		if(event.getActionCommand().equalsIgnoreCase("Approve Hours")){
			new ApproveHours(thisFrame,thisUserRole,thisUserType,thisUserId);
		}
		if(event.getActionCommand().equalsIgnoreCase("Enter Hours")){
			new EnterHours(thisFrame,thisUserRole,thisUserType,thisUserId);
		}
		if(event.getActionCommand().equalsIgnoreCase("System")){
			new SystemManager(thisFrame,thisUserRole,thisUserType,thisUserId);
		}
    } 
}
