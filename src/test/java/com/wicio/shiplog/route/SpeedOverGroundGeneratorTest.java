package com.wicio.shiplog.route;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  private SpeedGenerator testObj;

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
    int speedRangeMinValue = 0;
    int speedRangeMaxValue = 20;
    SpeedGeneratorConfigVO vo = new SpeedGeneratorConfigVO(speedRangeMinValue, speedRangeMaxValue,
        20);
    when(clampToRange.clamp(anyInt(), anyInt(), anyInt())).thenAnswer(i -> {
      int argument = (int) i.getArguments()[speedRangeMinValue];
      if (argument < speedRangeMinValue) {
        return speedRangeMinValue;
      }
      if (argument > speedRangeMaxValue) {
        return speedRangeMaxValue;
      }
      return argument;
    });

    when(randomGaussianNumberGenerator.normalDistribution(speedRangeMinValue,
        speedRangeMaxValue)).thenReturn(15d);
    //when
    int result = testObj.generateSpeedOverGround(vo, lastSOG, 60);
    //then
    assertThat(result).isBetween(speedRangeMinValue, speedRangeMaxValue);
  }

  @Test
  void shouldGenerateSOGWhenLastSOGisNull() {
    //given
    SpeedGeneratorConfigVO vo = new SpeedGeneratorConfigVO(0, 20, 10);
    when(randomNumberGenerator.randomIntBetween(0, 20)).thenReturn(10);
    //when
    int result = testObj.generateSpeedOverGround(vo, null, 60);
    //then
    assertThat(result).isBetween(0, 20);
  }

  @Test
  void shouldThrowExceptionWhenSpeedGeneratorConfigVOisNull() {
    //given
    SpeedGeneratorConfigVO vo = null;
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> testObj.generateSpeedOverGround(vo, 10, 60));
  }
}