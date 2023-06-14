package com.epam.summer.lab.mojos;

public class MailMojoTest extends AbstractBaseMojoTest {

    public void testSettingMojoVariables() throws Exception {
        MailMojo mojo = new MailMojo();

        setVariableValueToObject(mojo, "password", "demonstrate");
        setVariableValueToObject(mojo, "userName", "guest");
        setVariableValueToObject(mojo, "projectName", "blablabla");

        assertEquals("demonstrate", (String) getVariableValueFromObject(mojo, "password"));
        assertEquals("guest", (String) getVariableValueFromObject(mojo, "userName"));
        assertEquals("blablabla", (String) getVariableValueFromObject(mojo, "projectName"));
    }
}

