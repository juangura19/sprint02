package com.example.spring02.api;

import static com.example.spring02.util.Util.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.example.spring02.exception.ModeloNotFoundException;
import com.example.spring02.model.Persona;
import com.example.spring02.service.PersonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonaApiTest {

    private MockMvc mockMvc;

    @InjectMocks
    PersonaApi personaApi;

    @Mock
    PersonaService personaService;


    private List<Persona> list;
    private Persona persona;
    private Persona body;
    private Persona responseError;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.personaApi).build();

        list = new ArrayList<>();
        persona  = new Persona();
        persona.setId(1L);
        persona.setNombres("nombre 01");
        persona.setApellidos("apellidos 01");
        persona.setDni("00000001");
        persona.setEstado(true);
        list.add(persona);

        body  = new Persona();
        body.setNombres("nombre 01");
        body.setApellidos("apellidos 01");
        body.setDni("00000001");

        responseError =  new Persona();
        responseError.setNombres("no");
        responseError.setApellidos("apellidos 01");
        responseError.setDni("0000000A");

    }

    @Test
    public void when_findAllPersona_ok() throws Exception {

        when(personaService.findAll())
            .thenReturn(list);

        MvcResult mvcResult =  mockMvc.perform(get("/personas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombres").value("nombre 01"))
            .andReturn();

        assertEquals("application/json",mvcResult.getResponse().getContentType());

        verify(personaService).findAll();
    }

    @Test
    public void when_createPersona_ok() throws Exception {
        when(personaService.create(any(Persona.class)))
            .thenReturn(persona);

        mockMvc.perform(post("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(body)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nombres").value("nombre 01"))
            .andReturn();

        verify(personaService).create(any());
    }

    @Test
    public void when_createPersona_error() throws Exception {

        mockMvc.perform(post("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(responseError)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void when_udpatePersona_ok() throws Exception {
        when(personaService.update(any(Persona.class)))
            .thenReturn(persona);

        mockMvc.perform(put("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(persona)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nombres").value("nombre 01"))
            .andReturn();

        verify(personaService).update(any());
    }

    @Test
    public void when_updatePersona_error() throws Exception {

        mockMvc.perform(post("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(responseError)))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void when_findById_ok() throws Exception {

        doReturn(persona).when(personaService).findById(1L);

        mockMvc.perform(get("/personas/{id}",1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nombres").value("nombre 01"))
            .andReturn();

    }

    @Test
    public void when_findById_error() throws Exception {

        doReturn(new Persona()).when(personaService).findById(1L);

        mockMvc.perform(get("/personas/{id}",1L))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModeloNotFoundException))
            .andExpect(result -> assertEquals("ID no encontrado", result.getResolvedException().getMessage()));
    }

    @Test
    public void when_delete_ok() throws Exception {

        doReturn(persona).when(personaService).findById(1L);

        doNothing().when(personaService).delete(1L);

        mockMvc.perform(delete("/personas/{id}",1L))
            .andExpect(status().isNoContent());

    }

    @Test
    public void when_delete_errror() throws Exception {

        doReturn(new Persona()).when(personaService).findById(1L);

        mockMvc.perform(delete("/personas/{id}",1L))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModeloNotFoundException))
            .andExpect(result -> assertEquals("ID no encontrado", result.getResolvedException().getMessage()));

    }

}
