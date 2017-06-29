package com.homeAutomation.Service;

import java.util.Map;

/**
 * Created by fanta on 6/29/17.
 */
public class RoomVentilationService {
    private double fanStartTempC;
    private double fanStopTempC;

    private int fanStartHumidityP;
    private int fanStopHumidityP;

    private int fanMaxWorkTimeS;
    private int fanCoolDownTimeS;

    private boolean fanStarted;
    private long fanStartTimeS;

    private boolean serviceStopped;

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

        controller = new Controller(configuration.getResourcesHelper());

        Main.LOGGER.info(config.toString());
    }

    public void start() {
        Main.LOGGER.info("----------------------------------------");
        Main.LOGGER.info("Home Ventilation Service started!\n");
        sleep(2);

        double roomTemperature;
        int roomHumidity;
        fanStarted = false;
        setServiceStopped(false);

        while (!isServiceStopped()) {
            roomTemperature = controller.getRoomTemperature();
            roomHumidity = controller.getRoomHumidity();

            // Switch to debug
            Main.LOGGER.info("DEBUG: Room Temp: {}C", roomTemperature);
            Main.LOGGER.info("DEBUG: Room Humidity: {}%", roomHumidity);

            // If high temperature detected
            if (roomTemperature >= fanStartTempC) {
                Main.LOGGER.info("WARN: Room Temperature: {}C. Starting the fan...", roomTemperature);
                startFan();
            } else if (roomHumidity >= fanStartHumidityP) { // If high humidity detected
                Main.LOGGER.info("WARN: Room Humidity: {}%. Starting the fan...", roomHumidity);
                startFan();
            }

            if (fanStarted) {
                Main.LOGGER.info("INFO: Fan will be ON for max {}s ({} mins)", fanMaxWorkTimeS, fanMaxWorkTimeS / 60);
                while (true) {
                    roomTemperature = controller.getRoomTemperature();
                    roomHumidity = controller.getRoomHumidity();

                    if (roomTemperature <= fanStopTempC) {
                        Main.LOGGER.info("INFO: Room Temperature cooled down: {}C. Stopping the fan...", roomTemperature);
                        stopFan();
                        break;
                    }
                    if (roomHumidity <= fanStopHumidityP) {
                        Main.LOGGER.info("INFO: Room Humidity decreased: {}%. Stopping the fan...", roomHumidity);
                        stopFan();
                        break;
                    }
                    if ((getCurrentTimeS() - fanStartTimeS) > fanMaxWorkTimeS) {
                        Main.LOGGER.info(
                                "INFO: Fan is ON for {}s ({} mins). Stopping the fan to cool down at least for {}s({} mins)",
                                fanMaxWorkTimeS, fanMaxWorkTimeS / 60, fanCoolDownTimeS, fanCoolDownTimeS / 60);
                        stopFan();
                        sleep(fanCoolDownTimeS);
                        break;
                    }
                }
            }

            Main.LOGGER.info("\n-------------------------------------------");
            Main.LOGGER.info("Room Ventilation Service Terminated!");
        }
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
}
