package com.example.fairpaysystem;

import com.example.fairpaysystem.activepayrecord.ActivePaySystemConfig;
import com.example.fairpaysystem.service.utils.DirectoryManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.fairpaysystem.model.BaseConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Component
public class ConfigLoader {

    @Value("classpath:configuration.json")
    private Resource configFile;

    @Value("classpath:paysystemproperties.json")
    private Resource paysystempropertyFile;

    @Value("classpath:empty.json")
    private Resource emptyConfigFile;

    @Value("classpath:onlyproducts.json")
    private Resource onlyProductsConfigFile;

    @Value("classpath:employeesandproducts.json")
    private Resource employeeProductConfigFile;

    private BaseConfig baseConfig;

    private ActivePaySystemConfig activePaySystemConfig;

    private DirectoryManager directoryManager;

    @PostConstruct
    public void loadConfig() throws IOException
    {

        ObjectMapper objectMapper = new ObjectMapper();
        this.activePaySystemConfig = objectMapper.readValue(paysystempropertyFile.getInputStream(), ActivePaySystemConfig.class);
        this.directoryManager = new DirectoryManager(activePaySystemConfig);
        if (directoryManager.doesFileExist(directoryManager.getFullPathFileName())) {

            this.baseConfig = objectMapper.readValue((new File(directoryManager.getFullPathFileName())), BaseConfig.class);

        } else {

            this.baseConfig = objectMapper.readValue(configFile.getInputStream(), BaseConfig.class);

        }

    }

    public void updateConfig(String fileType) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        if (fileType.equals("e")) {
            this.baseConfig = objectMapper.readValue(emptyConfigFile.getInputStream(), BaseConfig.class);
        } else if (fileType.equals("p")) {
            this.baseConfig = objectMapper.readValue(onlyProductsConfigFile.getInputStream(), BaseConfig.class);
        } else if (fileType.equals("ep")) {
            this.baseConfig = objectMapper.readValue(employeeProductConfigFile.getInputStream(), BaseConfig.class);
        } else {
            throw new IllegalArgumentException("Unrecognized config type!");
        }


    }

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void setConfigFile(Resource configFile) {
        this.configFile = configFile;
    }

    public DirectoryManager getDirectoryManager() {
        return directoryManager;
    }

    public void setDirectoryManager(DirectoryManager directoryManager) {
        this.directoryManager = directoryManager;
    }
}
