package com.wicio.shiplog.route.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ClampToRangeTest {

  @ParameterizedTest
  @MethodSource("provideTestValues")
  void test(int value,
            int min,
            int max,
            int expected) {
    int result = new ClampToRange().clamp(value, min, max);
    assertEquals(expected, result);
  }

  @Test
  void shouldThrowExceptionWhenMinIsGreaterThanMax() {
    //given
    int value = 0;
    int min = 1;
    int max = 0;
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> new ClampToRange().clamp(value, min, max));
  }

  private static Stream<Arguments> provideTestValues() {
    return Stream.of(
        Arguments.of(0, 0, 360, 0),
        Arguments.of(-1, 0, 360, 0),
        Arguments.of(-99, 0, 360, 0),
        Arguments.of(-1_000, 0, 360, 0),
        Arguments.of(360, 0, 360, 360),
        Arguments.of(361, 0, 360, 360),
        Arguments.of(500, 0, 360, 360),
        Arguments.of(-101, -100, 100, -100),
        Arguments.of(99, -100, 100, 99)
    );
  }

}