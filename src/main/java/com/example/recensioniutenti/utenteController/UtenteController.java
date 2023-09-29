package com.example.recensioniutenti.utenteController;

import com.example.recensioniutenti.Config.*;
import com.example.recensioniutenti.utenteEntity.*;

import com.example.recensioniutenti.utenteRepository.RoleRepository;
import com.example.recensioniutenti.utenteRepository.UtenteRepository;
import com.example.recensioniutenti.utenteService.JwtService;
import com.example.recensioniutenti.utenteService.UtenteService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Base64Util.encode;


@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UtenteController {
    @Autowired
    UtenteService utenteService;
    @Autowired
    public final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UtenteRepository utenteRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request){
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                   request.getEmail(),
                       request.getPassword()
               )
       );
       SecurityContextHolder.getContext().setAuthentication(authentication);
        Utente userDetails = (Utente) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new UserInfoResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest request){

        Utente utente = new Utente(request.getFirstname(),request.getEmail(), passwordEncoder.encode(request.getPassword()));

        Set<String> stringRoles = request.getRole();
        Set<Ruoli> roles = new HashSet<>();

        if (stringRoles == null){
            Ruoli ruoloUtente = roleRepository.findByName(Role.ROLE_USER).orElseThrow(()
            -> new RuntimeException("Ruolo utente non trovato"));
            roles.add(ruoloUtente);
        } else {
            stringRoles.forEach(role -> {
                switch (role){
                    case "admin":
                        Ruoli ruoloAdmin = roleRepository.findByName(Role.ROLE_ADMIN).orElseThrow(
                                () -> new RuntimeException("Ruolo admin non trovato"));
                        roles.add(ruoloAdmin);
                        break;
                        case "agency":
                            Ruoli ruoloAgency = roleRepository.findByName(Role.ROLE_AGENCY).orElseThrow(
                                    () -> new RuntimeException("Ruolo aziendda non trovato"));
                            roles.add(ruoloAgency);
                            break;

                }
            });

        }

        utente.setRoles(roles.stream().toList());
        utenteRepository.save(utente);
        System.out.println(utente);
     return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/getAllUtenti")
    @ResponseBody
    public List<Utente> getAllUtenti(){
        return utenteService.getAllUtenti();
    }

    @PostMapping("/saveUtente")
    @ResponseBody
    public Utente saveUtente(Utente utente){
        return utenteService.saveUtente(utente);
    }

    @GetMapping("/getUtenteById/{idUser}")
    @ResponseBody
    public Utente getUtenteById(@PathVariable Long idUser){
        return utenteService.getUtenteById(idUser);
    }

    @DeleteMapping("/deleteUtente/{idUser}")
    @ResponseBody
    public void deleteUtente(@PathVariable Long idUser){
        utenteService.deleteUtente(idUser);
    }

}
