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
        throw new NotImplementedException();
    }

    public double getKitchenTemperature() {
        throw new NotImplementedException();
    }

    public int getRoomHumidity() {
        throw new NotImplementedException();
    }

    public int getKitchenHumidity() {
        throw new NotImplementedException();
    }

    public void startFan() {
        throw new NotImplementedException();
    }

    public void stopFan() {
        throw new NotImplementedException();
    }
}
