package com.wicio.shiplog.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wicio.shiplog.log.domain.Log;
import org.junit.jupiter.api.Test;

class EntityNotFoundExceptionTest {


  @Test
  void testConstructor() {
    // Arrange
    Class<?> clazz = Log.class;
    Long id = 123L;
    String expectedMessage = "Log[id:123] not found.";

    // Act
    EntityNotFoundException exception = new EntityNotFoundException(clazz, id);

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }

}