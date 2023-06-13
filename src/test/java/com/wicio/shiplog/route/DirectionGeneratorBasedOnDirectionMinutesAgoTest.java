package com.wicio.shiplog.route;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class DirectionGeneratorBasedOnDirectionMinutesAgoTest {

  @InjectMocks
  private DirectionGeneratorBasedOnDirectionMinutesAgo testObj;
  @Mock
  private RandomNumberGenerator randomNumberGenerator;

  @ParameterizedTest
  @MethodSource("provideTestValues")
  void generatedDirectionShouldBeInRange(int min,
                                         int max,
                                         Degree lastDirection,
                                         long minutesAgo) {
    //given
    when(randomNumberGenerator.randomIntBetween(min, max)).thenReturn(
        new Random().nextInt(min, max));
    //when
    int actual = testObj.generateDirection(lastDirection, minutesAgo)
        .getValue();
    //then
    assertThat(actual).isBetween(min, max);
  }

  private static Stream<Arguments> provideTestValues() {
    return Stream.of(
        Arguments.of(0, 360, null, 0),
        Arguments.of(0, 360, null, 1_000),
        Arguments.of(0, 360, new Degree(5), 1_000),
        Arguments.of(265, 275, new Degree(270), 10),
        Arguments.of(270 - 45, 270 + 45, new Degree(270), 90)
    );
  }

}