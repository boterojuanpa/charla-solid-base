package com.ceiba.solid.repository;

import com.ceiba.solid.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
}
