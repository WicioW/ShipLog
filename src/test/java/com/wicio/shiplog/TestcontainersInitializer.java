//package com.wicio.shiplog;
//
//import org.springframework.boot.test.util.TestPropertyValues;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.lifecycle.Startables;
//import org.testcontainers.utility.DockerImageName;
//
//class TestcontainersInitializer implements
//    ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//  static DockerImageName postgresImage = DockerImageName.parse("postgis/postgis:11-3.3-alpine")
//      .asCompatibleSubstituteFor("postgres");
//
//  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
//      postgresImage);
//
//  static KafkaContainer kafka = new KafkaContainer(
//      DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
//
//  static {
//    Startables.deepStart(postgres, kafka)
//        .join();
//  }
//
//  @Override
//  public void initialize(ConfigurableApplicationContext ctx) {
//    TestPropertyValues.of(
//            "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers(),
//            "spring.datasource.url=" + postgres.getJdbcUrl(),
//            "spring.datasource.username=" + postgres.getUsername(),
//            "spring.datasource.password=" + postgres.getPassword()
//        )
//        .applyTo(ctx.getEnvironment());
//  }
//}