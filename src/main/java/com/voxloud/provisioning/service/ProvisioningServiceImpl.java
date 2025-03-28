package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.strategy.ConferenceProvisioningStrategy;
import com.voxloud.provisioning.strategy.DeskProvisioningStrategy;
import com.voxloud.provisioning.strategy.ProvisioningStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {

    private final DeviceRepository deviceRepository;
    private final Environment environment;

    @Autowired
    public ProvisioningServiceImpl(DeviceRepository deviceRepository, Environment environment) {
        this.deviceRepository = deviceRepository;
        this.environment = environment;
    }

    @Override
    public String getProvisioningFile(String macAddress) {
        return deviceRepository.findById(macAddress)
            .map(this::generateProvisioningFile)
            .orElse(null);
    }

    private String generateProvisioningFile(Device device) {
        ProvisioningStrategy strategy;
        switch (device.getModel()) {
            case DESK:
                strategy = new DeskProvisioningStrategy(environment);
                break;
            case CONFERENCE:
                strategy = new ConferenceProvisioningStrategy(environment);
                break;
            default:
                throw new IllegalArgumentException("Unsupported device model: " + device.getModel());
        }
        return strategy.generateProvisioningFile(device);
    }
}
