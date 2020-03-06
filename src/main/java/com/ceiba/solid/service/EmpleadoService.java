package com.ceiba.solid.service;

import com.ceiba.solid.repository.EmpleadoRepository;
import com.ceiba.solid.entity.EmpleadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<EmpleadoEntity> consultar(){
        return empleadoRepository.findAll();
    }
}
