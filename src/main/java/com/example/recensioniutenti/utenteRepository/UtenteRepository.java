package com.example.recensioniutenti.utenteRepository;

import com.example.recensioniutenti.utenteEntity.Role;
import com.example.recensioniutenti.utenteEntity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);

    Optional<Utente> findByRole(Role role);

}

