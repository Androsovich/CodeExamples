package com.epam.summer.lab.mojos;

import com.epam.summer.lab.utills.SendMail;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Mojo(name = "sendMail", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class MailMojo extends BaseMojo {
    @Parameter
    private String password;
    @Parameter
    private String userName;
    @Parameter
    private String projectName;

    @Override
    public void execute() {
        final String theme = getTheme();

        SendMail postman = new SendMail(projectName);

        getLog().info("try to send message in " + this.getClass().getSimpleName());
        postman.send(userName, password, theme);
    }

    private String getTheme() {
        String result = null;
        try {
            result = Files.lines(Paths.get(getFileName())).collect(Collectors.joining());
        } catch (IOException e) {
            getLog().error(e.getMessage());
        }
        return result;
    }
}