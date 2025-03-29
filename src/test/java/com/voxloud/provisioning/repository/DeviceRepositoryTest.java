package com.voxloud.provisioning.repository;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.entity.Device.DeviceModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void testFindById() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-11-22-33");
        device.setModel(DeviceModel.DESK);
        device.setUsername("john");
        device.setPassword("doe");
        deviceRepository.save(device);

        Device foundDevice = deviceRepository.findById("aa-bb-cc-11-22-33").orElse(null);
        assertThat(foundDevice).isNotNull();
        assertThat(foundDevice.getUsername()).isEqualTo("john");
    }
} 