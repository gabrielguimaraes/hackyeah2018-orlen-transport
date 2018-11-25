package pl.hackyeah2018.orlentransport.json;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {
	private BigDecimal lng;
	private BigDecimal lat;
}
