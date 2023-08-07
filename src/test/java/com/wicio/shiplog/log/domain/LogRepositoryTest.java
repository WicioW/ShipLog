//package com.wicio.shiplog.log.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.wicio.shiplog.TestContainers;
//import com.wicio.shiplog.vessel.domain.Vessel;
//import com.wicio.shiplog.vessel.domain.VesselRepository;
//import java.time.Instant;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//class LogRepositoryTest extends TestContainers {
//
//  @Autowired
//  private LogRepository testObj;
//  @Autowired
//  private VesselRepository vesselRepository;
//
//  @BeforeEach
//  void clear() {
//    testObj.deleteAll();
//    vesselRepository.deleteAll();
//  }
//
//  @Test
//  void testFindVesselById() {
//    //given
//    Vessel vessel = getPersistedVessel();
//    Log log = testObj.save(
//        Log.builder()
//            .vessel(vessel)
//            .build());
//    testObj.save(Log.builder()
//        .vessel(getPersistedVessel())
//        .build());
//    testObj.save(Log.builder()
//        .vessel(getPersistedVessel())
//        .build());
//
//    //when
//    Page<Log> resultPage = testObj.findByVesselId(
//        log
//            .getVessel()
//            .getId(),
//        Pageable.unpaged());
//
//    //then
//    assertThat(vesselRepository.count()).isEqualTo(3);
//    assertThat(testObj.count()).isEqualTo(3);
//    assertThat(resultPage.getTotalElements()).isEqualTo(1);
//    assertThat(resultPage
//        .getContent()
//        .get(0)
//        .getId()).isEqualTo(log.getId());
//  }
//
//  private Vessel getPersistedVessel() {
//    return vesselRepository.save(
//        Vessel.builder()
//            .name("aqElRmv")
//            .productionDate(Instant.now())
//            .build());
//  }
//}