package com.wicio.shiplog.route.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class RandomNumberGeneratorTest {

  @InjectMocks
  private RandomNumberGenerator testObj;

  @Nested
  class RandomIntBetween {

    @ParameterizedTest
    @MethodSource("randomIntBetween")
    void shouldGenerateRandomIntBetween(int min,
                                        int max) {
      //given
      //when
      int actual = testObj.randomIntBetween(min, max);
      //then
      assertThat(actual).isBetween(min, max);
    }

    private static Stream<Arguments> randomIntBetween() {
      return Stream.of(
          Arguments.of(0, 10),
          Arguments.of(100, 200),
          Arguments.of(1_000_000, 2_000_000),
          Arguments.of(0, 2_000_000_000));
    }
  }

  @Nested
  class RandomDoubleBetween {

    @ParameterizedTest
    @MethodSource("randomDoubleBetween")
    void shouldGenerateRandomIntBetween(double min,
                                        double max) {
      //given
      //when
      double actual = testObj.randomDoubleBetween(min, max);
      //then
      assertThat(actual).isBetween(min, max);
    }

    private static Stream<Arguments> randomDoubleBetween() {
      return Stream.of(
          Arguments.of(0.0, 10.0),
          Arguments.of(100.0, 200.0),
          Arguments.of(5.5, 6.5),
          Arguments.of(5.5, 5.8),
          Arguments.of(1_000_000.0, 2_000_000.0),
          Arguments.of(0.0, 2_000_000_000.0));
    }
  }
}