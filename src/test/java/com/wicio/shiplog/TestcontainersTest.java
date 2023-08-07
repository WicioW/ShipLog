//package com.wicio.shiplog;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.timeout;
//import static org.mockito.Mockito.verify;
//
//import com.wicio.shiplog.log.api.dto.CreateLogRequest;
//import com.wicio.shiplog.log.domain.services.LogCreator;
//import com.wicio.shiplog.route.RoutesCreator;
//import com.wicio.shiplog.route.producer.NewVesselLogEvent;
//import com.wicio.shiplog.route.producer.NewVesselLogEventProducer;
//import com.wicio.shiplog.tmp.DbFiller;
//import java.util.List;
//import org.jeasy.random.EasyRandom;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//@Testcontainers
//@SpringBootTest
//public class TestcontainersTest {
//
////  static DockerImageName postgresImage = DockerImageName.parse("postgis/postgis:11-3.3-alpine")
////      .asCompatibleSubstituteFor("postgres");
////
////  @Container
////  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
////      postgresImage);
//
//  @Container
//  static KafkaContainer kafkaContainer = new KafkaContainer(
//      DockerImageName.parse("confluentinc/cp-kafka:latest"));
//
//  @DynamicPropertySource
//  static void kafkaProperties(DynamicPropertyRegistry registry) {
//    registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
////    registry.add("spring.datasource.url", postgres::getJdbcUrl);
////    registry.add("spring.datasource.username", postgres::getUsername);
////    registry.add("spring.datasource.password", postgres::getPassword);
//    registry.add("spring.datasource.url", () -> "jdbc:h2:mem:test");
//    registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
//    registry.add("spring.datasource.username", () -> "root");
//    registry.add("spring.datasource.password", () -> "secret");
//    registry.add("spring.flyway.enabled", () -> "false");
//  }
//
////  @Autowired
////  private UserKafkaProducer userKafkaProducer;
//
//  @MockBean
//  private DbFiller dbFiller;
//  @MockBean
//  private RoutesCreator routesCreator;
//
//  @Autowired
//  private NewVesselLogEventProducer kafkaProducer;
//
//  //  @Autowired
////  private UserKafkaConsumer userKafkaConsumer;
////  @Autowired
////  private NewVesselLogEventListener kafkaConsumer;
//
////  @MockBean
////  private UserService userService;
//
//  @MockBean
//  private LogCreator logCreator;
//
//  @Test
//  void testProduceAndConsumeKafkaMessage() {
//    ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
//    ArgumentCaptor<CreateLogRequest> requestCaptor = ArgumentCaptor.forClass(
//        CreateLogRequest.class);
//
////    ArgumentCaptor<NewVesselLogEvent> captor = ArgumentCaptor.forClass(NewVesselLogEvent.class);
//    EasyRandom easyRandom = new EasyRandom();
//    NewVesselLogEvent newVesselLogEvent = new NewVesselLogEvent(491L, easyRandom.nextObject(
//        CreateLogRequest.class));
//
////    userKafkaProducer.writeToKafka(user);
//
//    kafkaProducer.produceEvents(List.of(newVesselLogEvent));
//
//    verify(logCreator, timeout(5000)).apply(idCaptor.capture(), requestCaptor.capture());
//    assertNotNull(idCaptor.getValue());
//    assertNotNull(requestCaptor.getValue());
//    assertEquals(491L, idCaptor.getValue());
//
////    assertEquals("John", captor.getValue().getFirstName());
////    assertEquals("Wick", captor.getValue().getLastName());
//  }
//}
