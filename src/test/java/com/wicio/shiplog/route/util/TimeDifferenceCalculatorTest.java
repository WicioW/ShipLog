package com.wicio.shiplog.route.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class TimeDifferenceCalculatorTest {

  @InjectMocks
  private TimeDifferenceCalculator testObj;

  @ParameterizedTest
  @MethodSource("provideTestValues")
  void shouldCalculateDifference(long expected, Instant first, Instant second) {
    //given
    //when
    long actual = testObj.minutesBetween(first, second);
    //then
    assertEquals(expected, actual);
  }

  private static Stream<Arguments> provideTestValues() {
    return Stream.of(
        Arguments.of(10, Instant.now(), Instant.now()
            .plusSeconds(600)),
        Arguments.of(10, Instant.now(), Instant.now()
            .minusSeconds(600)),
        Arguments.of(0, Instant.now(), Instant.now()),
        Arguments.of(24 * 60,
            Instant.parse("2021-08-02T00:00:00Z"),
            Instant.parse("2021-08-01T00:00:00Z"))
    );
  }
}