package pl.hackyeah2018.orlentransport.graph;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RoadMeasures {
	private Long id;
	private BigDecimal width;
	private BigDecimal maxWeight;
	private BigDecimal height;
	private BigDecimal lenght;
	private Boolean closed;
	private Boolean busStop;

}