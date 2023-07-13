package com.wicio.shiplog.vessel.api;

import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wicio.shiplog.config.SecurityConfig;
import com.wicio.shiplog.vessel.api.dto.CreateVesselRequest;
import com.wicio.shiplog.vessel.api.dto.CreateVesselResponse;
import com.wicio.shiplog.vessel.api.dto.VesselResponse;
import com.wicio.shiplog.vessel.application.CreateVesselUseCase;
import com.wicio.shiplog.vessel.application.GetVesselUseCase;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = VesselController.class)
@MockitoSettings
class VesselControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetVesselUseCase getVesselUseCase;
  @MockBean
  private CreateVesselUseCase createVesselUseCase;

  @Test
  void testGetVessel() throws Exception {
    //given
    Long vesselId = 212L;

    when(getVesselUseCase.getVesselById(vesselId)).thenReturn(VesselResponse.builder()
        .name("test")
        .id(652L)
        .productionDate(Instant.parse("2021-11-08T13:32:00.00Z"))
        .build());

    //when-then
    mockMvc.perform(get("/vessels/{vesselId}", vesselId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("test"))
        .andExpect(jsonPath("$.id").value(652))
        .andExpect(jsonPath("$.productionDate").value("2021-11-08T13:32:00Z"));
  }

  @Test
  void testCreateLog() throws Exception {
    //given
    CreateVesselRequest createVesselRequest = CreateVesselRequest.builder()
        .name("test")
        .productionDate(Instant.parse("2021-11-08T13:32:00.00Z"))
        .build();

    when(createVesselUseCase.createVessel(eq(createVesselRequest))).thenReturn(
        CreateVesselResponse.builder()
            .id(652L)
            .build());
    //when-then
    mockMvc.perform(post("/vessels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(createVesselRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(652));

    verify(createVesselUseCase).createVessel(
        assertArg(it -> {
          assertThat(it.name()).isEqualTo("test");
          assertThat(it.productionDate()).isEqualTo(Instant.parse("2021-11-08T13:32:00.00Z"));
        }));
  }

  private String toJsonString(CreateVesselRequest createVesselRequest)
      throws JsonProcessingException {
    return objectMapper().writeValueAsString(createVesselRequest);
  }

  private ObjectMapper objectMapper() {
    return JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();
  }
}