package com.fleetguard360.alert_management.presentation.controller;

import com.fleetguard360.alert_management.persistence.entity.TipoEncargado;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadResponse;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaUpdateRequest;
import com.fleetguard360.alert_management.service.interfaces.TipoAlertaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoAlertaGraphQLControllerTest {

    @Mock
    private TipoAlertaService tipoAlertaService;

    @InjectMocks
    private TipoAlertaGraphQLController controller;

    private TipoAlertaResponse dummyResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Dummy objects para los campos adicionales
        NivelPrioridadResponse nivelPrioridad = new NivelPrioridadResponse(1, "Alta");
        TipoEncargado tipoEncargado = TipoEncargado.CONDUCTOR; // Usando el enum directamente
        dummyResponse = new TipoAlertaResponse(1, "Alerta Crítica", "Descripción de prueba", nivelPrioridad, tipoEncargado);
    }

    @Test
    void testTipoAlerta() {
        when(tipoAlertaService.getById(1)).thenReturn(dummyResponse);

        TipoAlertaResponse result = controller.tipoAlerta(1);

        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals("Alerta Crítica", result.nombre());
        assertEquals("Descripción de prueba", result.descripcion());
        assertEquals("Alta", result.nivelPrioridad().nombre());
        assertEquals(TipoEncargado.CONDUCTOR, result.tipoEncargado());
        verify(tipoAlertaService, times(1)).getById(1);
    }

    @Test
    void testTipoAlertas() {
        when(tipoAlertaService.listAll()).thenReturn(List.of(dummyResponse));

        List<TipoAlertaResponse> result = controller.tipoAlertas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dummyResponse, result.get(0));
        verify(tipoAlertaService, times(1)).listAll();
    }

    @Test
    void testCreateTipoAlerta() {
        TipoAlertaCreateRequest input = new TipoAlertaCreateRequest("Alerta Crítica", "Descripción de prueba", 1, TipoEncargado.CONDUCTOR);
        when(tipoAlertaService.create(input)).thenReturn(dummyResponse);

        TipoAlertaResponse result = controller.createTipoAlerta(input);

        assertNotNull(result);
        assertEquals(dummyResponse, result);
        verify(tipoAlertaService, times(1)).create(input);
    }

    @Test
    void testUpdateTipoAlerta() {
        TipoAlertaUpdateRequest input = new TipoAlertaUpdateRequest("Alerta Crítica", "Descripción de prueba", 1, TipoEncargado.CONDUCTOR);
        when(tipoAlertaService.update(1, input)).thenReturn(dummyResponse);

        TipoAlertaResponse result = controller.updateTipoAlerta(1, input);

        assertNotNull(result);
        assertEquals(dummyResponse, result);
        verify(tipoAlertaService, times(1)).update(1, input);
    }

    @Test
    void testDeleteTipoAlerta() {
        doNothing().when(tipoAlertaService).delete(1);

        Boolean result = controller.deleteTipoAlerta(1);

        assertTrue(result);
        verify(tipoAlertaService, times(1)).delete(1);
    }
}
