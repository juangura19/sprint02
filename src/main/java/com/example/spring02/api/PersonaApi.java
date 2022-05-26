package com.example.spring02.api;

import com.example.spring02.exception.ModeloNotFoundException;
import com.example.spring02.model.Persona;
import com.example.spring02.service.PersonaService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "personas")
public class PersonaApi {

    @Autowired
    PersonaService personaService;

    @GetMapping
    public ResponseEntity<List<Persona>> findAll(){
        return ResponseEntity.ok(personaService.findAll());
    }

    @PostMapping
    public ResponseEntity<Persona> create(@Valid @RequestBody Persona persona){
        Persona personResponse = personaService.create(persona);
        return new ResponseEntity<>(personResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Persona> update(@Valid @RequestBody Persona persona){
        return ResponseEntity.ok(personaService.update(persona));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> findById(@PathVariable("id") long id){
        Persona persona = personaService.findById(id);
        if(persona.getId() == 0){
            throw  new ModeloNotFoundException("ID no encontrado");
        }
        return ResponseEntity.ok(personaService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        Persona persona = personaService.findById(id);
        if(persona.getId() == 0){
            throw  new ModeloNotFoundException("ID no encontrado");
        }
        personaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/jpql")
    public ResponseEntity<List<Persona>> jpql(@RequestParam("apellido") String apellido){
        return ResponseEntity.ok(personaService.jpql(apellido));
    }

    @GetMapping("/sql")
    public ResponseEntity<List<Persona>> sql(@RequestParam("apellido") String apellido){
        return ResponseEntity.ok(personaService.sql(apellido));
    }

    @GetMapping("/paginacion")
    public ResponseEntity<Page<Persona>> paginacion( Pageable pageable){
        return ResponseEntity.ok(personaService.paginacion(pageable));
    }
}
