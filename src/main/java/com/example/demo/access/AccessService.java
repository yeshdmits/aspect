package com.example.demo.access;

import java.util.List;
import java.util.UUID;

public interface AccessService {
    void verifyAccessToBusinessRelationships(List<String> brIds);
    void verifyAccessToPartners(List<String> partnerIds);
}
