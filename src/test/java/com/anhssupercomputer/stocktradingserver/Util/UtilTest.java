package com.anhssupercomputer.stocktradingserver.Util;

import com.anhssupercomputer.stocktradingserver.Utility.Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UtilTest {
    @Test
    public void standardDeviationTest() {
        // Initialize a random list of values
        final int STD_DEV_LIST_SIZE = 100;
        List<Double> nums = new ArrayList<>(STD_DEV_LIST_SIZE);

        // Populate the list
        for(int i = 0; i < STD_DEV_LIST_SIZE; i++) {
            nums.add(Math.random() * 100);
        }

        // Print out the standard dev
        double stdDev = Util.standardDeviation(nums);
        double range = nums.stream().max(Double::compare).orElse(0.0) - nums.stream().min(Double::compare).orElse(0.0);
        System.out.println("STDDEV: {" + stdDev + "}");
        System.out.println("RANGE: {" + range + "}");
    }
}
