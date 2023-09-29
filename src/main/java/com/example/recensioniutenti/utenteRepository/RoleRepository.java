package com.example.recensioniutenti.utenteRepository;

import com.example.recensioniutenti.utenteEntity.Role;
import com.example.recensioniutenti.utenteEntity.Ruoli;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Ruoli, Long> {
    Optional<Ruoli> findByName(Role name);
}
