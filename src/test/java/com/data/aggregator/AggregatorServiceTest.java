package com.data.aggregator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AggregatorServiceTest {

    @Test
    public void testAggregateData() throws InterruptedException {
        // Mock data sources
        DataSource source1 = mock(DataSource.class);
        DataSource source2 = mock(DataSource.class);
        DataSource source3 = mock(DataSource.class);

        when(source1.getName()).thenReturn("Source 1");
        when(source1.fetchData()).thenReturn("Data from Source 1");

        when(source2.getName()).thenReturn("Source 2");
        when(source2.fetchData()).thenReturn("Data from Source 2");

        when(source3.getName()).thenReturn("Source 3");
        when(source3.fetchData()).thenReturn("Data from Source 3");

        List<DataSource> mockSources = Arrays.asList(source1, source2, source3);

        // Instantiate AggregatorService
        AggregatorService service = new AggregatorService(mockSources);

        // Execute aggregation
        List<String> results = service.aggregateData();

        // Verify results
        assertEquals(3, results.size());
        List<String> expected = Arrays.asList("Data from Source 1", "Data from Source 2", "Data from Source 3");
        Assertions.assertTrue(expected.containsAll(results) && results.containsAll(expected));

        // Verify methods were called
        verify(source1, times(1)).fetchData();
        verify(source2, times(1)).fetchData();
        verify(source3, times(1)).fetchData();
    }

    @Test
    public void testAggregateData2() throws InterruptedException {
        // Mock data sources
        DataSource source1 = mock(DataSource.class);
        DataSource source2 = mock(DataSource.class);
        DataSource source3 = mock(DataSource.class);

        when(source1.getName()).thenReturn("Source 1");
        when(source1.fetchData()).thenReturn("Data from Source 1");

        when(source2.getName()).thenReturn("Source 2");
        when(source2.fetchData()).thenReturn("Data from Source 2");

        when(source3.getName()).thenReturn("Source 3");
        when(source3.fetchData()).thenReturn("Data from Source 3");

        List<DataSource> mockSources = Arrays.asList(source1, source2, source3);

        // Instantiate AggregatorService
        AggregatorService2 service = new AggregatorService2(mockSources);

        // Execute aggregation
        List<String> results = service.aggregateData();

        // Verify results
        assertEquals(3, results.size());
        List<String> expected = Arrays.asList("Data from Source 1", "Data from Source 2", "Data from Source 3");
        Assertions.assertTrue(expected.containsAll(results) && results.containsAll(expected));

        // Verify methods were called
        verify(source1, times(1)).fetchData();
        verify(source2, times(1)).fetchData();
        verify(source3, times(1)).fetchData();
    }

    @Test
    public void testErrorHandling() throws InterruptedException {
        // Mock data sources with an error
        DataSource source1 = mock(DataSource.class);
        DataSource source2 = mock(DataSource.class);

        when(source1.getName()).thenReturn("Source 1");
        when(source1.fetchData()).thenReturn("Data from Source 1");

        when(source2.getName()).thenReturn("Source 2");
        when(source2.fetchData()).thenThrow(new RuntimeException("Fetch failed"));

        List<DataSource> mockSources = Arrays.asList(source1, source2);

        // Instantiate AggregatorService
        AggregatorService service = new AggregatorService(mockSources);

        // Execute aggregation
        List<String> results = service.aggregateData();

        // Verify results
        assertEquals(1, results.size());
        List<String> expected = List.of("Data from Source 1");
        assertEquals(expected, results);

        // Verify methods were called
        verify(source1, times(1)).fetchData();
        verify(source2, times(1)).fetchData();
    }
}

