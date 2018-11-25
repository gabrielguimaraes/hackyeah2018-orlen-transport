package pl.hackyeah2018.orlentransport.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

import pl.hackyeah2018.orlentransport.domain.Coordinate;
import pl.hackyeah2018.orlentransport.domain.RoadPath;

public class RouteGraph {
	public static Graph<RoadVertex, RoadEdge> ROUTE_GRAPH = new SimpleGraph<>(RoadEdge.class);
	public static ThreadLocal<Graph<RoadVertex, RoadEdge>> ROUTE_LOCAL_GRAPH = new ThreadLocal<>();
	
	
	public static void createVertexesFrom(List<Coordinate> coordinates) {
		for (Coordinate coordinate : coordinates) {
			RoadVertex roadVertex = createVertexFrom(coordinate);
			ROUTE_GRAPH.addVertex(roadVertex);
		}
	}
	
	public static RoadVertex createVertexFrom(Coordinate coordinate) {
		RoadCoordinates roadCoordinates = new RoadCoordinates(
				coordinate.getId(),
				coordinate.getLatitude(),
				coordinate.getLongitude());
		
		return new RoadVertex(roadCoordinates);
	}

	public static void createEdgesFrom(List<RoadPath> roadPaths) {
		for (RoadPath roadPath : roadPaths) {
			RoadEdge roadEdge = createEdgeFrom(roadPath);
			RoadVertex vertexA = createVertexFrom(roadPath.getPointA());
			RoadVertex vertexB = createVertexFrom(roadPath.getPointB());
			ROUTE_GRAPH.addEdge(vertexA, vertexB, roadEdge);
//			ROUTE_GRAPH.addEdge(vertexB, vertexA, roadEdge);
		}
	}

	public static RoadEdge createEdgeFrom(RoadPath roadPath) {
		RoadMeasures roadMeasures = new RoadMeasures(roadPath.getId(), 
				roadPath.getWidth(), 
				roadPath.getMaxWeight(), 
				roadPath.getHeight(), 
				roadPath.getLenght(), 
				roadPath.getClosed(), 
				roadPath.getBusStop());
		return new RoadEdge(roadMeasures);
	}
	
	public static void createLocalGraph() {
		ROUTE_LOCAL_GRAPH.set(ROUTE_GRAPH);
	}

	public static void removePaths(Set<RoadPath> forbiddenPaths) {
		createLocalGraph();
		Graph<RoadVertex, RoadEdge> graph = ROUTE_LOCAL_GRAPH.get();
		forbiddenPaths.stream()
			.forEach(forbiddenPath -> {
				
				RoadVertex vertexA = createVertexFrom(forbiddenPath.getPointA());
				RoadVertex vertexB = createVertexFrom(forbiddenPath.getPointB());
				graph.removeAllEdges(vertexA, vertexB);
				graph.removeAllEdges(vertexB, vertexA);
			});
	}

	public static List<RoadVertex> possibleRoads(Coordinate coordinateFrom, Coordinate coordinateTo) {
		Graph<RoadVertex, RoadEdge> graph = ROUTE_LOCAL_GRAPH.get();
		RoadVertex vertexFrom = createVertexFrom(coordinateFrom);
		RoadVertex vertexTo = createVertexFrom(coordinateTo);
		DijkstraShortestPath<RoadVertex, RoadEdge> dijkstraAlg =
	            new DijkstraShortestPath<>(graph);
	        SingleSourcePaths<RoadVertex, RoadEdge> iPaths = dijkstraAlg.getPaths(vertexFrom);
        return iPaths.getPath(vertexTo).getVertexList();
	}
}
