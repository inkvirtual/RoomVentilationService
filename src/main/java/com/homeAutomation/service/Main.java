package com.homeAutomation.service;

import com.homeAutomation.configuration.Configuration;
import com.homeAutomation.resources.ResourcesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String DEFAULT_RESOURCES_PATH = "/home/pi/HomeAutomation/RoomVentilationService";
    private static final String SERVICE_PROPERTIES = "room_ventilation_service.properties";


    public static void main(String[] args) {
        Configuration configuration = initConfiguration(args);

        RoomVentilationService service = new RoomVentilationService(configuration);
        service.start();
    }

    protected static Configuration initConfiguration(String[] args) {
        String resourcesPath = getResourcesPath(args);

        ResourcesHelper resourcesHelper = new ResourcesHelper(resourcesPath);
        String propertiesFullPath = resourcesHelper.getFullPath(SERVICE_PROPERTIES);

        return new Configuration(resourcesHelper, propertiesFullPath);
    }

    private static String getResourcesPath(String[] args) {
        boolean useDefaultResPath = false;

        if (null != args && args.length > 1) {
            LOGGER.error("Too many arguments provided:{}", args );
            useDefaultResPath = true;
        } else if (null == args || args.length == 0) {
            LOGGER.error("No argument provided");
            useDefaultResPath = true;
        } else {
            LOGGER.info("Using resources path:{}", args[0]);
        }

        if (useDefaultResPath) {
            LOGGER.info("Using default resources path:{}", DEFAULT_RESOURCES_PATH);
            return DEFAULT_RESOURCES_PATH;
        }

        return args[0];
    }
}
