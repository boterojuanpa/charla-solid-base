package com.ceiba.solid.service;

import com.ceiba.solid.entity.EmpleadoEntity;
import com.ceiba.solid.entity.PagoEntity;
import com.ceiba.solid.entity.ParametroSistemaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class PayrollService {

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public List<PagoEntity> generateSalary() {
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

                    if(getDiffYears(empleadoEntity.getFecha(), new Date()) > 3){
                        pagoEntity.setBonificacion(pagoEntity.getValor() * 0.1d);
                        pagoEntity.setValor(pagoEntity.getValor() + pagoEntity.getValor() * 0.1d);
                    }

                    c.add(Calendar.DATE, 5);
                    pagoEntity.setFechaDesembolso(c.getTime());
                    pagoEntities.add(pagoEntity);

                }
                if (empleadoEntity.getCargo().equals("SUPERVISOR")) {

                    c.add(Calendar.DATE, 3);
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(entityManager.find(ParametroSistemaEntity.class, 1l).getValue()) * 2);

                    if(getDiffYears(empleadoEntity.getFecha(), new Date()) > 2){
                        pagoEntity.setBonificacion(pagoEntity.getValor() * 0.1d);
                        pagoEntity.setValor(pagoEntity.getValor() + pagoEntity.getValor() * 0.1d);
                    }

                    pagoEntity.setFechaDesembolso(c.getTime());
                    pagoEntities.add(pagoEntity);
                }

                if (empleadoEntity.getCargo().equals("GERENTE")) {
                    PagoEntity pagoEntity = new PagoEntity();
                    pagoEntity.setIdEmpleado(empleadoEntity.getId());
                    pagoEntity.setValor(Double.parseDouble(entityManager.find(ParametroSistemaEntity.class, 3l).getValue()));
                    if(getDiffYears(empleadoEntity.getFecha(), new Date()) > 5){
                        pagoEntity.setBonificacion(pagoEntity.getValor() * 0.3d);
                        pagoEntity.setValor(pagoEntity.getValor() + pagoEntity.getValor() * 0.3d);
                    }

                    pagoEntity.setFechaDesembolso(c.getTime());
                    pagoEntities.add(pagoEntity);
                }
            }
        }

        pagoEntities.forEach(pago -> entityManager.persist(pago));

        return pagoEntities;
    }

    @Transactional
    public void payProvider(PagoEntity pay) {

        LocalDate localDate = LocalDate.now();
        localDate = localDate.withDayOfMonth(1).withMonth(localDate.getMonthValue() + 1);
        pay.setFechaDesembolso(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        if (pay.getIdProveedor() != null && pay.getValor() != null) {
            entityManager.persist(pay);

        }

    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}
