package com.epam.summer.lab.mojos;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

public class ConsoleWriterMojoTest extends AbstractMojoTestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testSettingMojoVariables() throws Exception {
        ConsoleWriterMojo mojo = new ConsoleWriterMojo();
        setVariableValueToObject(mojo, "fileName", "f:\\context.txt");

        assertEquals("f:\\context.txt", (String) getVariableValueFromObject(mojo, "fileName"));
    }
}