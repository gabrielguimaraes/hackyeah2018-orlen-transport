package pl.hackyeah2018.orlentransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.hackyeah2018.orlentransport.domain.Coordinate;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

}
