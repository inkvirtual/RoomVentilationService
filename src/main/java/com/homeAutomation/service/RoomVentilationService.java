package com.homeAutomation.service;

import com.homeAutomation.configuration.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

/**
 * Created by fanta on 6/29/17.
 */
public class RoomVentilationService {
    public static final String NEW_LINE = System.getProperty("line.separator");
    private double fanStartTempC;

    private Controller controller;
    private double fanStopTempC;

    private int fanStartHumidityP;
    private int fanStopHumidityP;

    private int fanMaxWorkTimeS;
    private int fanCoolDownTimeS;

    private boolean fanStarted;
    private long fanStartTimeS;

    private boolean serviceStopped;

    private int sleepTimeFanOn;
    private int sleepTimeStartService;
    private int sleepTimeService;

    private Integer maxIterationsNumber;

    public RoomVentilationService(Configuration configuration) {
        init(configuration);
    }

    private static void sleep(long s) {
        try {
            Thread.sleep(s * 1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init(Configuration configuration) {
        Map<String, String> config = configuration.getProperties();

        fanStartTempC = Double.parseDouble(config.getOrDefault("fan.start.temperature.celsius", "29"));
        fanStopTempC = Double.parseDouble(config.getOrDefault("fan.stop.temperature.celsius", "24"));

        fanStartHumidityP = Integer.parseInt(config.getOrDefault("fan.start.humidity.percents", "75"));
        fanStopHumidityP = Integer.parseInt(config.getOrDefault("fan.stop.humidity.percents", "50"));

        fanMaxWorkTimeS = Integer.parseInt(config.getOrDefault("fan.max.work.time.seconds", "120"));
        fanCoolDownTimeS = Integer.parseInt(config.getOrDefault("fan.cool.down.time.seconds", "30"));

//        checkConfig();

        fanStarted = false;
        setServiceStopped(false);

        setSleepTimeStartService(2);
        setSleepTimeFanOn(60);
        setSleepTimeService(60);

        maxIterationsNumber = -1;

        controller = new Controller(configuration.getResourcesHelper());

        Main.LOGGER.info(config.toString());
    }

    protected void checkConfig() {
//        if (fanStartTempC > 35 || fanStartTempC )
        throw new NotImplementedException();
    }

    public void start() {
        Main.LOGGER.info("----------------------------------------");
        Main.LOGGER.info("Home Ventilation Service started!\n");
        sleep(getSleepTimeStartService());

        double roomTemperature;
        int roomHumidity;

        while (!maxIterationsAchieved()) {
            roomTemperature = controller.getRoomTemperature();
            roomHumidity = controller.getRoomHumidity();

            // Switch to debug
            Main.LOGGER.info("DEBUG: Room Temp: {}C, Room Humidity: {}%", roomTemperature, roomHumidity);

            if (shouldStartFan()) {
                startFan();
            }

            if (fanStarted) {
                Main.LOGGER.info("INFO: Fan will be ON for max {}s ({} mins)", fanMaxWorkTimeS, fanMaxWorkTimeS / 60);
                while (fanStarted) {
                    if (shouldStopFan()) {
                        stopFan();
                    }
                    sleep(getSleepTimeFanOn());
                }
            }
            sleep(getSleepTimeService());
        }
        Main.LOGGER.info("Max Iterations Number achieved!");
        Main.LOGGER.info("\n-------------------------------------------");
        Main.LOGGER.info("Room Ventilation service Terminated!");
    }

    protected void stopFan() {
        controller.stopFan();
        fanStarted = false;
//        fanStartTimeS = -1;
        Main.LOGGER.info("Fan stopped!");
    }

    protected void startFan() {
        controller.startFan();
        fanStarted = true;
        fanStartTimeS = getCurrentTimeS();
        Main.LOGGER.info("Fan started!");
    }

    protected boolean shouldStartFan() {
        if (fanStarted) {
//            Main.LOGGER.info("WARN: Fan already started!");
            return false;
        }

        double roomTemperature = controller.getRoomTemperature();
        double kitchenTemperature = controller.getKitchenTemperature();

        int roomHumidity = controller.getRoomHumidity();
        int kitchenHumidity = controller.getKitchenHumidity();

        // If room is hot and kitchen is more cool
        if (roomTemperature >= fanStartTempC && roomTemperature > kitchenTemperature) {
            Main.LOGGER.info("WARN: High Room Temperature: {}C.", roomTemperature);
            return true;
        }

        // If room humidity is high, and its higher than kitchen humidity
        if (roomHumidity >= fanStartHumidityP && roomHumidity > kitchenHumidity) {
            Main.LOGGER.info("WARN: High Room Humidity: {}%.", roomHumidity);
            return true;
        }

        return false;
    }

    protected boolean shouldStopFan() {
        double roomTemperature = controller.getRoomTemperature();
        int roomHumidity = controller.getRoomHumidity();

        if (roomTemperature <= fanStopTempC && roomHumidity <= fanStopHumidityP) {
            Main.LOGGER.info("INFO: Room cooled down: {}C {}%", roomTemperature, roomHumidity);
            return true;
        }

        if ((getCurrentTimeS() - fanStartTimeS) > fanMaxWorkTimeS) {
            Main.LOGGER.info(
                    "INFO: Fan is ON for {}s ({} mins). Max allowed time is {}s({} mins)",
                    fanMaxWorkTimeS, fanMaxWorkTimeS / 60, fanCoolDownTimeS, fanCoolDownTimeS / 60);
            return true;
        }

        return false;
    }

    protected long getCurrentTimeS() {
        return System.currentTimeMillis() / 1_000;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean isServiceStopped() {
        return serviceStopped;
    }

    public void setServiceStopped(boolean serviceStopped) {
        this.serviceStopped = serviceStopped;
    }

    public int getSleepTimeFanOn() {
        return sleepTimeFanOn;
    }

    public void setSleepTimeFanOn(int sleepTimeFanOn) {
        this.sleepTimeFanOn = sleepTimeFanOn;
    }

    public int getSleepTimeStartService() {
        return sleepTimeStartService;
    }

    public void setSleepTimeStartService(int sleepTimeStartService) {
        this.sleepTimeStartService = sleepTimeStartService;
    }

    public int getSleepTimeService() {
        return sleepTimeService;
    }

    public void setSleepTimeService(int sleepTimeService) {
        this.sleepTimeService = sleepTimeService;
    }

    public void setMaxIterationsNumber(int maxIterationsNumber) {
        this.maxIterationsNumber = new Integer(maxIterationsNumber);
    }

    public boolean maxIterationsAchieved() {
        if (maxIterationsNumber != null) {
            if (maxIterationsNumber < 1)
                return true;

            maxIterationsNumber--;
        }
        return false;
    }
}
