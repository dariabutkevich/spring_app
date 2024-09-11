package com.example.pro.repository;

import com.example.pro.model.Lot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot, Long> {
    Optional<Lot> findByLotname(String lotname);
    Lot save(Lot lot);
}
