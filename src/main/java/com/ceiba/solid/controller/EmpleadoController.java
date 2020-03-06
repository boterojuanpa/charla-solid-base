package com.ceiba.solid.controller;

import com.ceiba.solid.entity.EmpleadoEntity;
import com.ceiba.solid.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping
    public List<EmpleadoEntity> consultar() {
        return empleadoService.consultar();
    }
}
