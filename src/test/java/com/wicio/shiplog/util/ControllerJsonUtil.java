package com.wicio.shiplog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ControllerJsonUtil {

  public static String toJsonString(Object object)
      throws JsonProcessingException {
    return objectMapper().writeValueAsString(object);
  }

  private static ObjectMapper objectMapper() {
    return JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();
  }
}
