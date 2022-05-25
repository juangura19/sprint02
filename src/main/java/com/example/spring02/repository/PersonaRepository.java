package com.example.spring02.repository;

import com.example.spring02.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query(value = "SELECT p FROM Persona p WHERE p.apellidos = ?1")
    List<Persona> jpql(String apellido);

    @Query(value = "SELECT * FROM Persona p WHERE p.apellidos = ?1",
    nativeQuery = true)
    List<Persona> sql (String apellido);

    @Query(value = "SELECT p FROM Persona p ")
    Page<Persona> paginacion(Pageable pageable);

}
