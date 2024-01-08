package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.HealthMetricsResponse;
import com.kenzie.appserver.controller.model.HealthMetricsUpdateRequest;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import com.kenzie.appserver.service.HealthMetricsService;
import com.kenzie.appserver.service.model.HealthMetrics;
import com.kenzie.appserver.service.model.WeightUnit;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.com.google.common.base.Verify.verify;

@IntegrationTest
public class HealthMetricsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    HealthMetricsService metricsService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getHealthMetrics_Exists() throws Exception {
        String metricsId = mockNeat.strings().valStr();
        metricsService.resetHealthMetrics(metricsId);

        mvc.perform(get("/healthMetrics/{userId}", metricsId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userId")
                        .isString())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testUpdateHealthMetrics() throws Exception {
        String userId = mockNeat.strings().valStr();
        metricsService.resetHealthMetrics(userId);

        HealthMetricsUpdateRequest request = new HealthMetricsUpdateRequest();
        request.setUserId(userId);
        request.setWeight(75.0);
        request.setWeightUnit(WeightUnit.KG);

        HealthMetricsResponse response = metricsService.updateHealthMetrics(request);
        when(metricsService.updateHealthMetrics(request)).thenReturn(response);

        mvc.perform(post("/healthMetrics/update")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }
    @Test
    public void testDeleteHealthMetrics() throws Exception {
        String userId = "testUserId";
        metricsService.resetHealthMetrics(userId);

        mvc.perform(delete("/healthMetrics/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(metricsService, times(1)).deleteHealthMetrics(userId);
    }
    @Test
    public void testResetHealthMetrics() throws Exception {
        String userId = "testUserId";
        metricsService.resetHealthMetrics(userId);

        mvc.perform(post("/healthMetrics/reset/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(metricsService, times(1)).resetHealthMetrics(userId);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
