package com.wicio.shiplog.vessel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VesselTest {

  @Test
  void testToLogString() {
    Vessel vessel = Vessel.builder().build();
    assertEquals("Vessel[id=null] ", vessel.toLogString());
  }
}
