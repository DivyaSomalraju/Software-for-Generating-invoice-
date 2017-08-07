package invoicejunit;

import invoicecreating.Clientdata;

public class ClientTextFieldTestCases extends Junit {
    private Clientdata emptyPanel;
    private Clientdata tannerPanel;

    protected void setUp(  ) throws Exception {
        // create a panel without a Person
        this.emptyPanel = new Clientdata();
        this.tannerPanel = new Clientdata();
    }

    public void testTextFieldsAreInitiallyDisabled(  ) {
        assertTrue("First name field should be disabled",!this.emptyPanel.name.isEnabled());
        assertTrue("Last name field should be disabled",!this.emptyPanel.number.isEnabled());
    }

    public void testEnabledStateAfterSettingPerson(  ) {
        assertTrue("First name field should be enabled",
                this.tannerPanel.name.isEnabled(  ));
        assertTrue("Last name field should be enabled",
                this.tannerPanel.number.isEnabled(  ));
    }
}
