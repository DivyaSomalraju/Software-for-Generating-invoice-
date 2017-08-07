package invoicejunit;

import invoicecreating.EaglesCompany;

import javax.swing.JFrame;

import junit.framework.TestCase;

public class Junit extends TestCase {
    private EaglesCompany testFrame;

    protected void tearDown() throws Exception {
        if (this.testFrame != null){
            this.testFrame.dispose();
            this.testFrame = null;
        }
    }
    public JFrame getTestFrame() {
        if (this.testFrame == null){
            this.testFrame = new EaglesCompany();
        }
        return this.testFrame;
    }
}  
