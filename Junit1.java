package invoicejunit;

import invoicedatabase.InvoiceDatabase;
import static org.junit.Assert.*;
import org.junit.Test;

public class Junit1 {
	 @Test  
	 public void getClientProjectEmployees(){  
		 InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
		 assertNotNull(invoiceDatabase.userloginAuthentication("Johnetta Abdallah","projectmanager"));
		 assertNotNull(invoiceDatabase.assigndeveloperdata());
	 }
}
