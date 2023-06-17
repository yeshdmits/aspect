package com.example.demo.access.impl;

import com.example.demo.access.AccessService;
import com.example.demo.exception.Forbidden;
import com.example.demo.model.Partner;
import com.example.demo.model.PartnerRepository;
import com.example.demo.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final SecurityService securityService;
    private final PartnerRepository repository;

    @Override
    public void verifyAccessToBusinessRelationships(List<String> brIds) {
        throw new Forbidden("No access to br");
    }

    @Override
    public void verifyAccessToPartners(List<String> partnerIds) {
        String username = securityService.getUsername();
        List<Partner> partners = repository.findAllById(partnerIds);
        partners.forEach(partner -> {
            if(!username.equals(partner.getUserId())) {
                throw new Forbidden("No access to partner");
            }
        });
    }
}
