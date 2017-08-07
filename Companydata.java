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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Companydata{
	JTextField name,addressLine1,addressLine2,city,state,zip;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	public Companydata(){
		
	}
	public Companydata(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Company Data"));
		
		JLabel titleLabel = new JLabel("COMPANY DATA");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		final InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList listOfCompanies=invoiceDatabase.selectcompanydata();
		int totalColumns=6;
		String companyColumnNames[] = {"Name", "Address Line1", "Address Line2","City", "State", "Zip"};
		String output[][]=new String[0][totalColumns];
		if(listOfCompanies!=null && listOfCompanies.size()!=0){
			output=new String[listOfCompanies.size()][totalColumns];
			Iterator iterator=listOfCompanies.iterator();
			int arrayIndex=0;
			while(iterator.hasNext()){
				String[] companyTokens=(String[])iterator.next();
				output[arrayIndex][0]=companyTokens[0];
				output[arrayIndex][1]=companyTokens[1];
				output[arrayIndex][2]=companyTokens[2];
				output[arrayIndex][3]=companyTokens[3];
				output[arrayIndex][4]=companyTokens[4];
				output[arrayIndex][5]=companyTokens[5];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable companyTable = new JTable(output, companyColumnNames);
	    ListSelectionModel rowSelection = companyTable.getSelectionModel();
	    rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    rowSelection.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e) {
	    		rowIndexPointer=""+companyTable.getSelectedRow();
	    	}
	    });	    
		JScrollPane scrollPane = new JScrollPane(companyTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton updateButton = new JButton("Update");
		updateButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		updateButton.setBounds(50, 600, 200, 30);		
		myFrame.add(updateButton);		
		
		String[] companydate= (String[])listOfCompanies.get(0);
		
		titleLabel = new JLabel("Name");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		name = new JTextField();
		name.setText(companydate[0]);
		name.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		name.setBounds(250, 148, 250, 25);
		myFrame.add(name);
		
		titleLabel = new JLabel("Address Line1");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 100, 25);
		myFrame.add(titleLabel);
		
		addressLine1 = new JTextField();
		addressLine1.setText(companydate[1]);
		addressLine1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addressLine1.setBounds(250, 178, 250, 25);
		myFrame.add(addressLine1);
		
		titleLabel = new JLabel("Address Line2");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 210, 125, 25);
		myFrame.add(titleLabel);
		
		addressLine2 = new JTextField();
		addressLine2.setText(companydate[2]);
		addressLine2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addressLine2.setBounds(250, 208, 250, 25);
		myFrame.add(addressLine2);

		titleLabel = new JLabel("City");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 240, 100, 25);
		myFrame.add(titleLabel);
		
		city = new JTextField();
		city.setText(companydate[3]);
		city.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		city.setBounds(250, 238, 250, 25);
		myFrame.add(city);
		
		titleLabel = new JLabel("State");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 280, 100, 25);
		myFrame.add(titleLabel);
		
		state = new JTextField();
		state.setText(companydate[4]);
		state.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		state.setBounds(250, 278, 250, 25);
		myFrame.add(state);
		
		titleLabel = new JLabel("Zip");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 310, 120, 25);
		myFrame.add(titleLabel);
		
		zip = new JTextField();
		zip.setText(companydate[5]);
		zip.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		zip.setBounds(250, 308, 250, 25);
		myFrame.add(zip);
		
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rowIndexPointer.trim().length()!=0){
					if(name!=null && addressLine1!=null && city!=null && state!=null && zip!=null && name.getText().trim().length()!=0 && addressLine1.getText().trim().length()!=0 && city.getText().trim().length()!=0 && state.getText().trim().length()!=0 && zip.getText().trim().length()!=0){
						try{
							invoiceDatabase.updatecompanydata(name.getText().trim(), addressLine1.getText().trim(), addressLine2.getText().trim(), city.getText().trim(), state.getText().trim(), zip.getText().trim());
							rowIndexPointer="";
							new Companydata(myFrame,userRole,userType,userID);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(null, "Please enter all company details");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please select table row details");
				}
			}
		});
		
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();	
	}
}
