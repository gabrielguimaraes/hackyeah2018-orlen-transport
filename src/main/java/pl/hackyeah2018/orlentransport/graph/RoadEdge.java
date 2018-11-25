package pl.hackyeah2018.orlentransport.graph;

import org.jgrapht.graph.DefaultEdge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoadEdge extends DefaultEdge {
	private static final long serialVersionUID = 7768442632249685748L;

	private RoadMeasures roadMeasures;

	@Override
	public String toString() {
		return roadMeasures.getId().toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof RoadEdge) && (toString().equals(obj.toString()));
	}

}
