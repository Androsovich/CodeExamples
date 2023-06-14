package com.epam.summer.lab.mojos;

public class FileWriterMojoTest extends AbstractBaseMojoTest {

    public void testSettingMojoVariables() throws Exception {
        FileWriterMojo mojo = new FileWriterMojo();

        setVariableValueToObject(mojo, "artifact", "demo");
        setVariableValueToObject(mojo, "group", "groupTest");
        setVariableValueToObject(mojo, "version", "1.0");
        setVariableValueToObject(mojo, "timeStamp", "14.56");

        assertEquals("demo", (String) getVariableValueFromObject(mojo, "artifact"));
        assertEquals("groupTest", (String) getVariableValueFromObject(mojo, "group"));
        assertEquals("1.0", (String) getVariableValueFromObject(mojo, "version"));
        assertEquals("14.56", (String) getVariableValueFromObject(mojo, "timeStamp"));
    }
}
