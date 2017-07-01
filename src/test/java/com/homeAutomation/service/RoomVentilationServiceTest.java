package com.homeAutomation.service;

import com.homeAutomation.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by fanta on 6/30/17.
 */
public class RoomVentilationServiceTest {
    private Controller mockController;
    private Configuration mockConfiguration;

    private Map<String, String> testConfig;

    private RoomVentilationService service;

    @Before
    public void setUp() throws Exception {
        mockController = mock(Controller.class);
        mockConfiguration = mock(Configuration.class);

        testConfig = setupConfig();
        doReturn(testConfig).when(mockConfiguration).getProperties();

        service = new RoomVentilationService(mockConfiguration);
        service.setController(mockController);

        setupSleepTimes();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fanNotStartsIfRoomTemperatureAndHumidityIsNormal() throws Exception {
        doReturn(25d).when(mockController).getRoomTemperature();
        doReturn(20d).when(mockController).getKitchenTemperature();

        doReturn(50).when(mockController).getRoomHumidity();
        doReturn(50).when(mockController).getKitchenHumidity();

        setupSleepTimes(1, 1, 1);
        service.setMaxIterationsNumber(3);

        service.start();

        verify(mockController, times(3)).getRoomTemperature();
        verify(mockController, times(3)).getRoomHumidity();

        verify(mockController, times(3)).getKitchenTemperature();
        verify(mockController, times(3)).getKitchenHumidity();

        verify(mockController, times(0)).startFan();
        verify(mockController, times(0)).stopFan();
    }

    @Test
    public void fanNotStartsIfKitchenIsHotterThanRoom() throws Exception {
        doReturn(35d).when(mockController).getRoomTemperature();
        doReturn(36d).when(mockController).getKitchenTemperature();

        doReturn(50).when(mockController).getRoomHumidity();
        doReturn(50).when(mockController).getKitchenHumidity();

        setupSleepTimes(1, 1, 1);
        service.setMaxIterationsNumber(3);

        service.start();

        verify(mockController, times(3)).getRoomTemperature();
        verify(mockController, times(3)).getRoomHumidity();

        verify(mockController, times(3)).getKitchenTemperature();
        verify(mockController, times(3)).getKitchenHumidity();

        verify(mockController, times(0)).startFan();
        verify(mockController, times(0)).stopFan();
        ;
    }

    @Test
    public void fanNotStartsIfKitchenHumidityIsHigherThanRoom() throws Exception {
        doReturn(25d).when(mockController).getRoomTemperature();
        doReturn(26d).when(mockController).getKitchenTemperature();

        doReturn(70).when(mockController).getRoomHumidity();
        doReturn(80).when(mockController).getKitchenHumidity();

        setupSleepTimes(1, 1, 1);
        service.setMaxIterationsNumber(3);

        service.start();

        verify(mockController, times(3)).getRoomTemperature();
        verify(mockController, times(3)).getRoomHumidity();

        verify(mockController, times(3)).getKitchenTemperature();
        verify(mockController, times(3)).getKitchenHumidity();

        verify(mockController, times(0)).startFan();
        verify(mockController, times(0)).stopFan();
        ;
    }

    @Test
    public void fanStartIfRoomIsHotterThanKitchen() throws Exception {
        doReturn(35d).when(mockController).getRoomTemperature();
        doReturn(31d).when(mockController).getKitchenTemperature();

        doReturn(50).when(mockController).getRoomHumidity();
        doReturn(50).when(mockController).getKitchenHumidity();

        setupSleepTimes(1, 1, 1);
        service.setMaxIterationsNumber(1);

        service.start();

        verify(mockController, times(1)).startFan();
        verify(mockController, times(1)).stopFan();
    }

    @Test
    public void fanStartsIfRoomHumidityIsHigherThanKitchen() throws Exception {
        doReturn(25d).when(mockController).getRoomTemperature();
        doReturn(20d).when(mockController).getKitchenTemperature();

        doReturn(75).when(mockController).getRoomHumidity();
        doReturn(50).when(mockController).getKitchenHumidity();

        setupSleepTimes(1, 1, 1);
        service.setMaxIterationsNumber(1);

        service.start();

        verify(mockController, times(1)).startFan();
    }

    @Test
    public void fanStopsWhenRoomCoolesDown() throws Exception {
        doReturn(35d).
                doReturn(30d).
                doReturn(20d).
                when(mockController).getRoomTemperature();
        doReturn(20d).when(mockController).getKitchenTemperature();

        doReturn(50).when(mockController).getRoomHumidity();
        doReturn(50).when(mockController).getKitchenHumidity();

        setupSleepTimes(1, 1, 1);
        service.setMaxIterationsNumber(4);

        service.start();

        verify(mockController, times(6)).getRoomTemperature();
        verify(mockController, times(6)).getRoomHumidity();

        verify(mockController, times(4)).getKitchenTemperature();
        verify(mockController, times(4)).getKitchenHumidity();


        verify(mockController, times(1)).startFan();
        verify(mockController, times(1)).stopFan();
    }

//    @Ignore("Invalid test")
//    @Test
//    public void fanNotStartsIfRoomIsHotButKitchenIsCold() throws Exception {
//        doReturn(35d).when(mockController).getRoomTemperature();
//        doReturn(20d).when(mockController).getKitchenTemperature();
//
//        doReturn(50).when(mockController).getRoomHumidity();
//        doReturn(50).when(mockController).getKitchenHumidity();
//
//        setupSleepTimes(1, 1, 1);
//        service.setMaxIterationsNumber(1);
//
//        service.start();
//
//        verify(mockController, times(1)).startFan();
//        verify(mockController, times(1)).stopFan();
//    }

//    @Ignore
//    @Test
//    public void checkConfig() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void start() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void stopFan() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void startFan() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void shouldStartFan() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void shouldStopFan() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void getCurrentTimeS() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void setController() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void isServiceStopped() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void setServiceStopped() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void getSleepTimeFanOn() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void setSleepTimeFanOn() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void getSleepTimeStartService() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void setSleepTimeStartService() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void getSleepTimeService() throws Exception {
//    }
//
//    @Ignore
//    @Test
//    public void setSleepTimeService() throws Exception {
//    }

    private Map<String, String> setupConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("fan.start.temperature.celsius", "30");
        config.put("fan.stop.temperature.celsius", "20");
        config.put("fan.start.humidity.percents", "70");
        config.put("fan.stop.humidity.percents", "50");
        config.put("fan.max.work.time.seconds", "5");
        config.put("fan.cool.down.time.seconds", "5");

        return config;
    }

    private void setupSleepTimes() {
        setupSleepTimes(1, 1, 1);
    }

    private void setupSleepTimes(int sleepTimeStartService, int sleepTimeService, int sleepTimeFanOn) {
        if (service == null)
            throw new IllegalStateException("Service should be initialized before setting sleep times!");

        service.setSleepTimeStartService(sleepTimeStartService);
        service.setSleepTimeService(sleepTimeService);
        service.setSleepTimeFanOn(sleepTimeFanOn);
    }

}