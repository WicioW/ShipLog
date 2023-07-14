package com.wicio.shiplog.log.api;

import com.wicio.shiplog.config.SecurityConfig;
import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.api.dto.CreateLogResponse;
import com.wicio.shiplog.log.api.dto.LogResponse;
import com.wicio.shiplog.log.application.CreateLogUseCase;
import com.wicio.shiplog.log.application.GetLogsListUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.wicio.shiplog.util.ControllerJsonUtil.toJsonString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
  void testGetLogsList() throws Exception {
    //given
    Long vesselId = 212L;
    LogResponse response = LogResponse.builder()
        .id(316L)
        .build();
    when(getLogsListUseCase.listOfLogsForVessel(eq(vesselId), eq(PageRequest.of(0, 10)))).thenReturn(new PageImpl<>(
        List.of(response), PageRequest.of(0, 10), 0));

    //when-then
    mockMvc.perform(get("/logs/{vesselId}?page=0&size=10", vesselId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(316));
  }

  @Test
  void testCreateLog() throws Exception{
    //given
    Long vesselId = 212L;

    CreateLogRequest request = CreateLogRequest.builder()
        .XCoordinate(120d)
        .YCoordinate(50d)
        .build();

    CreateLogResponse response = CreateLogResponse.builder()
        .id(43L)
        .build();
    when(createLogUseCase.createLog(eq(vesselId), eq(request))).thenReturn(response);

    //when-then
    mockMvc.perform(post("/logs/{vesselId}", vesselId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(43));
  }

  @Test
  void shouldReturnBadRequest_whenCreateLogHasNullCoordinates() throws Exception{
    //given
    Long vesselId = 212L;

    CreateLogRequest request = CreateLogRequest.builder()
        .build();

    CreateLogResponse response = CreateLogResponse.builder()
        .id(43L)
        .build();
    when(createLogUseCase.createLog(eq(vesselId), eq(request))).thenReturn(response);

    //when-then
    mockMvc.perform(post("/logs/{vesselId}", vesselId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequest_whenCreateLogHasCoordinatesOutOfScope() throws Exception{
    //given
    Long vesselId = 212L;

    CreateLogRequest request = CreateLogRequest.builder()
        .XCoordinate(200d)
        .YCoordinate(-500d)
        .build();

    CreateLogResponse response = CreateLogResponse.builder()
        .id(43L)
        .build();
    when(createLogUseCase.createLog(eq(vesselId), eq(request))).thenReturn(response);

    //when-then
    mockMvc.perform(post("/logs/{vesselId}", vesselId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(request)))
        .andExpect(status().isBadRequest());
  }
}