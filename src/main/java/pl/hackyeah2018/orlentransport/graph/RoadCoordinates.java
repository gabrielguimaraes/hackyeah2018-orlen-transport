package pl.hackyeah2018.orlentransport.graph;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RoadCoordinates {
	final private Long id;
	private BigDecimal latitude;
	private BigDecimal longitude;
}