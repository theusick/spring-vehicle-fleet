package com.theusick.fleet.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class EnterpriseServiceTest {

    @InjectMocks
    EnterpriseService enterpriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
