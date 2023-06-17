package com.wicio.shiplog.route;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class DistanceCalculatorBasedOnSpeedAndTimeTest {

  @InjectMocks
  private DistanceCalculatorBasedOnSpeedAndTime testObj;

  @Nested
  class DistanceInMetres {

    @ParameterizedTest
    @MethodSource("provideTestValuesForDistanceInMetres")
    void shouldCalculateDistanceInMetres(double expected,
                                         Integer speedOverGroundInKmPerH,
                                         long minutesBetween) {
      //given
      //when
      double actual = testObj.distanceInMetres(speedOverGroundInKmPerH, minutesBetween);
      //then
      assertEquals(expected, actual);
    }

    @Test
    void shouldThrowErrorWhenSpeedIsNegative() {
      assertThrows(IllegalStateException.class, () -> testObj.distanceInMetres(-1, 1));
    }

    @Test
    void shouldThrowErrorWhenTimeIsNegative() {
      assertThrows(IllegalStateException.class, () -> testObj.distanceInMetres(1, -1));
    }

    private static Stream<Arguments> provideTestValuesForDistanceInMetres() {
      return Stream.of(
          Arguments.of(0.0, 0, 0),
          Arguments.of(10_000.0, 10, 60),
          Arguments.of(10_000.0, 60, 10),
          Arguments.of(0.0, 0, 7),
          Arguments.of(0.0, 8, 0)
      );
    }
  }

  @Nested
  class DistanceInKilometres {

    @ParameterizedTest
    @MethodSource("provideTestValues")
    void shouldCalculateDistance(double expected,
                                 Integer speedOverGroundInKmPerH,
                                 long minutesBetween) {
      //given
      //when
      double actual = testObj.distanceInKilometres(speedOverGroundInKmPerH, minutesBetween);
      //then
      assertEquals(expected, actual);
    }

    @Test
    void shouldThrowErrorWhenSpeedIsNegative() {
      assertThrows(IllegalStateException.class, () -> testObj.distanceInKilometres(-1, 1));
    }

    @Test
    void shouldThrowErrorWhenTimeIsNegative() {
      assertThrows(IllegalStateException.class, () -> testObj.distanceInKilometres(1, -1));
    }

    private static Stream<Arguments> provideTestValues() {
      return Stream.of(
          Arguments.of(0.0, 0, 0),
          Arguments.of(10.0, 10, 1),
          Arguments.of(50.0, 10, 5),
          Arguments.of(0.0, 0, 7),
          Arguments.of(0.0, 8, 0)
      );
    }
  }
}