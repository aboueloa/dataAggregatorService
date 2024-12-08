package com.data.aggregator;

import java.util.List;
import java.util.concurrent.*;

public class AggregatorService {
    private final ExecutorService executorService;
    private final List<DataSource> dataSources;
    private final ResultAggregator aggregator;
    private final CountDownLatch latch;

    public AggregatorService(List<DataSource> dataSources) {
        this.executorService = Executors.newFixedThreadPool(dataSources.size());
        this.dataSources = dataSources;
        this.aggregator = new ResultAggregator();
        this.latch = new CountDownLatch(dataSources.size());
    }

    public List<String> aggregateData() throws InterruptedException {
        for (DataSource dataSource : dataSources) {
            executorService.submit(() -> queryAndAggregate(dataSource));
        }

        // Wait for all tasks to finish
        latch.await();
        executorService.shutdown();

        return aggregator.getResults();
    }

    private void queryAndAggregate(DataSource dataSource) {
        try {
            String result = dataSource.fetchData();
            aggregator.addResult(result);
        } catch (Exception e) {
            System.err.println("Error querying " + dataSource.getName() + ": " + e.getMessage());
        } finally {
            latch.countDown();
        }
    }
}

