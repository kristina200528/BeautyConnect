package ru.itis.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.ServiceOffering;
import ru.itis.service.ServiceOfferingService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/masters/{masterId}/services")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/new")
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public ResponseEntity<?> addServiceForMaster(@PathVariable Long masterId, @Valid @RequestBody ServiceOfferingDto newService, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        ServiceOfferingDto serviceOfferingDto=serviceOfferingService.addService(masterId, newService, principal.getName());
        return ResponseEntity.ok(serviceOfferingDto);
    }

    @GetMapping
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public ResponseEntity<List<ServiceOfferingDto>> getAllServicesForMaster(@PathVariable Long masterId, Principal principal) {
        List<ServiceOfferingDto> services = serviceOfferingService.getServicesByMasterId(masterId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{serviceId}")
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public ResponseEntity<ServiceOfferingDto> getServiceById(@PathVariable Long masterId, @PathVariable Long serviceId, Principal principal) {
        ServiceOfferingDto service = serviceOfferingService.getServiceByIdAndMasterId(serviceId, masterId);
        return ResponseEntity.ok(service);
    }

    @PutMapping("/{serviceId}")
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long masterId, @PathVariable Long serviceId, @RequestBody ServiceOfferingDto serviceDto, Principal principal) {
        ServiceOffering updated = serviceOfferingService.updateService(serviceId, serviceDto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{serviceId}")
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public ResponseEntity<Void> deleteService(@PathVariable Long masterId, @PathVariable Long serviceId, Principal principal) {
        serviceOfferingService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }

}
