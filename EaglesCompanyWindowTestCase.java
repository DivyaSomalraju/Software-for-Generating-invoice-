package invoicejunit;

import invoicecreating.EaglesCompany;
import junit.framework.TestCase;

public class EaglesCompanyWindowTestCase extends TestCase{
	EaglesCompany window;
	
	public void setUp() {
		window = new EaglesCompany();
		window.setVisible(true);
	}
	
	public void tearDown() {
		window.dispose();
	}
	
	public void testIsShowing() {
		assertTrue(window.isShowing());
	}
}
