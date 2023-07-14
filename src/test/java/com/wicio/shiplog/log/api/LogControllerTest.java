package com.wicio.shiplog.log.api;

import com.wicio.shiplog.config.SecurityConfig;
import com.wicio.shiplog.log.application.CreateLogUseCase;
import com.wicio.shiplog.log.application.GetLogsListUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = LogController.class)
@MockitoSettings
class LogControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetLogsListUseCase getLogsListUseCase;
  @MockBean
  private CreateLogUseCase createLogUseCase;

  @Test
  void testCreateLog(){
    //given

    //when

    //then
  }

}