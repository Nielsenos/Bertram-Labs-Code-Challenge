package com.example.fairpaysystem;


import com.example.fairpaysystem.ConfigLoader;
import com.example.fairpaysystem.model.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.LinkedHashMap;

@Service
public class ConfigurationService {

    private final ConfigLoader configLoader;

    @Autowired
    public ConfigurationService(ConfigLoader configLoader)
    {
        this.configLoader = configLoader;



        int a = 0;
    }

    public BaseConfig getConfiguration() {
        return configLoader.getBaseConfig();
    }


    public void updateConfigurationFile(BaseConfig baseConfig) {
        try {
            JsonFileWriter.writeJsonToFile("src/main/resources/configuration.json", baseConfig);
        } catch (IOException e) {
            throw new RuntimeException("Error in updating config file");
            // Handle the exception appropriately
        }
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }
}
