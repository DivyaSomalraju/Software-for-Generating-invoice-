package invoicecreating;

import invoicedatabase.InvoiceDatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class EnterHours {
	JTextField date,hours;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	public EnterHours(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Enter Hours Data"));
		
		JLabel titleLabel = new JLabel("ENTER HOURS");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList listOfProjects=invoiceDatabase.developerhoursdata(userType);
		int totalColumns=9;
		String enterhoursColumnNames[] = {"Client Number", "Project Number", "Developer Name", "Start Date", "End Date", "Worked Hours", "Bill Rate", "Total Amount", "Approve Status"};
		String outdata[][]=new String[0][totalColumns];
		if(listOfProjects!=null && listOfProjects.size()!=0){			
			Iterator iterator=listOfProjects.iterator();
			int arrayIndex=0;
			outdata=new String[listOfProjects.size()][totalColumns];
			while(iterator.hasNext()){
				String[] enterhoursTokens=(String[])iterator.next();
				outdata[arrayIndex][0]=enterhoursTokens[0];
				outdata[arrayIndex][1]=enterhoursTokens[1];
				outdata[arrayIndex][2]=enterhoursTokens[2];
				outdata[arrayIndex][3]=enterhoursTokens[4];
				outdata[arrayIndex][4]=enterhoursTokens[5];
				outdata[arrayIndex][5]=enterhoursTokens[3];
				outdata[arrayIndex][6]=enterhoursTokens[6];
				outdata[arrayIndex][7]=""+(Integer.parseInt(enterhoursTokens[3]))*(Integer.parseInt(enterhoursTokens[6]));
				outdata[arrayIndex][8]=enterhoursTokens[7];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable assignprojectTable = new JTable(outdata, enterhoursColumnNames);	    	    
		JScrollPane scrollPane = new JScrollPane(assignprojectTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		saveButton.setBounds(250, 300, 100, 30);		
		myFrame.add(saveButton);
		
		titleLabel = new JLabel("Project Number");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		ArrayList temporaryprojectlist=null;
		if(userRole.equalsIgnoreCase("Project Manager")){
			temporaryprojectlist=invoiceDatabase.assignedprojectmanagersdata(userType);
		}else{
			temporaryprojectlist=invoiceDatabase.assigndevelopersdata(userType);
		}
		final ArrayList projectlist=temporaryprojectlist;		
		
		String projectNumberLabels[] = new String[0];
		if(projectlist!=null && projectlist.size()!=0){
			projectNumberLabels = new String[projectlist.size()];
			for(int val=0;val<projectlist.size();val++){
				String[] assigndata= (String[])projectlist.get(val);
				projectNumberLabels[val] = assigndata[1];
			}
		}
	    final JComboBox projectNumberComboBox = new JComboBox(projectNumberLabels);	    
	    projectNumberComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    projectNumberComboBox.setSelectedIndex(0);
	    projectNumberComboBox.setBounds(250, 148, 250, 25);
	    myFrame.add(projectNumberComboBox);
		
		titleLabel = new JLabel("Date");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 100, 25);
		myFrame.add(titleLabel);
		final SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		date = new JTextField();		
		date.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		date.setBounds(250, 178, 250, 25);
		myFrame.add(date);
		
		titleLabel = new JLabel("Hours");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 210, 100, 25);
		myFrame.add(titleLabel);
		
		hours = new JTextField();
		hours.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		hours.setBounds(250, 208, 250, 25);
		myFrame.add(hours);  

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(date!=null && hours!=null 
						&& date.getText().trim().length()!=0 && hours.getText().trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
						String[] assigndata=(String[])projectlist.get(projectNumberComboBox.getSelectedIndex());							
						String people_date[]=invoiceDatabase.getdeveloperdata(userType);
						boolean check=invoiceDatabase.checkdeveloperhours(assigndata[0], ((String)(projectNumberComboBox.getItemAt(projectNumberComboBox.getSelectedIndex()))), userType, hours.getText(), date.getText());
						System.out.println(check);
						if(check){
							JOptionPane.showMessageDialog(null, "You have entered hours for this project on selected date");
						}else{
							invoiceDatabase.savedeveloperhoursdata(assigndata[0], ((String)(projectNumberComboBox.getItemAt(projectNumberComboBox.getSelectedIndex()))), userType, hours.getText(), date.getText(), date.getText(), people_date[3]);
							new EnterHours(myFrame,userRole,userType,userID);
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please enter assign developer details");
				}
			}
		});
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
    }
}
