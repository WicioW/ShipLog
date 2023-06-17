package com.wicio.shiplog.route.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import org.junit.jupiter.api.Test;

class RandomGaussianNumberGeneratorTest {

  RandomGaussianNumberGenerator testObj = new RandomGaussianNumberGenerator();

  @Test
  void testNormalDistribution() {
    int min = 0;
    int max = 100;
    int sampleSize = 1000;

    List<Double> generatedNumbers = new ArrayList<>();
    for (int i = 0; i < sampleSize; i++) {
      generatedNumbers.add(
          testObj
              .normalDistribution(min, max));
    }

    DoubleSummaryStatistics doubleSummaryStatistics = generatedNumbers.stream()
        .mapToDouble(Double::doubleValue)
        .summaryStatistics();

    // Calculate mean and standard deviation
    double mean = doubleSummaryStatistics.getSum() / sampleSize;

    double sumOfSquaredDifferences = 0;
    for (double number : generatedNumbers) {
      double difference = number - mean;
      sumOfSquaredDifferences += difference * difference;
    }
    double variance = sumOfSquaredDifferences / sampleSize;
    double standardDeviation = Math.sqrt(variance);

    //mean should be within the range [0, 100]
    assertThat(mean).isBetween((double) min, (double) max);
    //standard deviation should be greater than zero
    assertThat(standardDeviation).isGreaterThan(0);
  }
}