package br.com.danilocassiano.to_do_list.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")

public class meuPrimeiroController {

    @GetMapping("/")
    public String primeiraMensagem(){
        return "Funcionou";
    }
    
}
