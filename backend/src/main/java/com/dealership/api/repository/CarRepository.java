package com.dealership.api.repository;

import com.dealership.api.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link Car}.
 * JpaSpecificationExecutor enables dynamic filter queries.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
