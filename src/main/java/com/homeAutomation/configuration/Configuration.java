package com.homeAutomation.configuration;

import com.homeAutomation.resources.ResourcesHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//public class configuration implements configuration.IConfiguration {
public class Configuration {
    private Map<String, String> properties;
    private ResourcesHelper resourcesHelper;

    public Configuration(ResourcesHelper resourcesHelper, String propertiesFileName) {
        this.resourcesHelper = resourcesHelper;
        Properties propsFile = new Properties();
        properties = new HashMap<>();
        InputStream fis = null;

        try {
            resourcesHelper.checkPropsFileNameValid(propertiesFileName);

            fis = new FileInputStream(propertiesFileName);
            propsFile.load(fis);

            for (Map.Entry<Object, Object> entry : propsFile.entrySet()) {
                properties.put(entry.getKey().toString(), entry.getValue().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(fis);
        }
    }

    private void closeInputStream(InputStream is) {
        if (is != null)
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

//    @Override
    public Map<String, String> getProperties() {
        return this.properties;
    }

    public ResourcesHelper getResourcesHelper() {
        return resourcesHelper;
    }

    @Override
    public String toString() {
        return "configuration{" +
                "properties=" + properties +
                '}';
    }
}
