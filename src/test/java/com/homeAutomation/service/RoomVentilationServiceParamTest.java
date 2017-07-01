//package com.homeAutomation.service;
//
//import com.homeAutomation.configuration.Configuration;
//import com.homeAutomation.raspberryPi.PiBash;
//import com.homeAutomation.resources.ResourcesHelper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// * Created by fanta on 6/30/17.
// */
//
////@RunWith(Parameterized.class)
//public class RoomVentilationServiceParamTest {
//
//    private Controller mockController;
//    private Configuration mockConfiguration;
//    private RoomVentilationService service;
//
////    private double roomTemperature;
////    private double kitchenTemperature;
////
////    private int roomHumidity;
////    private int kitchenHumidity;
////
////    private boolean fanShouldStart;
//
//    public RoomVentilationServiceParamTest(
//            double roomTemperature, double kitchenTemperature, int roomHumidity, int kitchenHumidity, boolean fanShouldStart) {
//
////        bash = mock(PiBash.class);
////        resourcesHelper = mock(ResourcesHelper.class);
//
//        mockController = mock(Controller.class);
//        mockConfiguration = mock(Configuration.class);
//
////        doReturn("").when(resourcesHelper).getFullPath(anyString());
//
////        this.roomTemperature = roomTemperature;
////        this.kitchenTemperature = kitchenTemperature;
////        this.roomHumidity = roomHumidity;
////        this.kitchenHumidity = kitchenHumidity;
////        this.fanShouldStart = fanShouldStart;
//    }
//
////    @Parameterized.Parameters
////    public static Collection<Object[]> testConditions() {
////        return Arrays.asList(new Object[][] {
////                {28, 25, 70, 50, true},     //1
////                // TODO: add more test conditions
////        });
////    }
//
//    @Test
//    public void checkRoomFan() throws Exception {
//        Map<String, String> setConfig = setupConfig();
//
//        doReturn(setConfig).when(mockConfiguration).getProperties();
//
//        service = new RoomVentilationService(mockConfiguration);
//        service.setController(mockController);
//
//        doReturn()
//
//    }
//
//    private Map<String, String> setupConfig() {
//        Map<String, String> config = new HashMap<>();
//        config.put("28", "fan.start.temperature.celsius");
//        config.put("25", "fan.stop.temperature.celsius");
//        config.put("70", "fan.start.humidity.percents");
//        config.put("50", "fan.stop.humidity.percents");
//        config.put("10", "fan.max.work.time.seconds");
//        config.put("5", "fan.cool.down.time.seconds");
//
//        return config;
//    }
//}
