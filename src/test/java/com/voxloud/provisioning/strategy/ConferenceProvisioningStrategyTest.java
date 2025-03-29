package com.voxloud.provisioning.strategy;

import com.voxloud.provisioning.entity.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConferenceProvisioningStrategyTest {

    private ConferenceProvisioningStrategy strategy;
    private Environment environment;

    @BeforeEach
    void setUp() {
        environment = Mockito.mock(Environment.class);
        Mockito.when(environment.getProperty("provisioning.domain","default_value")).thenReturn("sip.voxloud.com");
        Mockito.when(environment.getProperty("provisioning.port","default_value")).thenReturn("5060");
        Mockito.when(environment.getProperty("provisioning.codecs", "default_value")).thenReturn("G711,G729,OPUS");
        strategy = new ConferenceProvisioningStrategy(environment);
    }

    @Test
    void testGenerateProvisioningFile_basic() {
        Device device = new Device();
        device.setUsername("john");
        device.setPassword("doe");

        String expected = "{\n" +
                          "  \"username\": \"john\",\n" +
                          "  \"password\": \"doe\",\n" +
                          "  \"domain\": \"sip.voxloud.com\",\n" +
                          "  \"port\": \"5060\",\n" +
                          "  \"codecs\": [\"G711\",\"G729\",\"OPUS\"]\n" +
                          "}";

        assertEquals(expected, strategy.generateProvisioningFile(device));
    }

    @Test
    void testGenerateProvisioningFile_withOverrideFragment() {
        Device device = new Device();
        device.setUsername("john");
        device.setPassword("doe");
        device.setOverrideFragment("{\"domain\":\"sip.anotherdomain.com\",\"port\":\"5161\",\"timeout\":10}");
        Mockito.when(environment.getProperty("provisioning.domain", "default_value")).thenReturn("sip.anotherdomain.com");
        Mockito.when(environment.getProperty("provisioning.port", "default_value")).thenReturn("5161");

        String expected = "{\n" +
                          "  \"username\": \"john\",\n" +
                          "  \"password\": \"doe\",\n" +
                          "  \"domain\": \"sip.anotherdomain.com\",\n" +
                          "  \"port\": \"5161\",\n" +
                          "  \"codecs\": [\"G711\",\"G729\",\"OPUS\"]\n" +
                          "}";

        assertEquals(expected, strategy.generateProvisioningFile(device));
    }

    @Test
    void testGenerateProvisioningFile_missingDeviceInfo() {
        Device device = new Device();
        device.setUsername(null);
        device.setPassword(null);

        String expected = "{\n" +
                          "  \"username\": \"null\",\n" +
                          "  \"password\": \"null\",\n" +
                          "  \"domain\": \"sip.voxloud.com\",\n" +
                          "  \"port\": \"5060\",\n" +
                          "  \"codecs\": [\"G711\",\"G729\",\"OPUS\"]\n" +
                          "}";

        assertEquals(expected, strategy.generateProvisioningFile(device));
    }

    @Test
    void testGenerateProvisioningFile_invalidOverrideFragment() {
        Device device = new Device();
        device.setUsername("john");
        device.setPassword("doe");
        device.setOverrideFragment("invalid=fragment");

        String expected = "{\n" +
                          "  \"username\": \"john\",\n" +
                          "  \"password\": \"doe\",\n" +
                          "  \"domain\": \"sip.voxloud.com\",\n" +
                          "  \"port\": \"5060\",\n" +
                          "  \"codecs\": [\"G711\",\"G729\",\"OPUS\"]\n" +
                          "}";

        assertEquals(expected, strategy.generateProvisioningFile(device));
    }
} 