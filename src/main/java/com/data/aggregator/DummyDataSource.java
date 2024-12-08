package com.data.aggregator;

import java.util.Random;

public class DummyDataSource implements DataSource{
    private final String name;
    private final int simulatedDelayMs;

    public DummyDataSource(String name, int simulatedDelayMs) {
        this.name = name;
        this.simulatedDelayMs = simulatedDelayMs;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String fetchData() throws InterruptedException {
        // Simulate a delay
        Thread.sleep(simulatedDelayMs + new Random().nextInt(500));
        return "Data from " + name;
    }
}
