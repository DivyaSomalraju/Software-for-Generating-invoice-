package invoicecreating;

import invoicedatabase.InvoiceDatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Reportsdata{
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	public Reportsdata(){
		
	}
	public void budgetReport(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Budget Report"));
		
		JLabel titleLabel = new JLabel("BUDGET REPORT");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList tempbudgetList=invoiceDatabase.selectinvoicesdata();
		ArrayList budgetList=null;
		if(tempbudgetList!=null && tempbudgetList.size()!=0){
			budgetList=new ArrayList();
			Iterator iterator=tempbudgetList.iterator();
			String[] invoicetokens=(String[])iterator.next();
			String[] projecttokens=invoiceDatabase.selectprojectdata(invoicetokens[1],invoicetokens[2]);
			String[] tokens=new String[4];
			tokens[0]=invoicetokens[0];
			tokens[1]=invoicetokens[1];
			tokens[2]=projecttokens[8];
			tokens[3]=invoicetokens[4];
			budgetList.add(tokens);
		}
		int totalColumns=4;
		String budgetColumnNames[] = {"Client Number","Project Number", "Budget", "Spent%"};
		String output[][]=new String[0][totalColumns];
		if(budgetList!=null && budgetList.size()!=0){			
			Iterator iterator=budgetList.iterator();
			int arrayIndex=0;
			output=new String[budgetList.size()][totalColumns];
			while(iterator.hasNext()){
				String[] budgettokens=(String[])iterator.next();
				output[arrayIndex][0]=budgettokens[0];
				output[arrayIndex][1]=budgettokens[1];
				output[arrayIndex][2]=budgettokens[2];
				output[arrayIndex][3]=""+String.format("%.2f",((Double.parseDouble(budgettokens[3])/Double.parseDouble(budgettokens[2]))*100))+"%";
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable budgetTable = new JTable(output, budgetColumnNames);
	    ListSelectionModel rowSelection = budgetTable.getSelectionModel();
	    rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    rowSelection.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e) {
	    		rowIndexPointer=""+budgetTable.getSelectedRow();
	    	}
	    });	    
		JScrollPane scrollPane = new JScrollPane(budgetTable);
		scrollPane.setBounds(100, 250, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		exitButton.setBounds(250, 600, 200, 30);
		myFrame.add(exitButton);	
		
		JLabel titleLabel1 = new JLabel("Type");
		titleLabel1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel1.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel1);
		
		String projectNumberLabels[] = {"All","All"};
	    final JComboBox projectNumberComboBox = new JComboBox(projectNumberLabels);	    
	    projectNumberComboBox.setMaximumRowCount(7);
	    projectNumberComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    projectNumberComboBox.setSelectedIndex(0);
	    projectNumberComboBox.setBounds(250, 148, 250, 25);
	    //myFrame.add(projectNumberComboBox);
		
	    JButton generateButton = new JButton("Generate Report");
	    generateButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
	    generateButton.setBounds(150, 200, 150, 25);
		//myFrame.add(generateButton);
	    
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
	}
	
	public void hoursReport(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Hours Report"));
		
		JLabel titleLabel = new JLabel("HOURS REPORT");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		final ArrayList listOfProjects=invoiceDatabase.developerhoursdata();
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
		JScrollPane tableScrollPane = new JScrollPane(assignprojectTable);
		tableScrollPane.setBounds(100, 200, 475, 250);
		myFrame.add(tableScrollPane, BorderLayout.CENTER);
		
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
	}
}
