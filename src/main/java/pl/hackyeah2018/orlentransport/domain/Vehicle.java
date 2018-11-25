package pl.hackyeah2018.orlentransport.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hackyeah2018.orlentransport.json.Transport;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private BigDecimal height;
	private BigDecimal length;
	private BigDecimal weight;
	private BigDecimal width;
	private BigDecimal turningRadius;
	private BigDecimal wallToWallRadius;
	
	public static Vehicle fromTransport(Transport transport) {
		return Vehicle.builder()
			.height(transport.getHeight())
			.length(transport.getLength())
			.weight(transport.getWeight())
			.width(transport.getWidth())
			.turningRadius(transport.getTurningRadius())
			.wallToWallRadius(transport.getWallToWallRadius())
			.build();
	}
}
