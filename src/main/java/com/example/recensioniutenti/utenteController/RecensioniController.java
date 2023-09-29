package com.example.recensioniutenti.utenteController;
import com.example.recensioniutenti.utenteEntity.Recensioni;
import com.example.recensioniutenti.utenteService.RecensioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/api/rec")
public class RecensioniController {
    @Autowired
    private RecensioniService recensioniService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getAllRecensioni")
    @ResponseBody
    public List<Recensioni> getAllRecensioni(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println("utentecorrente " + currentPrincipalName);
        return recensioniService.getAllRecensioni();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/saveRecensione")
    @ResponseBody
    public Recensioni saveRecensioni(@RequestBody Recensioni recensioni){
        System.out.println("creo recensioni");
        System.out.println(recensioni);
        recensioni.setData(LocalDate.from(LocalDateTime.now()));
        System.out.println(LocalDateTime.now() + "data " );
        return recensioniService.saveRecensioni(recensioni);
    }

    @GetMapping("/getRecensioniById/{id}")
    @ResponseBody
    public Recensioni getRecensioniById(@PathVariable Long id){
        return recensioniService.getRecensioniById(id);
    }

    @DeleteMapping("/deleteRecensioni/{id}")
    @ResponseBody
    public void deleteRecensioni(@PathVariable Long id){
        recensioniService.deleteRecensioni(id);
    }

    @GetMapping("/recensioni/page")
    @ResponseBody
    public Page<Recensioni> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "2") int size){
        Pageable pageable = PageRequest.of(page, size);
        return recensioniService.getAllRecensioni(pageable);

    }

}
