package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.ProvisioningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProvisioningController.class)
class ProvisioningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProvisioningService provisioningService;

    @BeforeEach
    void setUp() {
        Mockito.when(provisioningService.getProvisioningFile("aa-bb-cc-11-22-33"))
               .thenReturn("mocked provisioning file content");
    }

    @Test
    void testGetProvisioningFile() throws Exception {
        mockMvc.perform(get("/api/v1/provisioning/aa-bb-cc-11-22-33"))
               .andExpect(status().isOk())
               .andExpect(content().string("mocked provisioning file content"));
    }

    @Test
    void testGetProvisioningFile_NotFound() throws Exception {
        Mockito.when(provisioningService.getProvisioningFile("not-found"))
               .thenReturn(null);

        mockMvc.perform(get("/api/v1/provisioning/not-found"))
               .andExpect(status().isNotFound());
    }
} 