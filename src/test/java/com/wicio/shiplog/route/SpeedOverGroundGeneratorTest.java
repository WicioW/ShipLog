package com.wicio.shiplog.route;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.route.util.ClampToRange;
import com.wicio.shiplog.route.util.RandomGaussianNumberGenerator;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class SpeedOverGroundGeneratorTest {

  @InjectMocks
  private SpeedOverGroundGenerator testObj;

  @Mock
  private RandomNumberGenerator randomNumberGenerator;
  @Mock
  private RandomGaussianNumberGenerator randomGaussianNumberGenerator;
  @Mock
  private ClampToRange clampToRange;

  @Test
  void testGenerateSpeedOverGround() {
    //given
    int lastSOG = 10;
    when(clampToRange.clamp(anyInt(), anyInt(), anyInt())).thenAnswer(i -> {
      int argument = (int) i.getArguments()[0];
      if (argument < 0) {
        return 0;
      }
      if (argument > 20) {
        return 20;
      }
      return argument;
    });

    when(randomGaussianNumberGenerator.normalDistribution(0, 20)).thenReturn(15d);
    //when
    int result = testObj.generateSpeedOverGround(lastSOG, 60);
    //then
    assertThat(result).isBetween(0, 20);
  }

  @Test
  void shouldGenerateSOGWhenLastSOGisNull() {
    //given
    when(randomNumberGenerator.randomIntBetween(0, 20)).thenReturn(10);
    //when
    int result = testObj.generateSpeedOverGround(null, 60);
    //then
    assertThat(result).isBetween(0, 20);
  }
}