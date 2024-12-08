package com.data.aggregator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResultAggregator {
    private final List<String> results = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    public void addResult(String result) {
        lock.lock();
        try {
            results.add(result);
        } finally {
            lock.unlock();
        }
    }

    public List<String> getResults() {
        return Collections.unmodifiableList(results);
    }
}

