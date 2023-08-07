package com.wicio.shiplog;

import com.wicio.shiplog.TestContainers.TestcontainersInitializer;
import com.wicio.shiplog.route.RoutesCreator;
import com.wicio.shiplog.tmp.DbFiller;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@DirtiesContext
@ContextConfiguration(initializers = TestcontainersInitializer.class)
public class TestContainers {

  static DockerImageName postgresImage = DockerImageName.parse("postgis/postgis:11-3.3-alpine")
      .asCompatibleSubstituteFor("postgres");

  //  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      postgresImage).withReuse(true);

  //  @Container
  static KafkaContainer kafka = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

  static {
    Startables.deepStart(postgres, kafka)
        .join();
  }

  //so that the DB won't be filled during tests
  @MockBean
  private DbFiller dbFiller;

  @MockBean
  private RoutesCreator routesCreator;

  static class TestcontainersInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
      TestPropertyValues.of(
              "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers(),
              "spring.datasource.url=" + postgres.getJdbcUrl(),
              "spring.datasource.username=" + postgres.getUsername(),
              "spring.datasource.password=" + postgres.getPassword()
          )
          .applyTo(ctx.getEnvironment());
    }
  }

}
