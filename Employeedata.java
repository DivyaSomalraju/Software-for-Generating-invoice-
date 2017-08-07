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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Employeedata{
	JTextField name,title,billrate,role;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	String originalname="";
	JComboBox roleComboBox;
	public Employeedata(){
		
	}
	public Employeedata(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "View Employees Data"));
		
		JLabel titleLabel = new JLabel("VIEW EMPLOYEES");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList listOfEmployees=invoiceDatabase.selectpeopledata();
		int totalColumns=4;
		String employeesColumnNames[] = {"Name", "Title", "Bill Rate", "Role"};
		String outdata[][]=new String[0][totalColumns];
		if(listOfEmployees!=null && listOfEmployees.size()!=0){			
			outdata=new String[listOfEmployees.size()][totalColumns];
			Iterator iterator=listOfEmployees.iterator();
			int arrayIndex=0;
			while(iterator.hasNext()){
				String[] tokens=(String[])iterator.next();
				outdata[arrayIndex][0]=tokens[0];
				outdata[arrayIndex][1]=tokens[1];
				outdata[arrayIndex][2]=tokens[2];
				outdata[arrayIndex][3]=tokens[3];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable employeeTable = new JTable(outdata, employeesColumnNames);
	    ListSelectionModel rowSelection = employeeTable.getSelectionModel();
	    rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    rowSelection.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e) {
	    		rowIndexPointer=""+employeeTable.getSelectedRow();
	    		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
	    		ArrayList listOfEmployees=invoiceDatabase.selectpeopledata();
	    		String[] employee=(String[])listOfEmployees.get(employeeTable.getSelectedRow());
	    		originalname=employee[0];
	    		name.setText(employee[0]);
	    		title.setText(employee[1]);
	    		billrate.setText(employee[3]);
	    		roleComboBox.setSelectedItem(employee[3]);
	    	}
	    });	    
		JScrollPane scrollPane = new JScrollPane(employeeTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		addEmployeeButton.setBounds(50, 600, 200, 30);		
		myFrame.add(addEmployeeButton);
		
		JButton updateEmployeeButton = new JButton("Update Employee");
		updateEmployeeButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		updateEmployeeButton.setBounds(260, 600, 200, 30);
		myFrame.add(updateEmployeeButton);
		
		JButton inactiveEmployeeButton = new JButton("InActive Employee");
		inactiveEmployeeButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		inactiveEmployeeButton.setBounds(470, 600, 200, 30);
		myFrame.add(inactiveEmployeeButton);
		
		titleLabel = new JLabel("Name");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		name = new JTextField();
		name.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		name.setBounds(250, 148, 250, 25);
		myFrame.add(name);
		
		titleLabel = new JLabel("Title");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 100, 25);
		myFrame.add(titleLabel);
		
		title = new JTextField();
		title.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		title.setBounds(250, 178, 250, 25);
		myFrame.add(title);
		
		titleLabel = new JLabel("Bill Rate");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 210, 125, 25);
		myFrame.add(titleLabel);
		
		billrate = new JTextField();
		billrate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		billrate.setBounds(250, 208, 250, 25);
		myFrame.add(billrate);

		titleLabel = new JLabel("Role");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 240, 100, 25);
		myFrame.add(titleLabel);
		
		String roleLabels[] = {"Accountant","Project Manager", "Developer"};
	    roleComboBox = new JComboBox(roleLabels);	    
	    roleComboBox.setMaximumRowCount(7);
	    roleComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    roleComboBox.setSelectedIndex(0);
	    roleComboBox.setBounds(250, 238, 250, 25);
	    myFrame.add(roleComboBox);
		
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name!=null && title!=null && billrate!=null && roleComboBox!=null
						&& name.getText().trim().length()!=0 && title.getText().trim().length()!=0 && billrate.getText().trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
						invoiceDatabase.savepeopledata(name.getText().trim(), title.getText().trim(), billrate.getText().trim(), ((String)(roleComboBox.getItemAt(roleComboBox.getSelectedIndex()))));
						new Employeedata(myFrame,userRole,userType,userID);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		});
		
		updateEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(rowIndexPointer.trim().length()!=0){
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.updatepeopledata(originalname,name.getText().trim(), title.getText().trim(), billrate.getText().trim(), ((String)(roleComboBox.getItemAt(roleComboBox.getSelectedIndex()))));
						rowIndexPointer="";
						new Employeedata(myFrame,userRole,userType,userID);
					}else{
						JOptionPane.showMessageDialog(null, "Please select table row details");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		inactiveEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(rowIndexPointer.trim().length()!=0){
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.inactivepeopledata(name.getText().trim());
						rowIndexPointer="";
						new Employeedata(myFrame,userRole,userType,userID);
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
