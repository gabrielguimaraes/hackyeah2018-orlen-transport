package pl.hackyeah2018.orlentransport.graph;

import org.jgrapht.graph.DefaultEdge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PathEdge extends DefaultEdge {
	private static final long serialVersionUID = 7768442632249685748L;

	private PathMeasures pathMeasures;

	@Override
	public String toString() {
		return pathMeasures.getId().toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof PathMeasures) && (toString().equals(obj.toString()));
	}

}
