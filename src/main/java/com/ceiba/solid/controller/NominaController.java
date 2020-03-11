package com.ceiba.solid.controller;

import com.ceiba.solid.entity.PagoEntity;
import com.ceiba.solid.service.NominaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("nomina")
public class NominaController {

    @Autowired
    NominaService salarioService;

    @GetMapping("generar-salario")
    public List<PagoEntity> generar() {
        return salarioService.generarSalario();
    }

    @PostMapping("pagar-proveedor")
    public void pagarProveedor(@RequestBody PagoEntity pagoEntity) {
        salarioService.pagarProveedor(pagoEntity);
    }

}
