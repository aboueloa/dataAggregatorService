package com.data.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AggregatorService2 {
    private final ExecutorService executorService;
    private final List<DataSource> dataSources;
    private final ResultAggregator aggregator;

    public AggregatorService2(List<DataSource> dataSources) {
        this.executorService = Executors.newFixedThreadPool(dataSources.size());
        this.dataSources = dataSources;
        this.aggregator = new ResultAggregator();
    }

    public List<String> aggregateData() throws InterruptedException {
        // Create a list of Callable tasks
        List<Callable<String>> tasks = new ArrayList<>();
        for (DataSource dataSource : dataSources) {
            tasks.add(dataSource::fetchData);
        }

        // Execute all tasks and wait for completion
        List<Future<String>> futures = executorService.invokeAll(tasks);

        // Collect results
        for (Future<String> future : futures) {
            try {
                String result = future.get(); // Retrieve result
                aggregator.addResult(result);
            } catch (ExecutionException e) {
                System.err.println("Error during task execution: " + e.getCause().getMessage());
            }
        }

        executorService.shutdown();
        return aggregator.getResults();
    }
}

