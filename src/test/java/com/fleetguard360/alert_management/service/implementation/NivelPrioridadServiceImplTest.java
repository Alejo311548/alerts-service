package com.fleetguard360.alert_management.service.implementation;



import com.fleetguard360.alert_management.configuration.mapper.NivelPrioridadMapper;
import com.fleetguard360.alert_management.persistence.entity.NivelPrioridad;
import com.fleetguard360.alert_management.persistence.repository.NivelPrioridadRepository;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadResponse;
import com.fleetguard360.alert_management.service.exception.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NivelPrioridadServiceImplTest {

    @Mock
    private NivelPrioridadRepository nivelPrioridadRepository;

    @Mock
    private NivelPrioridadMapper mapper;

    @InjectMocks
    private NivelPrioridadServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void create_ShouldSaveAndReturnResponse_WhenValidRequest() {
        // Arrange
        NivelPrioridadCreateRequest request = new NivelPrioridadCreateRequest("Alta");
        NivelPrioridad entity = new NivelPrioridad();
        entity.setId(1);
        entity.setNombre("Alta");

        NivelPrioridadResponse expectedResponse = new NivelPrioridadResponse(1, "Alta");

        when(nivelPrioridadRepository.existsByNombreIgnoreCase("Alta")).thenReturn(false);
        when(nivelPrioridadRepository.save(any(NivelPrioridad.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(expectedResponse);

        // Act
        NivelPrioridadResponse result = service.create(request);

        // Assert
        assertNotNull(result);
        assertEquals("Alta", result.nombre());
        verify(nivelPrioridadRepository).save(any(NivelPrioridad.class));
    }


    @Test
    void create_ShouldThrowConflictException_WhenNameAlreadyExists() {
        // Arrange
        NivelPrioridadCreateRequest request = new NivelPrioridadCreateRequest("Media");
        when(nivelPrioridadRepository.existsByNombreIgnoreCase("Media")).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> service.create(request));
        verify(nivelPrioridadRepository, never()).save(any());
    }
}

