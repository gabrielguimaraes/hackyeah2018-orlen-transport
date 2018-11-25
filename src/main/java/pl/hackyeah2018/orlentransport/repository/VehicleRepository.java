package pl.hackyeah2018.orlentransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.hackyeah2018.orlentransport.domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
