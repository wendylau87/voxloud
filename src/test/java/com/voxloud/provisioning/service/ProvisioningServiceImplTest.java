package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.entity.Device.DeviceModel;
import com.voxloud.provisioning.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProvisioningServiceImplTest {

    private ProvisioningServiceImpl provisioningService;
    private DeviceRepository deviceRepository;
    private Environment environment;

    @BeforeEach
    void setUp() {
        deviceRepository = Mockito.mock(DeviceRepository.class);
        environment = Mockito.mock(Environment.class);
        provisioningService = new ProvisioningServiceImpl(deviceRepository, environment);
    }

    @Test
    void testGetProvisioningFile_Desk() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-11-22-33");
        device.setModel(DeviceModel.DESK);
        device.setUsername("john");
        device.setPassword("doe");

        when(deviceRepository.findById(anyString())).thenReturn(Optional.of(device));
        when(environment.getProperty("provisioning.domain", "default_value")).thenReturn("sip.voxloud.com");
        when(environment.getProperty("provisioning.port", "default_value")).thenReturn("5060");
        when(environment.getProperty("provisioning.codecs", "default_value")).thenReturn("G711,G729,OPUS");

        String expected = "username=john\n" +
                          "password=doe\n" +
                          "domain=sip.voxloud.com\n" +
                          "port=5060\n" +
                          "codecs=G711,G729,OPUS\n";

        String result = provisioningService.getProvisioningFile("aa-bb-cc-11-22-33");
        assertEquals(expected, result);
    }

    @Test
    void testGetProvisioningFile_NotFound() {
        when(deviceRepository.findById(anyString())).thenReturn(Optional.empty());

        String result = provisioningService.getProvisioningFile("not-found");
        assertNull(result);
    }
} 