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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Projectdata{
	JTextField client,projectNumber,projectName,stateDate,endDate,status,projectManager,clientContact,budget;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	String projectnumber="";
	public Projectdata(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "View Projects Data"));
		
		JLabel titleLabel = new JLabel("VIEW PROJECTS");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList templistOfProjects=null;
		if(userRole!=null && userRole.trim().length()!=0 && userRole.equalsIgnoreCase("Project Manager")){
			templistOfProjects=invoiceDatabase.selectprojectdata(userType);
		}else{
			templistOfProjects=invoiceDatabase.selectprojectdata();
		}
		final ArrayList listOfProjects=templistOfProjects;
		int totalColumns=9;
		String projectColumnNames[] = {"Client", "Project Number", "Project Name", "State Date", "End Date", "Status","Project Manager", "Client Contact", "Budget"};
		String outdata[][]=new String[0][totalColumns];
		if(listOfProjects!=null && listOfProjects.size()!=0){
			outdata=new String[listOfProjects.size()][totalColumns];
			Iterator iterator=listOfProjects.iterator();
			int arrayIndex=0;
			while(iterator.hasNext()){
				String[] projectTokens=(String[])iterator.next();
				outdata[arrayIndex][0]=projectTokens[0];
				outdata[arrayIndex][1]=projectTokens[1];
				outdata[arrayIndex][2]=projectTokens[2];
				outdata[arrayIndex][3]=projectTokens[3];
				outdata[arrayIndex][4]=projectTokens[4];
				outdata[arrayIndex][5]=projectTokens[5];
				outdata[arrayIndex][6]=projectTokens[6];
				outdata[arrayIndex][7]=projectTokens[7];
				outdata[arrayIndex][8]=projectTokens[8];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable projectTable = new JTable(outdata, projectColumnNames);
	    ListSelectionModel rowSelection = projectTable.getSelectionModel();
	    rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    rowSelection.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e) {
	    		rowIndexPointer=""+projectTable.getSelectedRow();
	    		String[] project=(String[])listOfProjects.get(projectTable.getSelectedRow());
	    		projectnumber=project[1];
	    		client.setText(project[0]);
	    		projectNumber.setText(project[1]);
	    		projectName.setText(project[2]);
	    		stateDate.setText(project[3]);
	    		endDate.setText(project[4]);
	    		status.setText(project[5]);
	    		projectManager.setText(project[6]);
	    		clientContact.setText(project[7]);
	    		budget.setText(project[8]);
	    	}
	    });	    
		JScrollPane scrollPane = new JScrollPane(projectTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton addProjectButton = new JButton("Add Project");
		addProjectButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		addProjectButton.setBounds(50, 600, 200, 30);		
		if(userRole!=null && userRole.trim().length()!=0 && !(userRole.equalsIgnoreCase("Project Manager"))){
			myFrame.add(addProjectButton);
		}
		
		JButton updateProjectButton = new JButton("Update Project");
		updateProjectButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		updateProjectButton.setBounds(260, 600, 200, 30);
		myFrame.add(updateProjectButton);
		
		JButton inactiveProjectButton = new JButton("InActive Project");
		inactiveProjectButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		inactiveProjectButton.setBounds(470, 600, 200, 30);
		myFrame.add(inactiveProjectButton);
		
		titleLabel = new JLabel("Client");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		client = new JTextField();
		client.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		client.setBounds(250, 148, 250, 25);
		myFrame.add(client);
		
		titleLabel = new JLabel("Project Number");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 100, 25);
		myFrame.add(titleLabel);
		
		projectNumber = new JTextField();
		projectNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		projectNumber.setBounds(250, 178, 250, 25);
		myFrame.add(projectNumber);	
		
		titleLabel = new JLabel("Project Name");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 210, 100, 25);
		myFrame.add(titleLabel);
		
		projectName = new JTextField();
		projectName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		projectName.setBounds(250, 208, 250, 25);
		myFrame.add(projectName);	
		
		titleLabel = new JLabel("Start Date");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 240, 125, 25);
		myFrame.add(titleLabel);
		
		stateDate = new JTextField();
		stateDate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		stateDate.setBounds(250, 238, 250, 25);
		myFrame.add(stateDate);

		titleLabel = new JLabel("End Date");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 270, 100, 25);
		myFrame.add(titleLabel);
		
		endDate = new JTextField();
		endDate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		endDate.setBounds(250, 268, 250, 25);
		myFrame.add(endDate);
		
		titleLabel = new JLabel("Status");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 300, 100, 25);
		myFrame.add(titleLabel);
		
		status = new JTextField();
		status.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		status.setBounds(250, 298, 250, 25);
		myFrame.add(status);
		
		titleLabel = new JLabel("Project Manager");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 330, 120, 25);
		myFrame.add(titleLabel);
		
		projectManager = new JTextField();
		projectManager.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		projectManager.setBounds(250, 328, 250, 25);
		myFrame.add(projectManager);
		
		titleLabel = new JLabel("Client Contact");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 360, 125, 25);
		myFrame.add(titleLabel);
		
		clientContact = new JTextField();
		clientContact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		clientContact.setBounds(250, 358, 250, 25);
		myFrame.add(clientContact);
		
		titleLabel = new JLabel("Budget");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 390, 125, 25);
		myFrame.add(titleLabel);
		
		budget = new JTextField();
		budget.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		budget.setBounds(250, 388, 250, 25);
		myFrame.add(budget);

		addProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(client!=null && projectNumber!=null && projectName!=null && stateDate!=null && endDate!=null && status!=null && projectManager!=null && clientContact!=null && budget!=null
						&& client.getText().trim().length()!=0 && projectNumber.getText().trim().length()!=0 && projectName.getText().trim().length()!=0 && stateDate.getText().trim().length()!=0 && endDate.getText().trim().length()!=0 && status.getText().trim().length()!=0 && projectManager.getText().trim().length()!=0 && clientContact.getText().trim().length()!=0 && budget.getText().trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.saveprojects(client.getText().trim(), projectNumber.getText().trim(), projectName.getText().trim(), stateDate.getText().trim(), endDate.getText().trim(), status.getText().trim(), projectManager.getText().trim(), clientContact.getText().trim(), budget.getText().trim());
						new Projectdata(myFrame,userRole,userType,userID);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please enter correct details");
				}
			}
		});
		
		updateProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(rowIndexPointer.trim().length()!=0){
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
						invoiceDatabase.updateprojects(projectnumber, client.getText().trim(), projectNumber.getText().trim(), projectName.getText().trim(), stateDate.getText().trim(), endDate.getText().trim(), status.getText().trim(), projectManager.getText().trim(), clientContact.getText().trim(), budget.getText().trim());
						projectnumber="";
						rowIndexPointer="";
						new Projectdata(myFrame,userRole,userType,userID);
					}else{
						JOptionPane.showMessageDialog(null, "Please select table row details");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		inactiveProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(rowIndexPointer.trim().length()!=0){
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.inactiveprojects(projectNumber.getText());
						projectnumber="";
						rowIndexPointer="";
						new Projectdata(myFrame,userRole,userType,userID);
					}else{
						JOptionPane.showMessageDialog(null, "Please select table row details");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
    }
}
