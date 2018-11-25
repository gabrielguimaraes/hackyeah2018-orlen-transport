package pl.hackyeah2018.orlentransport.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.hackyeah2018.orlentransport.domain.RoadPath;

@Repository
public interface RoadPathRepository extends JpaRepository<RoadPath, Long> {
	
	@Query("SELECT r FROM RoadPath r "
			+ " WHERE "
			+ " r.width < :width "
			+ " OR r.maxWeight < :weight "
			+ " OR r.height < :height "
			+ " OR r.closed = true ")
	public Set<RoadPath> findIdWhereWidthGreatherThanOrWeightGreaterThanOrHeightGreaterThanOrIsClosed(
			@Param("width") BigDecimal width,
			@Param("weight") BigDecimal weight,
			@Param("height") BigDecimal height);
}
