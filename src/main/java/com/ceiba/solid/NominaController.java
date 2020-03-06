package com.ceiba.solid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("nomina")
public class NominaController {

    @Autowired
    NominaService salarioService;

    @GetMapping("generar-salario")
    public List<PagoEntity> generar(){
        return salarioService.generarSalario();
    }

}
