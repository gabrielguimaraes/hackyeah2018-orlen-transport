package pl.hackyeah2018.orlentransport.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coordinate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(precision = 10, scale = 8, columnDefinition="DECIMAL(10,8)") 
	private BigDecimal longitude;
	@Column(precision = 10, scale = 8, columnDefinition="DECIMAL(10,8)")
	private BigDecimal latitude;
	
	public Boolean hasSameGeoPosition(Coordinate coordinate) {
		return coordinate.getLatitude().equals(latitude) && coordinate.getLongitude().equals(longitude);
	}
}
