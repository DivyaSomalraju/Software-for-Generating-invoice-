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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ApproveHours {
	JTextField date,hours;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	String[] approvedata=null;
	public ApproveHours(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Approve / Update Hours Data"));
		
		JLabel titleLabel = new JLabel("APPROVE/UPDATE HOURS");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		final ArrayList listOfProjects=invoiceDatabase.developerhoursapprovedata(userType);
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
		ListSelectionModel rowSelection = assignprojectTable.getSelectionModel();
	    rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    rowSelection.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e) {
	    		rowIndexPointer=""+assignprojectTable.getSelectedRow();
	    		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
	    		approvedata=(String[])listOfProjects.get(assignprojectTable.getSelectedRow());
	    		date.setText(approvedata[4]);
	    		hours.setText(approvedata[3]);
	    	}
	    });
	    date = new JTextField();
		date.setEditable(false);
		date.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		date.setBounds(250, 148, 250, 25);
		myFrame.add(date);
		
		titleLabel = new JLabel("Hours");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 100, 25);
		myFrame.add(titleLabel);
		
		hours = new JTextField();
		hours.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		hours.setBounds(250, 178, 250, 25);
		myFrame.add(hours);  
		JScrollPane scrollPane = new JScrollPane(assignprojectTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);		
		JButton saveButton = new JButton("Approve & Update");
		saveButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		saveButton.setBounds(250, 300, 200, 30);		
		myFrame.add(saveButton);
		
		titleLabel = new JLabel("Date");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(date!=null && hours!=null 
						&& date.getText().trim().length()!=0 && hours.getText().trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.updatedeveloperhoursdata(approvedata[0], approvedata[1],approvedata[2], date.getText().trim(), hours.getText().trim());
						new ApproveHours(myFrame,userRole,userType,userID);
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
