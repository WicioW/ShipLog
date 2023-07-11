package com.wicio.shiplog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(Class clazz,
                                 Long id) {
    super(String.format("%s[id:%s] not found.", clazz.getSimpleName(), id));
  }

}
