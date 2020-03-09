package com.ceiba.solid.service;

import com.ceiba.solid.entity.EmpleadoEntity;
import com.ceiba.solid.entity.PagoEntity;
import com.ceiba.solid.entity.ParametroSistemaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class NominaService {

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public List<PagoEntity> generarSalario() {
        List<EmpleadoEntity> empleadosEntity = entityManager.createQuery("SELECT e FROM EMPLEADO e", EmpleadoEntity.class).getResultList();
        List<PagoEntity> pagoEntities = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);


        for (EmpleadoEntity empleadoEntity : empleadosEntity) {
            if (empleadoEntity.getCargo() != null) {
                if (empleadoEntity.getCargo().equals("OPERARIO")) {
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(
                            Double.parseDouble(entityManager.find(ParametroSistemaEntity.class, 1l).getValue()) +
                                    Double.parseDouble(entityManager.find(ParametroSistemaEntity.class, 2l).getValue())
                    );

                    c.add(Calendar.DATE, 5);
                    pagoEntity.setFechaDesembolso(c.getTime());
                    pagoEntities.add(pagoEntity);

                }
                if (empleadoEntity.getCargo().equals("SUPERVISOR")) {

                    c.add(Calendar.DATE, 3);
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(entityManager.find(ParametroSistemaEntity.class, 1l).getValue()) * 2);
                    pagoEntity.setFechaDesembolso(c.getTime());
                    pagoEntities.add(pagoEntity);
                }
                if (empleadoEntity.getCargo().equals("GERENTE")) {
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(entityManager.find(ParametroSistemaEntity.class, 3l).getValue()));
                    pagoEntity.setFechaDesembolso(c.getTime());
                    pagoEntities.add(pagoEntity);
                }
            }
        }

        pagoEntities.forEach(pago -> entityManager.persist(pago));

        return pagoEntities;
    }

    @Transactional
    public void pagarProveedor(PagoEntity pago) {

        if (pago.getIdProveedor() != null && pago.getValor() != null) {
            entityManager.persist(pago);

        }

    }
}
