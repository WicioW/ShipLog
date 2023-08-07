package com.wicio.shiplog.route.producer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.wicio.shiplog.TestContainers;
import com.wicio.shiplog.log.api.NewVesselLogEventListener;
import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.domain.services.LogCreator;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

//@TestPropertySource(
//    properties = {
//        "spring.kafka.consumer.auto-offset-reset=earliest"
////        ,
////        "spring.datasource.url=jdbc:tc:mysql:8.0.32:///db",
//    }
//)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewVesselLogEventProducerTest extends TestContainers {

  EasyRandom easyRandom = new EasyRandom();

  @Autowired
  private NewVesselLogEventListener kafkaConsumer;

  @Autowired
  private KafkaTemplate<String, NewVesselLogEvent> kafkaTemplate;

  @Autowired
  private NewVesselLogEventProducer kafkaProducer;
  @MockBean
  private LogCreator logCreator;

  @Test
  @Order(3)
//  @Disabled
//  @RepeatedTest(2)
  void testProduceEventsConsumesMessages() throws ExecutionException, InterruptedException {
    //given
    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(491L, easyRandom.nextObject(
        CreateLogRequest.class));
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then
    System.out.println("TEST1");

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
    System.out.println("TEST4");
  }

  @Test
  @Order(2)
//  @Disabled
//  @RepeatedTest(2)
  void testProduceEventsConsumesMessages2() {
    //given
    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(375L, easyRandom.nextObject(
        CreateLogRequest.class));
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then
    System.out.println("TEST1");

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
    System.out.println("TEST4");
  }

  @Test
  @Order(1)
//  @Disabled
  void testProduceEventsConsumesMessages3() {
    //given
    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(1L, easyRandom.nextObject(
        CreateLogRequest.class));
    //when
    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//    kafkaTemplate.send(VESSEL_LOG, newVesselLogEvent);
    //then
    System.out.println("TEST1");

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

    System.out.println("TEST4");
  }
}
