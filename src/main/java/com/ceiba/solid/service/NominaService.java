package com.ceiba.solid.service;

import com.ceiba.solid.repository.PagoRepository;
import com.ceiba.solid.repository.ParametroSistemaRepositorio;
import com.ceiba.solid.entity.EmpleadoEntity;
import com.ceiba.solid.entity.PagoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NominaService {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ParametroSistemaRepositorio parametroSistemaRepositorio;

    @Autowired
    private PagoRepository pagoRepository;


    public List<PagoEntity> generarSalario() {
        List<EmpleadoEntity> empleadosEntity = empleadoService.consultar();
        List<PagoEntity> pagoEntities = new ArrayList<>();

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        for (EmpleadoEntity empleadoEntity : empleadosEntity) {
            if (empleadoEntity.getCargo() != null) {
                if (empleadoEntity.getCargo().equals("OPERARIO")) {
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(parametroSistemaRepositorio.findById(1l).get().getValue())
                            + Double.parseDouble(parametroSistemaRepositorio.findById(2l).get().getValue()));


                    c.add(Calendar.DATE, 5);
                    date = c.getTime();
                    pagoEntity.setFechaDesembolso(date);
                    pagoEntities.add(pagoEntity);

                }
                if (empleadoEntity.getCargo().equals("SUPERVISOR")) {

                    c.add(Calendar.DATE, 3);
                    date = c.getTime();

                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(parametroSistemaRepositorio.findById(1l).get().getValue()) * 2);
                    pagoEntity.setFechaDesembolso(date);
                    pagoEntities.add(pagoEntity);
                }
                if (empleadoEntity.getCargo().equals("GERENTE")) {
                    date = c.getTime();
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(parametroSistemaRepositorio.findById(3l).get().getValue()));
                    pagoEntity.setFechaDesembolso(date);
                    pagoEntities.add(pagoEntity);
                }
            }
        }

        pagoRepository.saveAll(pagoEntities);

        return pagoEntities;
    }

    public void pagarProveedor(PagoEntity pago) {

        if(pago.getIdProveedor() != null && pago.getValor() != null ){
            pagoRepository.save(pago);

        }

    }
}
