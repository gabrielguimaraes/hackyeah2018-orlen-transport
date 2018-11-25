package pl.hackyeah2018.orlentransport.json;

import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coords {
	
	@Embedded
	@JsonProperty("A")
	private Geo a;
	@Embedded
	@JsonProperty("B")
	private Geo b;
}
