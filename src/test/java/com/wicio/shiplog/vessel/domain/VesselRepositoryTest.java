package com.wicio.shiplog.vessel.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wicio.shiplog.EnableTestcontainers;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.LogRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableTestcontainers
class VesselRepositoryTest {

  @Autowired
  private VesselRepository testObj;

  @Autowired
  private LogRepository logRepository;

  @BeforeEach
  void clear() {
    testObj.findAll()
        .forEach(v -> {
          v.deleteLastLog();
          testObj.save(v);
        });
    logRepository.deleteAll();
    testObj.deleteAll();
  }

  @Test
  void testFindAllByLastLogIsNull() {
    //given
    Vessel vessel = createAndPersistVessel();
    createAndPersistVesselWithLastLog();
    createAndPersistVesselWithLastLog();
    Vessel vessel4 = createAndPersistVessel();

    //when
    List<Long> result = testObj.findAllByLastLogIsNull()
        .stream()
        .map(Vessel::getId)
        .toList();
    //then
    assertThat(testObj.count()).isEqualTo(4);
    assertThat(result).hasSize(2)
        .contains(vessel.getId(), vessel4.getId());
  }

  @Test
  void testFindAllByLastLogIsNotNull() {
    //given
    Vessel vessel = createAndPersistVesselWithLastLog();
    createAndPersistVessel();
    createAndPersistVessel();
    Vessel vessel2 = createAndPersistVesselWithLastLog();

    //when
    List<Long> result = testObj.findAllByLastLogIsNotNull()
        .stream()
        .map(Vessel::getId)
        .toList();
    //then
    assertThat(testObj.count()).isEqualTo(4);
    assertThat(result).hasSize(2)
        .contains(vessel.getId(), vessel2.getId());
  }

  private Vessel createAndPersistVessel() {
    return testObj.save(Vessel.builder()
        .name("2PK2mKe1")
        .build());
  }

  private Vessel createAndPersistVesselWithLastLog() {
    Vessel vessel = createAndPersistVessel();
    vessel.updateLastLog(persistLogForVessel(vessel));
    return testObj.save(vessel);
  }

  private Log persistLogForVessel(Vessel vessel) {
    return logRepository.save(
        Log.builder()
            .vessel(vessel)
            .build());
  }

}