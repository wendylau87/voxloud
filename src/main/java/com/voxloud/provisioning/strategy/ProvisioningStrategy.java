package com.voxloud.provisioning.strategy;

import com.voxloud.provisioning.entity.Device;

public interface ProvisioningStrategy {
    String generateProvisioningFile(Device device);
} 