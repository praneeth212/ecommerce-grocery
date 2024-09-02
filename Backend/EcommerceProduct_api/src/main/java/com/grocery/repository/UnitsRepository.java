package com.grocery.repository;

import com.grocery.model.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UnitsRepository extends JpaRepository<Units, Long> {
	
	@Query("Select c from Units c where c.unitName=:unitName")
    Optional<Units> findByUnitName(String unitName);
}
