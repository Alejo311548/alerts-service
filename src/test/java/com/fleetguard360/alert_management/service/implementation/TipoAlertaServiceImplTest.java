package com.fleetguard360.alert_management.service.implementation;

import com.fleetguard360.alert_management.configuration.mapper.TipoAlertaMapper;
import com.fleetguard360.alert_management.persistence.entity.NivelPrioridad;
import com.fleetguard360.alert_management.persistence.entity.TipoAlerta;
import com.fleetguard360.alert_management.persistence.repository.NivelPrioridadRepository;
import com.fleetguard360.alert_management.persistence.repository.TipoAlertaRepository;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadResponse;
import com.fleetguard360.alert_management.persistence.entity.TipoEncargado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoAlertaServiceImplTest {

    @Mock
    private TipoAlertaRepository tipoAlertaRepository;

    @Mock
    private NivelPrioridadRepository nivelPrioridadRepository;

    @Mock
    private TipoAlertaMapper mapper;

    @InjectMocks
    private TipoAlertaServiceImpl tipoAlertaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnTipoAlertaResponse_WhenDataIsValid() {
        // --- Arrange ---
        NivelPrioridad nivelPrioridad = new NivelPrioridad();
        nivelPrioridad.setId(1);
        nivelPrioridad.setNombre("Alta");

        TipoAlertaCreateRequest request = new TipoAlertaCreateRequest(
                "Alerta de prueba",
                "Prueba de descripción",
                1,
                TipoEncargado.OPERADOR_LOGISTICA
        );

        TipoAlerta savedEntity = new TipoAlerta();
        savedEntity.setId(1);
        savedEntity.setNombre(request.nombre());
        savedEntity.setDescripcion(request.descripcion());
        savedEntity.setNivelPrioridad(nivelPrioridad);
        savedEntity.setTipoEncargado(request.tipoEncargado());

        NivelPrioridadResponse nivelResponse = new NivelPrioridadResponse(1, "Alta");
        TipoAlertaResponse expectedResponse = new TipoAlertaResponse(
                1,
                request.nombre(),
                request.descripcion(),
                nivelResponse,
                request.tipoEncargado()
        );

        when(tipoAlertaRepository.existsByNombreIgnoreCase(anyString())).thenReturn(false);
        when(nivelPrioridadRepository.findById(1)).thenReturn(Optional.of(nivelPrioridad));
        when(tipoAlertaRepository.save(any(TipoAlerta.class))).thenReturn(savedEntity);
        when(mapper.toResponse(any(TipoAlerta.class))).thenReturn(expectedResponse);

        // --- Act ---
        TipoAlertaResponse result = tipoAlertaService.create(request);

        // --- Assert ---
        assertNotNull(result);
        assertEquals(expectedResponse.id(), result.id());
        assertEquals(expectedResponse.nombre(), result.nombre());
        assertEquals(expectedResponse.descripcion(), result.descripcion());
        assertEquals(expectedResponse.nivelPrioridad().nombre(), result.nivelPrioridad().nombre());
        assertEquals(expectedResponse.tipoEncargado(), result.tipoEncargado());

        // Verificar interacciones con mocks
        verify(tipoAlertaRepository).existsByNombreIgnoreCase(request.nombre());
        verify(nivelPrioridadRepository).findById(1);
        verify(tipoAlertaRepository).save(any(TipoAlerta.class));
        verify(mapper).toResponse(savedEntity);
    }
}
