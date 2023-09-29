package com.example.recensioniutenti.utenteService;

import com.example.recensioniutenti.utenteEntity.Recensioni;
import com.example.recensioniutenti.utenteRepository.PageRepository;
import com.example.recensioniutenti.utenteRepository.RecensioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecensioniService {
    @Autowired
    private RecensioniRepository recensioniRepository;
    @Autowired
    private PageRepository pageRepository;

    public List<Recensioni> getAllRecensioni(){
        return recensioniRepository.findAll();
    }

    public Recensioni saveRecensioni(Recensioni recensioni){
        return recensioniRepository.save(recensioni);
    }

    public Recensioni getRecensioniById(Long id){
        return recensioniRepository.findById(id).get();
    }
    public void deleteRecensioni(Long id){
        recensioniRepository.deleteById(id);
    }

    public Page<Recensioni> getAllRecensioni(Pageable pageable){
        return pageRepository.findAll(pageable);
    }
    public List<Recensioni> orderRecensioni(){
        return recensioniRepository.findAll();
    }
}
