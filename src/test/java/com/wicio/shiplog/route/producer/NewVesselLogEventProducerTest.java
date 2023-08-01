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
  private NewVesselLogEventListener kafkaConsumer;

//  @Autowired
//  private KafkaTemplate<String, NewVesselLogEvent> kafkaTemplate;

  @Autowired
  private NewVesselLogEventProducer kafkaProducer;
  @MockBean
  private LogCreator logCreator;

  @Test
  void testProduceEventsConsumesMessages() {
    //given
    NewVesselLogEvent newVesselLogEvent = easyRandom.nextObject(NewVesselLogEvent.class);
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then
    System.out.println("TEST1");

//    await().pollInterval(Duration.ofSeconds(1))
//        .atMost(Duration.ofSeconds(10))
//        .untilAsserted(() -> {
////      System.out.println("ELO2");
//
////          verify(kafkaConsumer).newVesselLogListener(any());
//
//          verify(logCreator, timeout(100_000))
//              .apply(
//                  (Long) assertArg(vesselId -> {
//                    assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
//                  }),
//                  assertArg(createLogRequest -> {
//                    assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
//                  }));
//        });

//    await().untilAsserted(() -> {
//      System.out.println("ELO2");
//      verify(logCreator, timeout(100_000))
//          .apply(
//              (Long) assertArg(vesselId -> {
//                assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
//              }),
//              assertArg(createLogRequest -> {
//                assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
//              }));
//    });
//

    verify(logCreator, timeout(1_000))
        .apply(
            (Long) assertArg(vesselId -> {
              assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
            }),
            assertArg(createLogRequest -> {
              assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
            }));
    System.out.println("TEST4");
  }
}