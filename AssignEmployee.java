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

public class AssignEmployee{
	JTextField projectNumber,projectName,projectManager,developerName;
	JFrame thisFrame;
	String rowIndexPointer="";
	String thisUserRole="";
	String thisUserType="";
	String thisUserId="",clientNumber="";
	public AssignEmployee(final JFrame myFrame,final String userRole,final String userType,final String userID){
		new UserOptions(myFrame,userRole,userType,userID);		
		thisUserRole=userRole;
		thisUserType=userType;
		thisUserId=userID;
		
		int length=150;
		myFrame.setTitle(String.format("%1$"+length+ "s", "Assign Developers to Projects"));
		
		JLabel titleLabel = new JLabel("ASSIGN DEVELOPER TO PROJECTS");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		titleLabel.setBounds(200, 100, 350, 40);
		myFrame.add(titleLabel);
		
		InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		ArrayList listOfProjects=null;
		if(userRole!=null && userRole.trim().length()!=0 && userRole.equalsIgnoreCase("Project Manager")){
			listOfProjects=invoiceDatabase.assigndeveloperdata(userType);
		}else{
			listOfProjects=invoiceDatabase.assigndeveloperdata();
		}
		int totalColumns=4;
		String assignprojectColumnNames[] = {"Project Number", "Project Name", "Project Manager", "Developer Name"};
		String outdata[][]=new String[0][totalColumns];
		if(listOfProjects!=null && listOfProjects.size()!=0){
			outdata=new String[listOfProjects.size()][totalColumns];
			Iterator iterator=listOfProjects.iterator();
			int arrayIndex=0;
			while(iterator.hasNext()){
				String[] assignprojectTokens=(String[])iterator.next();
				outdata[arrayIndex][0]=assignprojectTokens[0];
				outdata[arrayIndex][1]=assignprojectTokens[1];
				outdata[arrayIndex][2]=assignprojectTokens[2];
				outdata[arrayIndex][3]=assignprojectTokens[3];
				arrayIndex=arrayIndex+1;
			}			
		}
		
		final JTable assignprojectTable = new JTable(outdata, assignprojectColumnNames);
		JScrollPane scrollPane = new JScrollPane(assignprojectTable);
		scrollPane.setBounds(600, 200, 475, 250);
		myFrame.add(scrollPane, BorderLayout.CENTER);
		
		JButton addProjectButton = new JButton("Assign");
		addProjectButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		addProjectButton.setBounds(250, 350, 100, 30);		
		myFrame.add(addProjectButton);
		
		projectName = new JTextField();
		projectName.setEditable(false);
		projectName.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		projectName.setBounds(250, 178, 250, 25);
		myFrame.add(projectName);
		
		titleLabel = new JLabel("Project Manager");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 210, 125, 25);
		myFrame.add(titleLabel);
		
		projectManager = new JTextField();
		projectManager.setEditable(false);
		projectManager.setFont(new Font("Times New Roman",  Font.PLAIN, 14));
		projectManager.setBounds(250, 208, 250, 25);
		myFrame.add(projectManager);
		
		titleLabel = new JLabel("Project Number");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 150, 100, 25);
		myFrame.add(titleLabel);
		
		ArrayList tempprojectlist=invoiceDatabase.selectprojectdata();		
		if(userRole!=null && userRole.trim().length()!=0 && userRole.equalsIgnoreCase("Project Manager")){
			tempprojectlist=invoiceDatabase.selectprojectdata(userType);
		}else{
			tempprojectlist=invoiceDatabase.selectprojectdata();
		}
		final ArrayList projectlist=tempprojectlist;
		String projectNumberLabels[] = new String[0];
		if(projectlist!=null && projectlist.size()!=0){
			projectNumberLabels = new String[projectlist.size()];
			for(int val=0;val<projectlist.size();val++){
				String[] project_data = (String[])projectlist.get(val);
				if(val==0){
					clientNumber=project_data[0];
					projectName.setText(project_data[2]);
					projectManager.setText(project_data[6]);
				}
				projectNumberLabels[val] = project_data[1];
			}
		}
	    final JComboBox projectNumberComboBox = new JComboBox(projectNumberLabels);	    
	    //projectNumberComboBox.setMaximumRowCount(7);
	    projectNumberComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    projectNumberComboBox.setSelectedIndex(0);
	    projectNumberComboBox.setBounds(250, 148, 250, 25);
	    myFrame.add(projectNumberComboBox);
	    projectNumberComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] project_data=(String[])projectlist.get(projectNumberComboBox.getSelectedIndex());
				clientNumber=project_data[0];
				projectName.setText(project_data[2]);
				projectManager.setText(project_data[6]);
			}
	    });
		
		titleLabel = new JLabel("Project Name");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 180, 120, 25);
		myFrame.add(titleLabel);
		
		titleLabel = new JLabel("Developer Name");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLabel.setBounds(100, 240, 120, 25);
		myFrame.add(titleLabel);
		
		ArrayList listOfDevelopers=invoiceDatabase.developerdata();
		String developerNameLabels[] = new String[0];
		if(listOfDevelopers!=null && listOfDevelopers.size()!=0){
			developerNameLabels = new String[listOfDevelopers.size()];
			for(int val=0;val<listOfDevelopers.size();val++){
				developerNameLabels[val] = (String)listOfDevelopers.get(val);
			}
		}				
	    final JComboBox developerNameComboBox = new JComboBox(developerNameLabels);	    
	    //developerNameComboBox.setMaximumRowCount(7);
	    developerNameComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 16));
	    developerNameComboBox.setSelectedIndex(0);
	    developerNameComboBox.setBounds(250, 238, 250, 25);
	    myFrame.add(developerNameComboBox);

		addProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clientNumber!=null && clientNumber.trim().length()!=0){
					try{
						InvoiceDatabase invoiceDatabase=new InvoiceDatabase();						
						invoiceDatabase.saveassigndeveloperdata(clientNumber.trim(), ((String)(projectNumberComboBox.getItemAt(projectNumberComboBox.getSelectedIndex()))), ((String)(developerNameComboBox.getItemAt(developerNameComboBox.getSelectedIndex()))));
						new AssignEmployee(myFrame,userRole,userType,userID);
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
