package pl.hackyeah2018.orlentransport.json;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transport {
	private BigDecimal height;
	private BigDecimal length;
	private BigDecimal weight;
	private BigDecimal width;
	private BigDecimal turningRadius;
	private BigDecimal wallToWallRadius;

}
