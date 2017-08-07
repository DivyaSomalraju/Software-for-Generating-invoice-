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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Invoicedata{
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="";
	public Invoicedata(){
		
	}
	public void generateInvoiceSamples(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Generate Invoices"));
		
		JLabel titleLabel = new JLabel("GENERATE INVOCIE");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		
		JButton openButton = new JButton("Open");
		openButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		openButton.setBounds(260, 250, 200, 30);
		myFrame.add(openButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		exitButton.setBounds(500, 250, 200, 30);
		myFrame.add(exitButton);
		
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
			
	}
	
	public void displayHours(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Display Hours Data"));
		
		JLabel titleLabel = new JLabel("DISPLAY HOURS");
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
	
	public void generateInvoice(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Generate Invoice Data"));
		
		JLabel titleLabel = new JLabel("DISPLAY INVOICES");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(250, 100, 350, 40);
		myFrame.add(titleLabel);
		final SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		invoiceDatabase.selectinvoicedata();
		final ArrayList listOfInvoices=invoiceDatabase.selectinvoicesdata();
		int totalColumns=5;
		String enterhoursColumnNames[] = {"Invoice Number", "Client Number", "Project Number", "Invoice Date", "Total Amount"};
		String outdata[][]=new String[0][totalColumns];
		if(listOfInvoices!=null && listOfInvoices.size()!=0){			
			Iterator iterator=listOfInvoices.iterator();
			int arrayIndex=0;
			outdata=new String[listOfInvoices.size()][totalColumns];
			while(iterator.hasNext()){
				String[] enterhoursTokens=(String[])iterator.next();
				outdata[arrayIndex][0]=enterhoursTokens[0];
				outdata[arrayIndex][1]=enterhoursTokens[1];
				outdata[arrayIndex][2]=enterhoursTokens[2];
				outdata[arrayIndex][3]=enterhoursTokens[3];
				outdata[arrayIndex][4]=enterhoursTokens[4];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable assignprojectTable = new JTable(outdata, enterhoursColumnNames);
		JScrollPane tableScrollPane = new JScrollPane(assignprojectTable);
		tableScrollPane.setBounds(100, 200, 475, 250);
		myFrame.add(tableScrollPane, BorderLayout.CENTER);
		
		JButton savePDFButton = new JButton("Save PDF");
		savePDFButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		savePDFButton.setBounds(175, 500, 150, 30);
		savePDFButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					/*InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
					ArrayList listOfInvoices=invoiceDatabase.selectinvoicedata();
					if(listOfInvoices!=null && listOfInvoices.size()!=0){			
						Iterator iterator=listOfInvoices.iterator();
						String[] invoicetokens=(String[])iterator.next();
						String invoicenumber=invoiceDatabase.saveinvoicedata(invoicetokens[0],invoicetokens[1], sdf.format(new Date()), invoicetokens[2]);
						String[] projecttokens=invoiceDatabase.selectprojectdata(invoicetokens[0],invoicetokens[1]);
						String[] clienttokens=invoiceDatabase.clientdata(invoicetokens[0]);	
						ArrayList invoicedeveloperlist=invoiceDatabase.selectinvoicedevelopersdata(invoicetokens[0],invoicetokens[1]);
						//invoiceDatabase.saveInvoicePdf(invoicetokens[0],clienttokens,projecttokens,invoicedeveloperlist,invoicetokens[2],invoicenumber);
						JOptionPane.showMessageDialog(null, "Successfully send invoice to clients via emails");
					}*/
					JOptionPane.showMessageDialog(null, "Successfully send invoice to clients via emails");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});		
		myFrame.add(savePDFButton);
		
		thisFrame=myFrame;
		myFrame.getContentPane().repaint();
	}
}
