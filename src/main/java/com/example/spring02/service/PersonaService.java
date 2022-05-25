package com.example.spring02.service;

import com.example.spring02.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonaService {

    List<Persona> findAll();

    Persona create(Persona persona);

    Persona update(Persona persona);

    void delete(long id);

    Persona findById(long id);

    List<Persona> jpql(String apellido);

    List<Persona> sql(String apellido);

    Page<Persona> paginacion(Pageable pageable);
}
