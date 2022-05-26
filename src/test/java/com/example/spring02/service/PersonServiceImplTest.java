package com.example.spring02.service;

import com.example.spring02.model.Persona;
import com.example.spring02.repository.PersonaRepository;
import com.example.spring02.service.impl.PersonaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceImplTest {

    @InjectMocks
    PersonaServiceImpl personaService;

    @Mock
    PersonaRepository personaRepository;

    private List<Persona> list;
    private Persona persona;
    private Persona body;
    private Persona responseError;

    @BeforeEach
    public void setUp(){
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
        responseError.setNombres("nombre 01");
        responseError.setApellidos("apellidos 01");

    }

    @Test
    public void when_findAllPersona_ok(){
        when(personaRepository.findAll())
            .thenReturn(list);

        assertThat(personaService.findAll()).isEqualTo(list);
        verify(personaRepository).findAll();
    }

    @Test
    public void when_createPersona_ok(){
        when(personaRepository.save(any(Persona.class)))
            .thenReturn(persona);

        assertThat(personaService.create(body)).isEqualTo(persona);
        verify(personaRepository).save(any());
    }

    @Test
    public void when_createPersona_null(){
        when(personaRepository.save(any(Persona.class)))
            .thenReturn(null);

        assertNull(personaService.create(responseError));
        verify(personaRepository).save(any());
    }

    @Test
    public void when_updatePersona_ok(){
        when(personaRepository.save(any(Persona.class)))
            .thenReturn(persona);

        assertThat(personaService.create(persona)).isEqualTo(persona);
        verify(personaRepository).save(any());
    }

    @Test
    public void when_updatePersona_null(){
        when(personaRepository.save(any(Persona.class)))
            .thenReturn(null);

        assertNull(personaService.create(responseError));
        verify(personaRepository).save(any());
    }

    @Test
    public void when_deletePersona_ok(){
        doNothing().when(personaRepository).deleteById(anyLong());
        personaService.delete(1L);

        verify(personaRepository).deleteById(1L);
    }

    @Test
    public void when_getPersona_ok(){
        when(personaRepository.findById(anyLong()))
            .thenReturn(Optional.of(persona));

        assertThat(personaService.findById(1L)).isEqualTo(persona);
        verify(personaRepository).findById(anyLong());
    }

    @Test
    public void when_getPersona_empty(){
        when(personaRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThat(personaService.findById(1L)).isEqualTo(new Persona());
        verify(personaRepository).findById(anyLong());
    }
}
