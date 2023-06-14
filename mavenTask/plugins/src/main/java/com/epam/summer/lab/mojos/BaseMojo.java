package com.epam.summer.lab.mojos;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class BaseMojo extends AbstractMojo {
    @Parameter(property = "fileName", readonly = true, required = true)
    private String fileName;

    public String getFileName() {
        return fileName;
    }
}