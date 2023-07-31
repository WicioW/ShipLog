package com.wicio.shiplog.route.producer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.wicio.shiplog.TestContainers;
import com.wicio.shiplog.log.api.NewVesselLogEventListener;
import com.wicio.shiplog.log.domain.services.LogCreator;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class NewVesselLogEventProducerTest extends TestContainers {

  EasyRandom easyRandom = new EasyRandom();

  @Autowired
  private NewVesselLogEventProducer kafkaProducer;

  @Autowired
  private NewVesselLogEventListener kafkaConsumer;

  @MockBean
  private LogCreator logCreator;

  @Test
  void testProduceEventsConsumesMessages() {
    //given
    NewVesselLogEvent newVesselLogEvent = easyRandom.nextObject(NewVesselLogEvent.class);
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
    //then
    verify(logCreator, timeout(10000)).apply(
        (Long) assertArg(vesselId -> {
          assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
        }),
        assertArg(createLogRequest -> {
          assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
        }));
  }
}