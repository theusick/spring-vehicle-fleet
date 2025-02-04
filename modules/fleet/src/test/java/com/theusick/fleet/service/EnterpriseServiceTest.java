package com.theusick.fleet.service;

import com.theusick.fleet.repository.EnterpriseRepository;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.impl.EnterpriseServiceImpl;
import com.theusick.fleet.service.mapper.EnterpriseMapper;
import com.theusick.fleet.service.model.EnterpriseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnterpriseServiceTest {

    @Mock
    private EnterpriseRepository enterpriseRepository;

    @Mock
    private EnterpriseMapper enterpriseMapper;

    @InjectMocks
    private EnterpriseServiceImpl enterpriseService;

    private EnterpriseEntity testEnterpriseEntity;
    private EnterpriseModel testEnterpriseModel;

    @BeforeEach
    void setUp() {
        testEnterpriseEntity = new EnterpriseEntity();
        testEnterpriseEntity.setId(1L);
        testEnterpriseEntity.setName("testName");
        testEnterpriseEntity.setCity("testCity");

        testEnterpriseModel = new EnterpriseModel();
        testEnterpriseModel.setId(1L);
        testEnterpriseModel.setName("testName");
        testEnterpriseModel.setCity("testCity");
    }

    @Test
    @DisplayName("Should return enterpriseModel when enterpriseEntity exists")
    void getEnterpriseExisting() throws NoSuchEnterpriseException {
        when(enterpriseRepository.findById(1L))
            .thenReturn(Optional.of(testEnterpriseEntity));
        when(enterpriseMapper.enterpriseModelFromEntity(testEnterpriseEntity))
            .thenReturn(testEnterpriseModel);

        EnterpriseModel enterpriseModel = enterpriseService.getEnterprise(1L);

        assertNotNull(enterpriseModel);
        assertEquals("testName", enterpriseModel.getName());
        verify(enterpriseRepository, times(1)).findById(1L);
        verify(enterpriseMapper, times(1))
            .enterpriseModelFromEntity(testEnterpriseEntity);
    }

    @Test
    @DisplayName("Should throw NoSuchEnterpriseException when enterpriseEntity does not exist")
    void getEnterpriseNotExisting() {
        when(enterpriseRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(NoSuchEnterpriseException.class,
            () -> enterpriseService.getEnterprise(1L));
        verify(enterpriseRepository, times(1)).findById(1L);
    }

}
