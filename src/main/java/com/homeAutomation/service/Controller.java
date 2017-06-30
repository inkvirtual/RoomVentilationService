package com.homeAutomation.service;

import com.homeAutomation.raspberryPi.PiBash;
import com.homeAutomation.resources.ResourcesHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Controller {
    private PiBash bash;
    private ResourcesHelper resourcesHelper;

    public Controller(ResourcesHelper resourcesHelper) {
        this.bash = new PiBash();
        this.resourcesHelper = resourcesHelper;
    }

    public double getRoomTemperature() {
        bash.execute(resourcesHelper.getFullPath("room_get_temperature_and_humidity.sh"));

        // TODO: get substring for temperature

        throw new NotImplementedException();
    }

    public double getKitchenTemperature() {
        bash.execute(resourcesHelper.getFullPath("kitchen_get_temperature_and_humidity.sh"));

        // TODO: get substring for temperature

        throw new NotImplementedException();
    }

    public int getRoomHumidity() {
        bash.execute(resourcesHelper.getFullPath("room_get_temperature_and_humidity.sh"));

        // TODO: get substring for humidity

        throw new NotImplementedException();
    }

    public int getKitchenHumidity() {
        bash.execute(resourcesHelper.getFullPath("kitchen_get_temperature_and_humidity.sh"));

        // TODO: get substring for humidity

        throw new NotImplementedException();
    }

    public void startFan() {
        bash.execute(resourcesHelper.getFullPath("room_start_fan.sh"));
    }

    public void stopFan() {
        bash.execute(resourcesHelper.getFullPath("room_stop_fan.sh"));
    }
}
