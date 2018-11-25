package pl.hackyeah2018.orlentransport.json;


import java.math.BigDecimal;

import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {
	private BigDecimal width;
	private BigDecimal maxWeight;
	private BigDecimal height;
	private BigDecimal lenght;
	private Boolean isClosed;
	private Boolean busStop;
	
	@Embedded
	private Coords coords;
}