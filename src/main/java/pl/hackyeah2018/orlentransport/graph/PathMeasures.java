package pl.hackyeah2018.orlentransport.graph;

import java.math.BigDecimal;

import org.jgrapht.graph.DefaultEdge;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PathMeasures extends DefaultEdge {

	private static final long serialVersionUID = 620260679346189877L;
	private Long id;
	private BigDecimal width;
	private BigDecimal maxWeight;
	private BigDecimal height;
	private BigDecimal lenght;
	private Boolean closed;
	private Boolean busStop;

}