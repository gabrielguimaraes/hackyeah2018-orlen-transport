package pl.hackyeah2018.orlentransport.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoadVertex {
	final private RoadCoordinates roadCoordinates;

	@Override
	public String toString() {
		return roadCoordinates.getId().toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof RoadVertex) && (toString().equals(obj.toString()));
	}

}
