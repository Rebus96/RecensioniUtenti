package com.example.recensioniutenti.utenteRepository;

import com.example.recensioniutenti.utenteEntity.Recensioni;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageRepository extends PagingAndSortingRepository<Recensioni, Long> {
    Page<Recensioni> findAll(Pageable pageable);
}
