package com.epam.summer.lab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.epam.summer.lab.services", "com.epam.summer.lab.aop", "com.epam.summer.lab.dto", "com.epam.summer.lab.utils"})
public class RootConfig {
}