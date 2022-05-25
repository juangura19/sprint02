package com.example.spring02.service.impl;

import com.example.spring02.model.Persona;
import com.example.spring02.repository.PersonaRepository;
import com.example.spring02.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    @Override
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    public Persona create(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public Persona update(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public void delete(long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public Persona findById(long id) {
        Optional<Persona> op =  personaRepository.findById(id);
        return op.isPresent() ? op.get() : new Persona();
    }

    @Override
    public List<Persona> jpql(String apellido) {
        return personaRepository.jpql(apellido);
    }

    @Override
    public List<Persona> sql(String apellido) {
        return personaRepository.sql(apellido);
    }

    @Override
    public Page<Persona> paginacion(Pageable pageable) {
        return personaRepository.paginacion(pageable);
    }
}
