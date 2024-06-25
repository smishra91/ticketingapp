package com.trainbooking.app;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {
    private static final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters()
            .seed(System.currentTimeMillis()) // Ensure different values on each run
            .objectPoolSize(100) // Set the size of the object pool
            .randomizationDepth(3)
            .stringLengthRange(5, 15) // Set the range for string lengths
            .collectionSizeRange(1, 100)); // Set the range for collection sizes

    public static <T> T generateRandomObject(Class<T> type) {
        return easyRandom.nextObject(type);
    }

    public static <T> List<T> generateRandomObjectList(Class<T> type, int count) {
        return easyRandom.objects(type, count).collect(Collectors.toList());
    }
}
