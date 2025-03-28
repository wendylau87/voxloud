package com.voxloud.provisioning.strategy;

import com.voxloud.provisioning.entity.Device;
import org.springframework.core.env.Environment;

public abstract class AbstractProvisioningStrategy implements ProvisioningStrategy {

    protected final Environment environment;

    public AbstractProvisioningStrategy(Environment environment) {
        this.environment = environment;
    }

    protected String getProperty(String key, String overrideFragment) {
        if (overrideFragment != null && overrideFragment.contains(key)) {
            String[] lines = overrideFragment.split("\n");
            for (String line : lines) {
                if (line.startsWith(key)) {
                    return line.split("=")[1].trim();
                }
            }
        }
        return environment.getProperty(key, "default_value");
    }

    @Override
    public abstract String generateProvisioningFile(Device device);
} 