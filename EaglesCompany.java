package invoicecreating;

import invoicedatabase.InvoiceDatabase;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EaglesCompany extends JFrame {
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					EaglesCompany companyhome = new EaglesCompany();
					String current="";					
					companyhome.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    companyhome.setBounds(250, 25, 1100, 700);
				    JPanel homePanel = new JPanel();
				    homePanel.setBackground(Color.WHITE);
				    homePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				    homePanel.setLayout(null);
				    companyhome.setContentPane(homePanel);				    
				    companyhome.setVisible(true);
				    /*InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
				    invoiceDatabase.importcompanydataintodatabase();
				    invoiceDatabase.importclientsdataintodatabase();
				    invoiceDatabase.importprojectsdataintodatabase();
				    invoiceDatabase.importemployeesdataintodatabase();*/
				    new UsersLogin(companyhome);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
