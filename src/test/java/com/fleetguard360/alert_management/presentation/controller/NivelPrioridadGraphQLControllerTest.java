package com.fleetguard360.alert_management.presentation.controller;

import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadResponse;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadUpdateRequest;
import com.fleetguard360.alert_management.service.interfaces.NivelPrioridadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NivelPrioridadGraphQLControllerTest {

    @Mock
    private NivelPrioridadService nivelPrioridadService;

    @InjectMocks
    private NivelPrioridadGraphQLController controller;

    private NivelPrioridadResponse dummyResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Solo dos campos según el record
        dummyResponse = new NivelPrioridadResponse(1, "Alta");
    }

    @Test
    void testNivelPrioridad() {
        when(nivelPrioridadService.getById(1)).thenReturn(dummyResponse);

        NivelPrioridadResponse result = controller.nivelPrioridad(1);

        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals("Alta", result.nombre());
        verify(nivelPrioridadService, times(1)).getById(1);
    }

    @Test
    void testNivelesPrioridad() {
        when(nivelPrioridadService.listAll()).thenReturn(List.of(dummyResponse));

        List<NivelPrioridadResponse> result = controller.nivelesPrioridad();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dummyResponse, result.get(0));
        verify(nivelPrioridadService, times(1)).listAll();
    }

    @Test
    void testCreateNivelPrioridad() {
        NivelPrioridadCreateRequest input = new NivelPrioridadCreateRequest("Alta");
        when(nivelPrioridadService.create(input)).thenReturn(dummyResponse);

        NivelPrioridadResponse result = controller.createNivelPrioridad(input);

        assertNotNull(result);
        assertEquals(dummyResponse, result);
        verify(nivelPrioridadService, times(1)).create(input);
    }

    @Test
    void testUpdateNivelPrioridad() {
        NivelPrioridadUpdateRequest input = new NivelPrioridadUpdateRequest("Alta");
        when(nivelPrioridadService.update(1, input)).thenReturn(dummyResponse);

        NivelPrioridadResponse result = controller.updateNivelPrioridad(1, input);

        assertNotNull(result);
        assertEquals(dummyResponse, result);
        verify(nivelPrioridadService, times(1)).update(1, input);
    }

    @Test
    void testDeleteNivelPrioridad() {
        doNothing().when(nivelPrioridadService).delete(1);

        Boolean result = controller.deleteNivelPrioridad(1);

        assertTrue(result);
        verify(nivelPrioridadService, times(1)).delete(1);
    }
}
