package invoicedatabase;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceDatabase {
	private BaseFont baseFont,tempBF;
	private int pageNumber = 0;
	public void saveInvoicePdf(String filename,String[] client,String[] project,ArrayList developerlist,String totalamount,String invoicenumber,String startenddates){
		generateInvoicePDF(filename.trim()+".pdf",client,project,developerlist,totalamount,invoicenumber,startenddates);
	 }
	public Connection invoiceCreationDBConnection(){
		Connection sqlconnection=null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String mysqConnectionPath="jdbc:mysql://localhost/divya";
			sqlconnection =DriverManager.getConnection(mysqConnectionPath,"root","test");  
		}catch (Exception e){
			e.printStackTrace();
		}
		return sqlconnection;
	}	
	
	public void saveclients(String number,String name,String addressline1,String addressline2,String city,String state,String zip,String email,String contact,String invoicefreq,String billingterms,String invoicegrouping) throws SQLException {
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="insert into client_data (number,name,addressline1,addressline2,city,state,zip,email,contact,invoicefreq,billingterms,invoicegrouping,inactive) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setInt(1,Integer.parseInt(number));
				sqlstatement.setString(2,name);
				sqlstatement.setString(3,addressline1);
				sqlstatement.setString(4,addressline2);
				sqlstatement.setString(5,city);
				sqlstatement.setString(6,state);
				sqlstatement.setString(7,zip);
				sqlstatement.setString(8,email);
				sqlstatement.setString(9,contact);
				sqlstatement.setString(10,invoicefreq);
				sqlstatement.setString(11,billingterms);
				sqlstatement.setString(12,invoicegrouping);
				sqlstatement.setString(13,"N");    		
				sqlstatement.addBatch();
				sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateclients(String originalnumber,String number,String name,String addressline1,String addressline2,String city,String state,String zip,String email,String contact,String invoicefreq,String billingterms,String invoicegrouping) throws SQLException {
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update client_data set number="+number+",name='"+name+"',addressline1='"+addressline1+"',addressline2='"+addressline2+"',city='"+city+"',state='"+state+"',zip="+zip+",email='"+email+"',contact='"+contact+"',invoicefreq='"+invoicefreq+"',billingterms='"+billingterms+"',invoicegrouping='"+invoicegrouping+"' where number="+originalnumber;
				sqlstatement=sqlconnection.createStatement();
				sqlstatement.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void inactiveclients(String number) throws SQLException {
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update client_data set inactive='Y' where number="+number;
				sqlstatement=sqlconnection.createStatement();
				sqlstatement.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void inactiveprojects(String number) throws SQLException {
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update project_data set inactive='Y' where projectnumber="+number;
				sqlstatement=sqlconnection.createStatement();
				sqlstatement.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void inactivepeopledata(String name) throws SQLException {
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update people_data set inactive='Y' where name='"+name+"'";
				sqlstatement=sqlconnection.createStatement();
				sqlstatement.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveprojects(String client,String projectnumber,String projectname,String startdate,String enddate,String status,String projectmanager,String clientcontact,String budget) throws SQLException {
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="insert into project_data (client,projectnumber,projectname,startdate,enddate,status,projectmanager,clientcontact,budget,inactive) values (?,?,?,?,?,?,?,?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setInt(1,Integer.parseInt(client));
				sqlstatement.setInt(2,Integer.parseInt(projectnumber));
				sqlstatement.setString(3,projectname);
				sqlstatement.setString(4,startdate);
				sqlstatement.setString(5,enddate);
				sqlstatement.setString(6,status);
				sqlstatement.setString(7,projectmanager);
				sqlstatement.setString(8,clientcontact);
				sqlstatement.setDouble(9,Integer.parseInt(budget));
				sqlstatement.setString(10,"N");    		
				sqlstatement.addBatch();
				sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateprojects(String originalnumber,String client,String projectnumber,String projectname,String startdate,String enddate,String status,String projectmanager,String clientcontact,String budget) throws SQLException {
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update project_data set client="+client+",projectnumber="+projectnumber+",projectname='"+projectname+"',startdate='"+startdate+"',enddate='"+enddate+"',status='"+status+"',projectmanager='"+projectmanager+"',clientcontact='"+clientcontact+"',budget="+budget+" where projectnumber="+originalnumber;
				System.out.println(sql);
				sqlstatement=sqlconnection.createStatement();
				sqlstatement.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void savepeopledata(String name,String title,String billrate,String role) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String password="";
				if(role.equalsIgnoreCase("Project Manager")){
					password="projectmanager";
				}else if(role.equalsIgnoreCase("Developer")){
					password="developer";
				}else if(role.equalsIgnoreCase("Accountant")){
					password="accountant";
				}
				String sql="insert into people_data (name,title,billrate,role,inactive,password) values (?,?,?,?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setString(1,name);
				sqlstatement.setString(2,title);
				sqlstatement.setString(4,role);
				sqlstatement.setInt(3,Integer.parseInt(billrate));
				sqlstatement.setString(5,"N"); 
				sqlstatement.setString(6,password);
				sqlstatement.addBatch();
				sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void updatepeopledata(String originalname,String name,String title,String billrate,String role) throws SQLException{
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update people_data set name='"+name+"',title='"+title+"',billrate="+billrate+",role='"+role+"' where name='"+originalname+"'";
				System.out.println(sql);
				sqlstatement=sqlconnection.createStatement();
				sqlstatement.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void savecompanydata(String name,String addressline1,String addressline2,String city,String state,String zip) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="insert into company_data (name,addressline1,addressline2,city,state,zip) values (?,?,?,?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setString(1,name);
				sqlstatement.setString(2,addressline1);
				sqlstatement.setString(3,addressline2);	    		
				sqlstatement.setString(4,city);
				sqlstatement.setString(5,state);
				sqlstatement.setInt(6,Integer.parseInt(zip));
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveassigndeveloperdata(String client,String projectnumber,String developername) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="insert into developer_assignto_project_data (client,projectnumber,developername) values (?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setInt(1,Integer.parseInt(client));
				sqlstatement.setInt(2,Integer.parseInt(projectnumber));
				sqlstatement.setString(3,developername);
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String saveinvoicedata(String client,String projectnumber,String invoicedate,String amount) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		String invoicenumber=null;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("hhmmss");
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="insert into invoice_data (invoicenumber,client,projectnumber,invoicedate,amount) values (?,?,?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				invoicenumber=new Random().nextInt(6)+sdf.format(new Date());
				sqlstatement.setInt(1,Integer.parseInt(invoicenumber));				
				sqlstatement.setInt(2,Integer.parseInt(client));
				sqlstatement.setInt(3,Integer.parseInt(projectnumber));
				sqlstatement.setString(4,invoicedate);
				sqlstatement.setInt(5,Integer.parseInt(amount));
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return invoicenumber;
	}
	
	public void savedeveloperhoursdata(String client,String projectnumber,String developername,String workhours,String startdate,String enddate,String billrate) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="insert into developer_hours_data (client,projectnumber,developername,workhours,startdate,enddate,billrate,hoursapprove) values (?,?,?,?,?,?,?,?)";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setInt(1,Integer.parseInt(client));
				sqlstatement.setInt(2,Integer.parseInt(projectnumber));
				sqlstatement.setString(3,developername);
				sqlstatement.setInt(4,Integer.parseInt(workhours));
				sqlstatement.setString(5,startdate);
				sqlstatement.setString(6,enddate);
				sqlstatement.setInt(7,Integer.parseInt(billrate));
				sqlstatement.setString(8,"PENDING");
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void savepassworddata(String type,String password) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update people_data set password=? where role=?";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setString(1,password);
				sqlstatement.setString(2,type);
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void updatedeveloperhoursdata(String client,String projectnumber,String developername,String date,String hours) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update developer_hours_data set workhours=?,hoursapprove=? where startdate=? and client=? and projectnumber=? and developername=?";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setInt(1,Integer.parseInt(hours));
				sqlstatement.setString(2,"APPROVED");
				sqlstatement.setString(3,date);
				sqlstatement.setString(4,client);
				sqlstatement.setString(5,projectnumber);
				sqlstatement.setString(6,developername);
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void updatecompanydata(String name,String addressline1,String addressline2,String city,String state,String zip) throws SQLException{
		Connection sqlconnection=null;
		PreparedStatement sqlstatement=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				String sql="update company_data set name=?,addressline1=?,addressline2=?,city=?,state=?,zip=?";
				sqlstatement=sqlconnection.prepareStatement(sql);
				sqlstatement.setString(1,name);
				sqlstatement.setString(2,addressline1);
				sqlstatement.setString(3,addressline2);	    		
				sqlstatement.setString(4,city);
				sqlstatement.setString(5,state);
				sqlstatement.setInt(6,Integer.parseInt(zip));
				sqlstatement.addBatch();
	    		sqlstatement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList selectpeopledata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from people_data where inactive='N'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[4];
	    				people_data[0]=rs.getString("name");
	    				people_data[1]=rs.getString("title");
	    				people_data[2]=rs.getString("role");
	    				people_data[3]=""+rs.getInt("billrate");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public boolean checkdeveloperhours(String client,String projectnumber,String developername,String workhours,String startdate){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		boolean check=false;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from developer_hours_data where client='"+client+"' and projectnumber='"+projectnumber+"' and developername='"+developername+"' and startdate='"+startdate+"'";
	            System.out.println(sql);
				ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			while(rs.next()){
	    				if(rs.getString("startdate")!=null && rs.getString("startdate").trim().length()!=0){
		    				System.out.println(rs.getString("startdate")+"  "+rs.getString("projectnumber"));
	    					check=true;
	    				}
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return check;
    }
	
	public String previousClientInvoiceGeneratedDate(String clientnumber){
		Connection mysqlconnection=null;
		Statement stmt=null;
		String lastInvoiceDate=null;
		try{
			mysqlconnection = invoiceCreationDBConnection();;
			if(mysqlconnection!=null){
	            stmt = mysqlconnection.createStatement();
	            ResultSet rs = stmt.executeQuery("select max(invoicedate) from invoice_data where client="+clientnumber);
	    		if(rs!=null){
	    			while(rs.next()){
	    				lastInvoiceDate=rs.getString("max(invoicedate)");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }finally{
		     try{
		    	 if(stmt!=null){
			    	 stmt.close();
			     }
		         if(mysqlconnection!=null)
		        	 mysqlconnection.close();
		      }catch(Exception e){
		         e.printStackTrace();
		      }
		}
        return lastInvoiceDate;
    }
	
	public ArrayList selectinvoicesdata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from invoice_data";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[5];
	    				people_data[0]=rs.getString("invoicenumber");
	    				people_data[1]=rs.getString("client");
	    				people_data[2]=rs.getString("projectnumber");
	    				people_data[3]=rs.getString("invoicedate");
	    				people_data[4]=rs.getString("amount");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList selectinvoicedata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList invoicedatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				try{
					String sql="select distinct client from developer_hours_data where hoursapprove='APPROVED'";
		            ResultSet rs = sqlstatement.executeQuery(sql);
		    		if(rs!=null){
		    			Calendar tempcalendar=Calendar.getInstance();
		    			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		    			while(rs.next()){
		    				String[] clienttokens=clientdata(rs.getString("client"));
		    				String previousClientInvoiceGeneratedDate=previousClientInvoiceGeneratedDate(rs.getString("client"));
		    				if(previousClientInvoiceGeneratedDate!=null && previousClientInvoiceGeneratedDate.trim().length()!=0){
		    					tempcalendar.setTime(sdf.parse(previousClientInvoiceGeneratedDate));
							}
		    				if(previousClientInvoiceGeneratedDate==null){
		    					if(clienttokens[9].equalsIgnoreCase("Weekly")){
		    						Calendar calendar = Calendar.getInstance();
		    						calendar.add(calendar.DATE, -3);
									String enddate=sdf.format(calendar.getTime());
									calendar = Calendar.getInstance();
									calendar.add(calendar.DATE, -9);
									String startdate=sdf.format(calendar.getTime());
									generatePdf(clienttokens[0],startdate,enddate);
		    					}
		    					if(clienttokens[9].equalsIgnoreCase("BiWeekly")){
		    						Calendar calendar = Calendar.getInstance();
		    						calendar.add(calendar.DATE, -3);
									String enddate=sdf.format(calendar.getTime());
									calendar = Calendar.getInstance();
									int num=-16;
									calendar.add(calendar.DATE, num);
									String startdate=sdf.format(calendar.getTime());
									generatePdf(clienttokens[0],startdate,enddate);
		    					}
		    					if(clienttokens[9].equalsIgnoreCase("Monthly")){
		    						Calendar calendar = Calendar.getInstance();
		    						calendar.add(calendar.DATE, -3);
									String enddate=sdf.format(calendar.getTime());
									calendar = Calendar.getInstance();
									int num=-32;
									calendar.add(calendar.DATE, num);
									String startdate=sdf.format(calendar.getTime());
									generatePdf(clienttokens[0],startdate,enddate);
		    					}
		    					if(clienttokens[9].equalsIgnoreCase("Monthly-Cal")){
		    						SimpleDateFormat secondsimpledateformat=new SimpleDateFormat("MM");
		    						SimpleDateFormat firstsimpledateformat=new SimpleDateFormat("yyyy");
		    						Calendar calendar=Calendar.getInstance();
		    						calendar.add(calendar.MONTH, -1);
		    						int month=Integer.parseInt(secondsimpledateformat.format(calendar.getTime()));
		    						String enddate="";
		    						if(month==2 && (Integer.parseInt(firstsimpledateformat.format(calendar.getTime())))%4==0){
		    							enddate="29-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
		    						}else if(month==2 && (Integer.parseInt(firstsimpledateformat.format(calendar.getTime())))%4!=0){
		    							enddate="28-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
		    						}else if(month==4 || month==6 || month==9 || month==9){
		    							enddate="30-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
		    						}else{
		    							enddate="31-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
		    						}
		    						String startdate="01-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
		    						generatePdf(clienttokens[0],startdate,enddate);
		    					}
		    				}else{ 
			    				if(clienttokens[9].equalsIgnoreCase("Weekly")){							
			    					tempcalendar.add(tempcalendar.DATE, 7);
									if(previousClientInvoiceGeneratedDate!=null && previousClientInvoiceGeneratedDate.trim().length()!=0 && sdf.format(tempcalendar.getTime()).equalsIgnoreCase(sdf.format(new Date()))){							
										Calendar calendar=Calendar.getInstance();
										calendar.add(calendar.DATE,-3);
										String enddate=sdf.format(calendar.getTime());
										calendar = Calendar.getInstance();
										int num=-9;
										calendar.add(calendar.DATE,num);
										String startdate=sdf.format(calendar.getTime());
										generatePdf(clienttokens[0],startdate,enddate);
									}
								}
			    				if(clienttokens[9].equalsIgnoreCase("BiWeekly")){
			    					tempcalendar.add(tempcalendar.DATE,14);
									if(previousClientInvoiceGeneratedDate!=null && previousClientInvoiceGeneratedDate.trim().length()!=0 && sdf.format(tempcalendar.getTime()).equalsIgnoreCase(sdf.format(new Date()))){
				    					Calendar calendar=Calendar.getInstance();
				    					calendar.add(calendar.DATE,3);
										String enddate=sdf.format(calendar.getTime());
										calendar=Calendar.getInstance();
										int num=-16;
										calendar.add(calendar.DATE,num);
										String startdate=sdf.format(calendar.getTime());
										generatePdf(clienttokens[0],startdate,enddate);
									}
		    					}
			    				if(clienttokens[9].equalsIgnoreCase("Monthly")){
			    					tempcalendar.add(tempcalendar.DATE,30);
									if(previousClientInvoiceGeneratedDate!=null && previousClientInvoiceGeneratedDate.trim().length()!=0 && sdf.format(tempcalendar.getTime()).equalsIgnoreCase(sdf.format(new Date()))){
										Calendar calendar = Calendar.getInstance();
										calendar.add(calendar.DATE,-3);
										String enddate=sdf.format(calendar.getTime());
										calendar=Calendar.getInstance();
										int num=-32;
										calendar.add(calendar.DATE,num);
										String startdate=sdf.format(calendar.getTime());
										generatePdf(clienttokens[0],startdate,enddate);
									}
		    					}
			    				if(clienttokens[9].equalsIgnoreCase("Monthly-Cal")){
			    					for(int monthvalue=Calendar.JANUARY;monthvalue<=Calendar.DECEMBER;monthvalue++){
			    					    GregorianCalendar gregorianCalendar = new GregorianCalendar(2016,monthvalue,1);
			    					    gregorianCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
			    					    gregorianCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			    					    if(sdf.format(gregorianCalendar.getTime()).equalsIgnoreCase(sdf.format(new Date()))){
			    					    	SimpleDateFormat secondsimpledateformat=new SimpleDateFormat("MM");
				    						SimpleDateFormat firstsimpledateformat=new SimpleDateFormat("yyyy");
				    						Calendar calendar = Calendar.getInstance();
				    						calendar.add(calendar.MONTH, -1);
				    						int month=Integer.parseInt(secondsimpledateformat.format(calendar.getTime()));
				    						String enddate="";
				    						if(month==2 && (Integer.parseInt(firstsimpledateformat.format(calendar.getTime())))%4==0){
				    							enddate="29-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
				    						}else if(month==2 && (Integer.parseInt(firstsimpledateformat.format(calendar.getTime())))%4!=0){
				    							enddate="28-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
				    						}else if(month==4 || month==6 || month==9 || month==9){
				    							enddate="30-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
				    						}else{
				    							enddate="31-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
				    						}
				    						String startdate="01-"+secondsimpledateformat.format(calendar.getTime())+"-"+firstsimpledateformat.format(calendar.getTime());
				    						generatePdf(clienttokens[0],startdate,enddate);
			    					    }
			    					}
			    				}
		    				}
		    			}
		    		}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return invoicedatalist;
    }
	
	public void generatePdf(String clientNumber,String startDate,String endDate){
		try{
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat numberDateFormat=new SimpleDateFormat("ddMM");
			ArrayList list=employeeworkhoursdata(clientNumber,startDate,endDate);
			ArrayList projectList=employeeworkhoursprojectdata(clientNumber,startDate,endDate);
			if(list!=null && list.size()!=0){
				if(projectList!=null && projectList.size()!=0){
					for(int j=0;j<projectList.size();j++){
						ArrayList templist=new ArrayList();
						String projectnumber=(String)projectList.get(j);
						int totalamount=0;
						for(int i=0;i<list.size();i++){
							String[] tokensdata=(String[])list.get(i);
							if(tokensdata[0].equalsIgnoreCase(projectnumber)){
								totalamount=totalamount+(Integer.parseInt(tokensdata[2])*Integer.parseInt(tokensdata[3]));
								templist.add(tokensdata);
							}											
						}
						
						String invoiceNumber=""+(100000+Integer.parseInt(projectnumber)+Integer.parseInt(numberDateFormat.format(new Date())));
						createinvoice(invoiceNumber,clientNumber,projectnumber,simpleDateFormat.format(new Date()), ""+totalamount);
						String[] projecttokens=selectprojectdata(clientNumber,projectnumber);
						String[] clienttokens=clientdata(clientNumber);
						saveInvoicePdf(projectnumber+"_"+invoiceNumber,clienttokens,projecttokens,templist,""+totalamount,invoiceNumber,startDate+"-"+endDate);
					}									
				}								
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String createinvoice(String invoiceNumber, String clientNumber,String projectNumber,String createdDate,String totalAmount){
		Connection mysqlconnection=null;
		Statement stmt=null;
		String invoicenumber=null;
		try{
			mysqlconnection =invoiceCreationDBConnection();
			if(mysqlconnection!=null){
	            stmt = mysqlconnection.createStatement();
	            Random randomno = new Random();
	            SimpleDateFormat sd=new SimpleDateFormat("ddMMyyyy");
	            String sql = "insert into invoice_data (invoicenumber,client,projectnumber,invoicedate,amount) values ("+invoiceNumber+","+clientNumber+","+projectNumber+",'"+createdDate+"',"+totalAmount+")";
	            stmt.executeUpdate(sql);
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }finally{
		     try{
		    	 if(stmt!=null){
			    	 stmt.close();
			     }
		         if(mysqlconnection!=null)
		        	 mysqlconnection.close();
		      }catch(Exception e){
		         e.printStackTrace();
		      }
		}
        return invoicenumber;
	}
	
	public ArrayList employeeworkhoursprojectdata(String clientNumber,String startDate,String endDate){
		Connection mysqlconnection=null;
		Statement stmt=null;
		ArrayList projectList=null;
		try{
			mysqlconnection =invoiceCreationDBConnection();
			if(mysqlconnection!=null){
	            stmt = mysqlconnection.createStatement();
	            ResultSet rs = stmt.executeQuery("select distinct projectnumber from developer_hours_data where client="+clientNumber+" and STR_TO_DATE(startdate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"+startDate+"','%d-%m-%Y') AND STR_TO_DATE('"+endDate+"','%d-%m-%Y') group by projectnumber,developername,billrate");
	    		if(rs!=null){
	    			projectList=new ArrayList();
	    			while(rs.next()){
	    				projectList.add(""+rs.getInt("projectnumber"));
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }finally{
		     try{
		    	 if(stmt!=null){
			    	 stmt.close();
			     }
		         if(mysqlconnection!=null)
		        	 mysqlconnection.close();
		      }catch(Exception e){
		         e.printStackTrace();
		      }
		}
        return projectList;
    }
	
	public ArrayList employeeworkhoursdata(String clientNumber,String startDate,String endDate){
		Connection mysqlconnection=null;
		Statement stmt=null;
		ArrayList clientList=null;
		try{
			mysqlconnection =invoiceCreationDBConnection();
			if(mysqlconnection!=null){
	            stmt = mysqlconnection.createStatement();
	            ResultSet rs = stmt.executeQuery("select projectnumber,developername,billrate,sum(workhours) from developer_hours_data where client="+clientNumber+" and STR_TO_DATE(startdate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"+startDate+"', '%d-%m-%Y') AND STR_TO_DATE('"+endDate+"','%d-%m-%Y') group by projectnumber,developername,billrate");
	    		if(rs!=null){
	    			clientList=new ArrayList();
	    			while(rs.next()){
	    				String[] tokens=new String[4];
	    				tokens[0]=""+rs.getInt("projectnumber");
	    				tokens[1]=rs.getString("developername");
	    				tokens[2]=rs.getString("billrate");
	    				tokens[3]=rs.getString("sum(workhours)");
	    				clientList.add(tokens);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }finally{
		     try{
		    	 if(stmt!=null){
			    	 stmt.close();
			     }
		         if(mysqlconnection!=null)
		        	 mysqlconnection.close();
		      }catch(Exception e){
		         e.printStackTrace();
		      }
		}
        return clientList;
    }
	
	public ArrayList selectinvoicedevelopersdata(String clientnumber,String projectnumber){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList invoicedeveloperdatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from developer_hours_data where client="+clientnumber+" and projectnumber="+projectnumber+" and hoursapprove='APPROVED'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			invoicedeveloperdatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] invoice_developer_data=new String[7];
	    				invoice_developer_data[0]=rs.getString("client");
	    				invoice_developer_data[1]=rs.getString("projectnumber");
	    				invoice_developer_data[2]=rs.getString("developername");
	    				invoice_developer_data[3]=""+rs.getInt("workhours");
	    				invoice_developer_data[4]=rs.getString("startdate");
	    				invoice_developer_data[5]=rs.getString("enddate");
	    				invoice_developer_data[6]=""+rs.getInt("billrate");
	    				invoicedeveloperdatalist.add(invoice_developer_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return invoicedeveloperdatalist;
    }
	
	public String[] getdeveloperdata(String name){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		String[] people_data=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from people_data where name='"+name+"' and inactive='N'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			while(rs.next()){
	    				people_data=new String[4];
	    				people_data[0]=rs.getString("name");
	    				people_data[1]=rs.getString("title");
	    				people_data[2]=rs.getString("role");
	    				people_data[3]=""+rs.getInt("billrate");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return people_data;
    }
	
	public ArrayList assigndeveloperdata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select dapd.projectnumber,pd.projectname,pd.projectmanager,dapd.developername from developer_assignto_project_data dapd,project_data pd where pd.client=dapd.client and pd.projectnumber=dapd.projectnumber";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[4];
	    				people_data[0]=""+rs.getInt("projectnumber");
	    				people_data[1]=rs.getString("projectname");
	    				people_data[2]=rs.getString("projectmanager");
	    				people_data[3]=rs.getString("developername");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList assigndevelopersdata(String developername){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from developer_assignto_project_data dapd where developername='"+developername+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[3];
	    				people_data[0]=""+rs.getInt("client");
	    				people_data[1]=rs.getString("projectnumber");
	    				people_data[2]=rs.getString("developername");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList assignedprojectmanagersdata(String projectmanagername){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from project_data where projectmanager='"+projectmanagername+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[3];
	    				people_data[0]=""+rs.getInt("client");
	    				people_data[1]=rs.getString("projectnumber");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList assigndeveloperdata(String username){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select dapd.projectnumber,pd.projectname,pd.projectmanager,dapd.developername from developer_assignto_project_data dapd,project_data pd where pd.client=dapd.client and pd.projectnumber=dapd.projectnumber and pd.projectmanager='"+username+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[4];
	    				people_data[0]=""+rs.getInt("projectnumber");
	    				people_data[1]=rs.getString("projectname");
	    				people_data[2]=rs.getString("projectmanager");
	    				people_data[3]=rs.getString("developername");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList assigndeveloperprojectdata(String username){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select dapd.projectnumber,pd.projectname,pd.projectmanager,dapd.developername from developer_assignto_project_data dapd,project_data pd where pd.client=dapd.client and pd.projectnumber=dapd.projectnumber and dapd.developername='"+username+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[4];
	    				people_data[0]=""+rs.getInt("projectnumber");
	    				people_data[1]=rs.getString("projectname");
	    				people_data[2]=rs.getString("projectmanager");
	    				people_data[3]=rs.getString("developername");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList developerhoursdata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from developer_hours_data";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[8];
	    				people_data[0]=""+rs.getInt("client");
	    				people_data[1]=rs.getString("projectnumber");
	    				people_data[2]=rs.getString("developername");
	    				people_data[3]=rs.getString("workhours");
	    				people_data[4]=rs.getString("startdate");
	    				people_data[5]=rs.getString("enddate");
	    				people_data[6]=rs.getString("billrate");
	    				people_data[7]=rs.getString("hoursapprove");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList developerapprovedhoursdata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from developer_hours_data where hoursapprove='APPROVED'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[8];
	    				people_data[0]=""+rs.getInt("client");
	    				people_data[1]=rs.getString("projectnumber");
	    				people_data[2]=rs.getString("developername");
	    				people_data[3]=rs.getString("workhours");
	    				people_data[4]=rs.getString("startdate");
	    				people_data[5]=rs.getString("enddate");
	    				people_data[6]=rs.getString("billrate");
	    				people_data[7]=rs.getString("hoursapprove");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList developerhoursapprovedata(String name){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select dad.client,dad.projectnumber,dad.developername,dad.workhours,dad.startdate,dad.enddate,dad.billrate,dad.hoursapprove from developer_hours_data dad,project_data pd where pd.projectnumber=dad.projectnumber and pd.client=dad.client and pd.projectmanager='"+name+"' and pd.inactive='N' and dad.hoursapprove='PENDING'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[8];
	    				people_data[0]=""+rs.getInt("client");
	    				people_data[1]=rs.getString("projectnumber");
	    				people_data[2]=rs.getString("developername");
	    				people_data[3]=rs.getString("workhours");
	    				people_data[4]=rs.getString("startdate");
	    				people_data[5]=rs.getString("enddate");
	    				people_data[6]=rs.getString("billrate");
	    				people_data[7]=rs.getString("hoursapprove");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList developerhoursdata(String name){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from developer_hours_data where developername='"+name+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] people_data=new String[8];
	    				people_data[0]=""+rs.getInt("client");
	    				people_data[1]=rs.getString("projectnumber");
	    				people_data[2]=rs.getString("developername");
	    				people_data[3]=rs.getString("workhours");
	    				people_data[4]=rs.getString("startdate");
	    				people_data[5]=rs.getString("enddate");
	    				people_data[6]=rs.getString("billrate");
	    				people_data[7]=rs.getString("hoursapprove");
	    				peopledatalist.add(people_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public String[] clientdata(String clientnumber){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		String[] client_data=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from client_data where number="+clientnumber+" and inactive='N'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			while(rs.next()){
	    				client_data=new String[12];
	    				client_data[0]=""+rs.getInt("number");
	    				client_data[1]=rs.getString("name");
	    				client_data[2]=rs.getString("addressline1");
	    				client_data[3]=rs.getString("addressline2");
	    				client_data[4]=rs.getString("city");
	    				client_data[5]=rs.getString("state");
	    				client_data[6]=""+rs.getInt("zip");
	    				client_data[7]=rs.getString("email");
	    				client_data[8]=rs.getString("contact");
	    				client_data[9]=rs.getString("invoicefreq");
	    				client_data[10]=rs.getString("billingterms");
	    				client_data[11]=rs.getString("invoicegrouping");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return client_data;
    }
	
	public ArrayList developerdata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList peopledatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select name from people_data where role='developer'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			peopledatalist=new ArrayList();
	    			while(rs.next()){
	    				peopledatalist.add(rs.getString("name"));
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return peopledatalist;
    }
	
	public ArrayList selectcompanydata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList companydatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from company_data";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			companydatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] company_data=new String[6];
	    				company_data[0]=rs.getString("name");
	    				company_data[1]=rs.getString("addressline1");
	    				company_data[2]=rs.getString("addressline2");
	    				company_data[3]=rs.getString("city");
	    				company_data[4]=rs.getString("state");
	    				company_data[5]=""+rs.getInt("zip");
	    				companydatalist.add(company_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return companydatalist;
    }
	
	public ArrayList selectprojectdata(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList projectdatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from project_data where inactive='N'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			projectdatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] project_data=new String[9];
	    				project_data[0]=""+rs.getInt("client");
	    				project_data[1]=""+rs.getInt("projectnumber");
	    				project_data[2]=rs.getString("projectname");
	    				project_data[3]=rs.getString("startdate");
	    				project_data[4]=rs.getString("enddate");
	    				project_data[5]=rs.getString("status");
	    				project_data[6]=rs.getString("projectmanager");
	    				project_data[7]=rs.getString("clientcontact");
	    				project_data[8]=""+rs.getDouble("budget");
	    				projectdatalist.add(project_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return projectdatalist;
    }
	
	public ArrayList selectprojectdata(String projectmanager){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList projectdatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from project_data where projectmanager='"+projectmanager+"' and inactive='N'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			projectdatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] project_data=new String[9];
	    				project_data[0]=""+rs.getInt("client");
	    				project_data[1]=""+rs.getInt("projectnumber");
	    				project_data[2]=rs.getString("projectname");
	    				project_data[3]=rs.getString("startdate");
	    				project_data[4]=rs.getString("enddate");
	    				project_data[5]=rs.getString("status");
	    				project_data[6]=rs.getString("projectmanager");
	    				project_data[7]=rs.getString("clientcontact");
	    				project_data[8]=""+rs.getDouble("budget");
	    				projectdatalist.add(project_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return projectdatalist;
    }
	
	public ArrayList selectclients(){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		ArrayList clientdatalist=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from client_data where inactive='N'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			clientdatalist=new ArrayList();
	    			while(rs.next()){
	    				String[] client_data=new String[12];
	    				client_data[0]=""+rs.getInt("number");
	    				client_data[1]=rs.getString("name");
	    				client_data[2]=rs.getString("addressline1");
	    				client_data[3]=rs.getString("addressline2");
	    				client_data[4]=rs.getString("city");
	    				client_data[5]=rs.getString("state");
	    				client_data[6]=""+rs.getInt("zip");
	    				client_data[7]=rs.getString("email");
	    				client_data[8]=rs.getString("contact");
	    				client_data[9]=rs.getString("invoicefreq");
	    				client_data[10]=rs.getString("billingterms");
	    				client_data[11]=rs.getString("invoicegrouping");
	    				clientdatalist.add(client_data);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return clientdatalist;
    }
	
	public String[] selectprojectdata(String clientnumber,String projectnumber){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		String[] project_data=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from project_data where client="+clientnumber+" and projectnumber="+projectnumber;
				System.out.println(sql);
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			while(rs.next()){
	    				project_data=new String[9];
	    				project_data[0]=""+rs.getInt("client");
	    				project_data[1]=""+rs.getInt("projectnumber");
	    				project_data[2]=rs.getString("projectname");
	    				project_data[3]=rs.getString("startdate");
	    				project_data[4]=rs.getString("enddate");
	    				project_data[5]=rs.getString("status");
	    				project_data[6]=rs.getString("projectmanager");
	    				project_data[7]=rs.getString("clientcontact");
	    				project_data[8]=""+rs.getDouble("budget");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return project_data;
    }
	
	public String[] userloginAuthentication(String loginid,String password){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		String[] people_data=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from people_data where name='"+loginid+"' and password='"+password+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			while(rs.next()){
	    				people_data=new String[4];
	    				people_data[0]=rs.getString("name");
	    				people_data[1]=rs.getString("title");
	    				people_data[2]=""+rs.getInt("billrate");
	    				people_data[3]=rs.getString("role");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return people_data;
    }
	
	public String[] projectmanagerproject(String name){
		Connection sqlconnection=null;
		Statement sqlstatement=null;
		String[] project_data=null;
		try{
			sqlconnection=invoiceCreationDBConnection();
			if(sqlconnection!=null){
				sqlstatement = sqlconnection.createStatement();
				String sql="select * from people_data where projectmanager='"+name+"'";
	            ResultSet rs = sqlstatement.executeQuery(sql);
	    		if(rs!=null){
	    			while(rs.next()){
	    				project_data=new String[9];
	    				project_data[0]=""+rs.getInt("client");
	    				project_data[1]=""+rs.getInt("projectnumber");
	    				project_data[2]=rs.getString("projectname");
	    				project_data[3]=rs.getString("startdate");
	    				project_data[4]=rs.getString("enddate");
	    				project_data[5]=rs.getString("status");
	    				project_data[6]=rs.getString("projectmanager");
	    				project_data[7]=rs.getString("clientcontact");
	    				project_data[8]=""+rs.getDouble("budget");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return project_data;
    }
	
	public void importcompanydataintodatabase(){
		try{
			CSVReader csvreader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\invoiceimportdata\\company_data.csv"));
			List<String[]> listOfClients=csvreader.readAll();
			int indexpointer=0;
			for(int index=0;index<listOfClients.size();index++){
				String[] savecompanyt=(String[])listOfClients.get(index);
				if(indexpointer!=0){
					savecompanydata(savecompanyt[0],savecompanyt[1],savecompanyt[2],savecompanyt[3],savecompanyt[4],savecompanyt[5]);
				}
				indexpointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void importclientsdataintodatabase(){
		try{
			CSVReader csvreader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\invoiceimportdata\\client_data.csv"));
			List<String[]> listOfClients=csvreader.readAll();
			int indexpointer=0;
			for(int index=0;index<listOfClients.size();index++){
				String[] savepclient=(String[])listOfClients.get(index);
				if(indexpointer!=0){
					saveclients(savepclient[0],savepclient[1],savepclient[2],savepclient[3],savepclient[4],savepclient[5],savepclient[6],savepclient[7],savepclient[8],savepclient[9],savepclient[10],savepclient[11]);
				}
				indexpointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void importprojectsdataintodatabase(){
		try{
			CSVReader csvreader=new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\invoiceimportdata\\project_data.csv"));
			List<String[]> listOfProjects=csvreader.readAll();
			int indexpointer=0;
			for(int index=0;index<listOfProjects.size();index++){
				String[] saveproject=(String[])listOfProjects.get(index);
				if(indexpointer!=0){
					saveprojects(saveproject[0],saveproject[1],saveproject[2],saveproject[3],saveproject[4],saveproject[5],saveproject[6],saveproject[7],saveproject[8]);
				}
				indexpointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	public void importemployeesdataintodatabase(){
		try{
			CSVReader csvreader=new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\invoiceimportdata\\people_data.csv"));
			List<String[]> listOfEmployees=csvreader.readAll();
			int indexpointer=0;
			for(int index=0;index<listOfEmployees.size();index++){
				String[] savepeopledata=(String[])listOfEmployees.get(index);
				if(indexpointer!=0){
					savepeopledata(savepeopledata[0],savepeopledata[1],savepeopledata[2],savepeopledata[3]);
				}
				indexpointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	private void generateInvoicePDF(String filename,String[] client,String[] project,ArrayList developerlist,String totalamount,String invoicenumber,String startenddates){
		 Document documentpdf = new Document();
		 PdfWriter documentPdfWriter = null;
		 initializeTextSizeFonts();
		 try {
			 String path = "C://Users//divya//Desktop//"+filename;
			 documentPdfWriter = PdfWriter.getInstance(documentpdf , new FileOutputStream(path));
			 documentpdf.addAuthor("Test");
			 documentpdf.addCreationDate();
			 documentpdf.addProducer();
			 documentpdf.addCreator("Test");
			 documentpdf.addTitle("Generate Invoices as PDF format");
			 documentpdf.setPageSize(PageSize.LETTER);
			 documentpdf.open();
			 PdfContentByte pdfcontentByte = documentPdfWriter.getDirectContent();	   
			 boolean startPageNumber = true;
			 createTextLabels(pdfcontentByte,400,760,"Eagle Consulting Invoice");
			 int height=0;
			 for(int width=0;width<developerlist.size();width++){
				 String[] developinvoicedata=(String[])developerlist.get(width);
				 if(startPageNumber){
					 startPageNumber = false;
					 invoicedetails(documentpdf,pdfcontentByte,invoicenumber); 
					 companydata(pdfcontentByte,client,totalamount,project);
					 height = 507; 
				 }
				 tabledata(pdfcontentByte,width,height,developinvoicedata,startenddates);
				 height = height - 15;
				 if(height < 50){
					 displaypageNumber(pdfcontentByte);
					 documentpdf.newPage();
					 startPageNumber = true;
				 }
			 }
			 createTextLabels(pdfcontentByte,50,150,"Remit Payment To:");
			 createTextLabels(pdfcontentByte,75,135,"Eagle Consulting");
			 createTextLabels(pdfcontentByte,75,120,"2501 E Memorial Road");
			 createTextLabels(pdfcontentByte,75,105,"Edmond, Ok 73013");			 
			 displaypageNumber(pdfcontentByte);
		 }catch(DocumentException dex){
			 dex.printStackTrace();
		 }
		 catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if(documentpdf != null){
				 documentpdf.close();
			 }
			 if(documentPdfWriter != null){
				 documentPdfWriter.close();
			 }
		 }
	 }
	 private void invoicedetails(Document documentpdf, PdfContentByte cb,String invoicenumber){
		 try{
			 cb.setLineWidth(1f);
			 cb.rectangle(40,615,510,120);
			 cb.moveTo(275,735);
			 cb.lineTo(275,615);
			 cb.stroke();
			 createTextLabels(cb,375,723,"Invoice Number: "+invoicenumber);
			 createTextLabels(cb,375,703,"Invoice Date: "+new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			 createTextLabels(cb,375,683,"Payment Terms: ");
			 createTextLabels(cb,375,663,"Billing Frequency: ");
			 createTextLabels(cb,375,643,"Total Amount Due: $");
			 cb.rectangle(20,50,550,410); 
			 createTextLabels(cb,50,525,"Date");
			 createTextLabels(cb,150,525,"Description");
			 createTextLabels(cb,400,525,"Rate");
			 createTextLabels(cb,450,525,"Hours");
			 createTextLabels(cb,500,525,"Amount");
			 Image companyLogo = Image.getInstance(System.getProperty("user.dir")+"\\src\\invoiceimportdata\\eagle.png");
			 companyLogo.setAbsolutePosition(50,750);
			 companyLogo.scalePercent(25);
			 documentpdf.add(companyLogo);
		 }catch (Exception dex){
			 dex.printStackTrace();
		 }
	 }
	 
	 private void companydata(PdfContentByte pdfcontentbyte,String[] clientinfo,String totalamount,String[] project){
		 try{
			 createTextLabels(pdfcontentbyte,75,723,clientinfo[1]);
			 createTextLabels(pdfcontentbyte,75,708,clientinfo[2]);
			 createTextLabels(pdfcontentbyte,75,693,clientinfo[3]);
			 createTextLabels(pdfcontentbyte,75,678,clientinfo[4]+","+clientinfo[5]+" - "+clientinfo[6]);
			 createTextLabels(pdfcontentbyte,75,663,"United States");
			 createTextLabels(pdfcontentbyte,75,648,"Client ID#: "+clientinfo[0]);
			 createTextLabels(pdfcontentbyte,75,633,project[2]);			 
			 createTextLabels(pdfcontentbyte,475,683,clientinfo[10]);
			 createTextLabels(pdfcontentbyte,475,663,clientinfo[9]);
			 createTextLabels(pdfcontentbyte,475,643,totalamount);
		 }catch (Exception ex){
			 ex.printStackTrace();
		 }
	 }
	 
	 private void tabledata(PdfContentByte pdfcontentbyte,int index,int y,String[] developinvoicedata,String startenddates){
		 DecimalFormat df = new DecimalFormat("0.00");
		 try{
			 createContent(pdfcontentbyte,115,y,startenddates,PdfContentByte.ALIGN_RIGHT);
			 createContent(pdfcontentbyte,150,y,developinvoicedata[1],PdfContentByte.ALIGN_LEFT);
			 createContent(pdfcontentbyte,400,y,developinvoicedata[2],PdfContentByte.ALIGN_LEFT);
			 createContent(pdfcontentbyte,465,y,developinvoicedata[3],PdfContentByte.ALIGN_RIGHT);
			 createContent(pdfcontentbyte,520,y,""+((Integer.parseInt(developinvoicedata[2])*(Integer.parseInt(developinvoicedata[3])))),PdfContentByte.ALIGN_RIGHT);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	 }

	 private void createTextLabels(PdfContentByte cb, float x, float y, String text){
		 cb.beginText();
		 cb.setFontAndSize(baseFont, 10);
		 cb.setTextMatrix(x,y);
		 cb.showText(text.trim());
		 cb.endText();
	 }
	 
	 private void displaypageNumber(PdfContentByte cb){
		 cb.beginText();
		 cb.setFontAndSize(baseFont, 8);
		 cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber+1), 570 , 25, 0);
		 cb.endText();
		 pageNumber++;	  
	 }
	 
	 private void createContent(PdfContentByte cb, float x, float y, String text, int align){
		 cb.beginText();
		 cb.setFontAndSize(tempBF, 8);
		 cb.showTextAligned(align, text.trim(), x , y, 0);
		 cb.endText(); 
	 }

	 private void initializeTextSizeFonts(){
		 try {
			 baseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			 tempBF = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		 }catch(DocumentException e){
			 e.printStackTrace();
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	 }
}
