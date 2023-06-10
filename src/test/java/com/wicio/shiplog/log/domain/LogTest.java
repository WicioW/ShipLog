package com.wicio.shiplog.log.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LogTest {

    @Test
    void testToLogString() {
        Log log = new Log();
        assertEquals("Log[id=null] ", log.toLogString());
    }
  
}