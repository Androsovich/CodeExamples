package com.epam.summer.lab.mojos;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Mojo(name = "writeFile", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class FileWriterMojo extends BaseMojo {
    @Parameter(property = "artifact", readonly = true)
    private String artifact;
    @Parameter(property = "group", readonly = true)
    private String group;
    @Parameter(property = "version", readonly = true)
    private String version;
    @Parameter(property = "timeStamp", readonly = true)
    private String timeStamp;

    public void execute() {
        final String DELIMITER = ";";
        final String result = String.join(DELIMITER, artifact, group, version, timeStamp, System.lineSeparator());

        try {
            getLog().info("Start write to file " + getFileName() + " from " + this.getClass().getName());
            Files.write(Paths.get(getFileName()), result.getBytes(), StandardOpenOption.APPEND);
            getLog().info("recording was successful to file " + getFileName());
        } catch (IOException e) {
            getLog().error(e.getMessage());
        }
    }
}