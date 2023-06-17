package com.example.demo.controller;

import com.example.demo.access.CheckAccess;
import com.example.demo.access.CheckAccessType;
import com.example.demo.exception.NotFound;
import com.example.demo.model.Partner;
import com.example.demo.model.PartnerRepository;
import com.example.demo.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartnerController {

    private final SecurityService securityService;
    private final PartnerRepository partnerRepository;

    @GetMapping("/api/partner/{id}")
    public Partner getPartner(@PathVariable @CheckAccess(CheckAccessType.PARTNER) String id) {
        return partnerRepository.findById(id).orElseThrow(() -> new NotFound("No partner found in db"));
    }

    @GetMapping("/api/partners")
    public List<Partner> getPartnerBulk(@RequestBody @CheckAccess(CheckAccessType.PARTNER) List<String> ids) {
        List<Partner> allByIdIn = partnerRepository.findAllByIdIn(ids);
        if (allByIdIn.isEmpty()) {
            throw new NotFound("No partners found in db");
        }
        return allByIdIn;
    }

    @GetMapping("/api/partners/full")
    public List<Partner> getPartners() {
        return partnerRepository.findAll();
    }


    @PostMapping("/api/partner")
    public Partner createPartner(@RequestBody Partner partner) {
        partner.setId(null);
        partner.setUserId(securityService.getUsername());
        return partnerRepository.save(partner);
    }

    @PutMapping("/api/partner/{id}")
    public Partner updatePartner(@PathVariable String id, @RequestBody Partner partner) {
        partner.setId(id);
        partner.setUserId(securityService.getUsername());
        return partnerRepository.save(partner);
    }

    @DeleteMapping("/api/partner/{id}")
    public void deletePartner(@PathVariable String id) {
        partnerRepository.deleteById(id);
    }
}
