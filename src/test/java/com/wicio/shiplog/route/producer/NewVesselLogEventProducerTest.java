package com.wicio.shiplog.route.producer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.wicio.shiplog.TestContainers;
import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.domain.services.LogCreator;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class NewVesselLogEventProducerTest extends TestContainers {

  EasyRandom easyRandom = new EasyRandom();

  @Autowired
  private NewVesselLogEventProducer kafkaProducer;
  @MockBean
  private LogCreator logCreator;

  @Test
  void testProduceEventsConsumesMessages() throws ExecutionException, InterruptedException {
    //given
    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(491L, easyRandom.nextObject(
        CreateLogRequest.class));
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then

//    CompletableFuture<SendResult<String, NewVesselLogEvent>> future =
//        kafkaTemplate.send("vessel-log", newVesselLogEvent);
//
//    SendResult<String, NewVesselLogEvent> join = future.join();
//    SendResult<String, NewVesselLogEvent> sendResult = future.get();
//
//    System.out.println(sendResult.toString());
//    System.out.println(join.toString());
//
//    boolean awaited = kafkaConsumer.getLatch()
//        .await(5, TimeUnit.SECONDS);
//    System.out.println("awaited: " + awaited);

    verify(logCreator, timeout(5_000))
        .apply(
            (Long) assertArg(vesselId -> {
              assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
            }),
            assertArg(createLogRequest -> {
              assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
            }));
  }

  @Test
  void testProduceEventsConsumesMessages2() {
    //given
    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(375L, easyRandom.nextObject(
        CreateLogRequest.class));
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then

//    await()
//        .pollInterval(Duration.ofSeconds(1))
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

    verify(logCreator, timeout(5_000))
        .apply(
            (Long) assertArg(vesselId -> {
              assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
            }),
            assertArg(createLogRequest -> {
              assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
            }));
  }

  @Test
  void testProduceEventsConsumesMessages3() {
    //given
    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(1L, easyRandom.nextObject(
        CreateLogRequest.class));
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then

    await()
        .pollInterval(Duration.ofSeconds(1))
        .atMost(Duration.ofSeconds(10))
        .untilAsserted(() -> {
//      System.out.println("ELO2");

//          verify(kafkaConsumer).newVesselLogListener(any());

          verify(logCreator, timeout(5_000))
              .apply(
                  (Long) assertArg(vesselId -> {
                    assertThat(vesselId).isEqualTo(newVesselLogEvent.vesselId());
                  }),
                  assertArg(createLogRequest -> {
                    assertThat(createLogRequest).isEqualTo(newVesselLogEvent.createLogRequest());
                  }));
        });

  }
}
