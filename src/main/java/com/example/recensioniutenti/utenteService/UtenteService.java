package com.example.recensioniutenti.utenteService;

import com.example.recensioniutenti.utenteEntity.Utente;
import com.example.recensioniutenti.utenteRepository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;


    public List<Utente> getAllUtenti(){
        return utenteRepository.findAll();
    }
    public Utente saveUtente(Utente utente){
        return utenteRepository.save(utente);
    }

    public Utente getUtenteById(Long id){
        return utenteRepository.findById(id).get();
    }

    public void deleteUtente(Long id){
        utenteRepository.deleteById(id);
    }
}
