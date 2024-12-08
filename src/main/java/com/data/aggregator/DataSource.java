package com.data.aggregator;

import java.util.Random;

public interface DataSource {
    String getName();

   String fetchData() throws InterruptedException;
}

