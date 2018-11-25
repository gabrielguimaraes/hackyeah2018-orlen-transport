package pl.hackyeah2018.orlentransport.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hackyeah2018.orlentransport.json.Path;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RoadPath {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal width;
	private BigDecimal maxWeight;
	private BigDecimal height;
	private BigDecimal lenght;
	private Boolean closed;
	private Boolean busStop;

	@ManyToOne
	private Coordinate pointA;
	@ManyToOne
	private Coordinate pointB;

	public static RoadPath fromPath(Path path) {
		return RoadPath.builder()
				.width(path.getWidth())
				.maxWeight(path.getMaxWeight())
				.height(path.getHeight())
				.closed(path.getIsClosed())
				.busStop(path.getBusStop())
				.pointA(new Coordinate(null, path.getCoords().getA().getLng(), path.getCoords().getA().getLat()))
				.pointB(new Coordinate(null, path.getCoords().getB().getLng(), path.getCoords().getB().getLat()))
				.build();
	}
}
