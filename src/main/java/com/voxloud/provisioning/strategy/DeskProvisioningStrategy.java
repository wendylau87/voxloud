package com.voxloud.provisioning.strategy;

import com.voxloud.provisioning.entity.Device;
import org.springframework.core.env.Environment;

public class DeskProvisioningStrategy extends AbstractProvisioningStrategy {

    public DeskProvisioningStrategy(Environment environment) {
        super(environment);
    }

    @Override
    public String generateProvisioningFile(Device device) {
        return new StringBuilder()
            .append("username=").append(device.getUsername()).append("\n")
            .append("password=").append(device.getPassword()).append("\n")
            .append("domain=").append(getProperty("provisioning.domain", device.getOverrideFragment())).append("\n")
            .append("port=").append(getProperty("provisioning.port", device.getOverrideFragment())).append("\n")
            .append("codecs=").append(getProperty("provisioning.codecs", device.getOverrideFragment())).append("\n")
            .toString();
    }
} 