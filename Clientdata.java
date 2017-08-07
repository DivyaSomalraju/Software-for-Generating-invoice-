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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Clientdata{
	public JTextField number,name,addressLine1,addressLine2,city,state,zip,email,contact,invoiceFreq,billingTerms,invoiceGrouping;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	String clientNumber="";
	JComboBox invoiceFreqComboBox,billingTermsComboBox,invoiceGroupingComboBox;
	public Clientdata(){
	}
	public Clientdata(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "View Clients Data"));
		
		JLabel titleLabel = new JLabel("VIEW CLIENTS");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList listOfClients=invoiceDatabase.selectclients();
		int totalColumns=12;
		String clientColumnNames[] = {"Number", "Name", "Address Line 1","Address Line 2", "City", "State", "Zip", "Email", "Contact", "Invoice Freq", "Billing Terms", "Invoice Grouping"};
		String outdata[][]=new String[0][totalColumns];
		if(listOfClients!=null && listOfClients.size()!=0){	
			outdata=new String[listOfClients.size()][totalColumns];
			Iterator iterator=listOfClients.iterator();
			int arrayIndex=0;
			while(iterator.hasNext()){
				String[] clientTokens=(String[])iterator.next();
				outdata[arrayIndex][0]=clientTokens[0];
				outdata[arrayIndex][1]=clientTokens[1];
				outdata[arrayIndex][2]=clientTokens[2];
				outdata[arrayIndex][3]=clientTokens[3];
				outdata[arrayIndex][4]=clientTokens[4];
				outdata[arrayIndex][5]=clientTokens[5];
				outdata[arrayIndex][6]=clientTokens[6];
				outdata[arrayIndex][7]=clientTokens[7];
				outdata[arrayIndex][8]=clientTokens[8];
				outdata[arrayIndex][9]=clientTokens[9];
				outdata[arrayIndex][10]=clientTokens[10];
				outdata[arrayIndex][11]=clientTokens[11];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable clientTable = new JTable(outdata, clientColumnNames);
	    ListSelectionModel rowSelection = clientTable.getSelectionModel();
	    rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    rowSelection.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e) {
	    		rowIndexPointer=""+clientTable.getSelectedRow();
	    		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
	    		ArrayList listOfClients=invoiceDatabase.selectclients();
	    		String[] client=(String[])listOfClients.get(clientTable.getSelectedRow());
	    		clientNumber=client[0];
	    		number.setText(client[0]);
	    		name.setText(client[1]);
	    		addressLine1.setText(client[2]);
	    		addressLine2.setText(client[3]);
	    		city.setText(client[4]);
	    		state.setText(client[5]);
	    		zip.setText(client[6]);
	    		email.setText(client[7]);
	    		contact.setText(client[8]);
	    		invoiceFreqComboBox.setSelectedItem(client[9]);
	    		billingTermsComboBox.setSelectedItem(client[10]);
	    		invoiceGroupingComboBox.setSelectedItem(client[11]);
	    	}
	    });	    
		JScrollPane scrollPane = new JScrollPane(clientTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton addClientButton = new JButton("Add Client");
		addClientButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		addClientButton.setBounds(50, 600, 200, 30);		
		myFrame.add(addClientButton);
		
		JButton updateClientButton = new JButton("Update Client");
		updateClientButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		updateClientButton.setBounds(260, 600, 200, 30);
		myFrame.add(updateClientButton);
		
		JButton deleteClientButton = new JButton("InActive Client");
		deleteClientButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		deleteClientButton.setBounds(470, 600, 200, 30);
		myFrame.add(deleteClientButton);
		
		titleLabel = new JLabel("Number");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		number = new JTextField();
		if(rowIndexPointer.trim().length()!=0){
			number.setEditable(false);
		}
		number.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		number.setBounds(250, 148, 250, 25);
		myFrame.add(number);
		
		titleLabel = new JLabel("Name");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 100, 25);
		myFrame.add(titleLabel);
		
		name = new JTextField();
		name.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		name.setBounds(250, 178, 250, 25);
		myFrame.add(name);
		
		titleLabel = new JLabel("Address Line1");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 210, 125, 25);
		myFrame.add(titleLabel);
		
		addressLine1 = new JTextField();
		addressLine1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addressLine1.setBounds(250, 208, 250, 25);
		myFrame.add(addressLine1);

		titleLabel = new JLabel("Address Line2");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 240, 100, 25);
		myFrame.add(titleLabel);
		
		addressLine2 = new JTextField();
		addressLine2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addressLine2.setBounds(250, 238, 250, 25);
		myFrame.add(addressLine2);
		
		titleLabel = new JLabel("City");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 280, 100, 25);
		myFrame.add(titleLabel);
		
		city = new JTextField();
		city.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		city.setBounds(250, 278, 250, 25);
		myFrame.add(city);
		
		titleLabel = new JLabel("State");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 310, 120, 25);
		myFrame.add(titleLabel);
		
		state = new JTextField();
		state.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		state.setBounds(250, 308, 250, 25);
		myFrame.add(state);
		
		titleLabel = new JLabel("Zip");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 340, 125, 25);
		myFrame.add(titleLabel);
		
		zip = new JTextField();
		zip.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		zip.setBounds(250, 338, 250, 25);
		myFrame.add(zip);
		
		titleLabel = new JLabel("Email");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 380, 125, 25);
		myFrame.add(titleLabel);
		
		email = new JTextField();
		email.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		email.setBounds(250, 378, 250, 25);
		myFrame.add(email);

		titleLabel = new JLabel("Contact");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 410, 120, 25);
		myFrame.add(titleLabel);
		
		contact = new JTextField();
		contact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		contact.setBounds(250, 408, 250, 25);
		myFrame.add(contact);
		
		titleLabel = new JLabel("Invoice Freq");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 440, 125, 25);
		myFrame.add(titleLabel);
		
		String invoiceFreqLabels[] = {"Weekly","BiWeekly"};
	    invoiceFreqComboBox = new JComboBox(invoiceFreqLabels);	    
	    invoiceFreqComboBox.setMaximumRowCount(7);
	    invoiceFreqComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    invoiceFreqComboBox.setSelectedIndex(0);
	    invoiceFreqComboBox.setBounds(250, 438, 250, 25);
	    myFrame.add(invoiceFreqComboBox);
		
		titleLabel = new JLabel("Billing Terms");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 480, 125, 25);
		myFrame.add(titleLabel);
		
		String billingTermsLabels[] = {"Net 10","Net 20", "Net 30"};
	    billingTermsComboBox = new JComboBox(billingTermsLabels);	    
	    billingTermsComboBox.setMaximumRowCount(7);
	    billingTermsComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    billingTermsComboBox.setSelectedIndex(0);
	    billingTermsComboBox.setBounds(250, 478, 250, 25);
	    myFrame.add(billingTermsComboBox);

		titleLabel = new JLabel("Invoice Grouping");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 510, 120, 25);
		myFrame.add(titleLabel);
		
		String invoiceGroupingLabels[] = {"Project","Invoice"};
	    invoiceGroupingComboBox = new JComboBox(invoiceGroupingLabels);	    
	    invoiceGroupingComboBox.setMaximumRowCount(7);
	    invoiceGroupingComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    invoiceGroupingComboBox.setSelectedIndex(0);
	    invoiceGroupingComboBox.setBounds(250, 508, 250, 25);
	    myFrame.add(invoiceGroupingComboBox);
		
				
		addClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(number!=null && name!=null && addressLine1!=null && city!=null && state!=null && zip!=null && email!=null && contact!=null && invoiceFreqComboBox!=null && billingTermsComboBox!=null && invoiceGroupingComboBox!=null 
				&& number.getText().trim().length()!=0 && name.getText().trim().length()!=0 && addressLine1.getText().trim().length()!=0 && city.getText().trim().length()!=0 && state.getText().trim().length()!=0 && zip.getText().trim().length()!=0 
				&& email.getText().trim().length()!=0 && contact.getText().trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.saveclients(number.getText().trim(), name.getText().trim(), addressLine1.getText().trim(), addressLine2.getText().trim(), city.getText().trim(), state.getText().trim(), zip.getText().trim(), email.getText().trim(), contact.getText().trim(), ((String)(invoiceFreqComboBox.getItemAt(invoiceFreqComboBox.getSelectedIndex()))), ((String)(billingTermsComboBox.getItemAt(billingTermsComboBox.getSelectedIndex()))), ((String)(invoiceGroupingComboBox.getItemAt(invoiceGroupingComboBox.getSelectedIndex()))));
						new Clientdata(myFrame,userRole,userType,userID);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please enter all clients details");
				}
			}
		});
		
		updateClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(rowIndexPointer.trim().length()!=0){
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
						invoiceDatabase.updateclients(clientNumber,number.getText().trim(), name.getText().trim(), addressLine1.getText().trim(), addressLine2.getText().trim(), city.getText().trim(), state.getText().trim(), zip.getText().trim(), email.getText().trim(), contact.getText().trim(), ((String)(invoiceFreqComboBox.getItemAt(invoiceFreqComboBox.getSelectedIndex()))), ((String)(billingTermsComboBox.getItemAt(billingTermsComboBox.getSelectedIndex()))), ((String)(invoiceGroupingComboBox.getItemAt(invoiceGroupingComboBox.getSelectedIndex()))));
						clientNumber="";
						rowIndexPointer="";
						new Clientdata(myFrame,userRole,userType,userID);
					}else{
						JOptionPane.showMessageDialog(null, "Please select table row details");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});
		
		deleteClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(rowIndexPointer.trim().length()!=0){
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();					
						invoiceDatabase.inactiveclients(number.getText().trim());
						clientNumber="";
						rowIndexPointer="";
						new Clientdata(myFrame,userRole,userType,userID);
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
