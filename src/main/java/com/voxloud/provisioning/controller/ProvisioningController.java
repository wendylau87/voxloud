package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.ProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {

    @Autowired
    private ProvisioningService provisioningService;

    @GetMapping("/provisioning/{macAddress}")
    public ResponseEntity<String> getProvisioningFile(@PathVariable String macAddress) {
        String provisioningFile = provisioningService.getProvisioningFile(macAddress);
        if (provisioningFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(provisioningFile);
    }
}