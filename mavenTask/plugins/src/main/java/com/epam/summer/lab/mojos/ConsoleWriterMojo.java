package com.epam.summer.lab.mojos;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(name = "writeConsole", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class ConsoleWriterMojo extends BaseMojo {

    @Override
    public void execute() {
        try {
            getLog().info("Start write to console " + " from " + this.getClass().getName());
            List<String> result = Files.lines(Paths.get(getFileName())).collect(Collectors.toList());
            result.forEach(getLog()::info);
        } catch (IOException e) {
            getLog().error(e.getMessage());
        }
    }
}