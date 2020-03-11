package com.ceiba.solid.controller;

import com.ceiba.solid.entity.PagoEntity;
import com.ceiba.solid.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payroll")
public class PayrollController {

    @Autowired
    PayrollService salaryService;

    @GetMapping("apply-salary")
    public List<PagoEntity> apply() {
        return salaryService.generateSalary();
    }

    @PostMapping("pay-provider")
    public void payProvider(@RequestBody PagoEntity pagoEntity) {
        salaryService.payProvider(pagoEntity);
    }

}
