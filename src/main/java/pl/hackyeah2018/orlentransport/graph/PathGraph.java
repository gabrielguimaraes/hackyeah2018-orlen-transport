package pl.hackyeah2018.orlentransport.graph;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.SimpleDirectedGraph;

import pl.hackyeah2018.orlentransport.domain.Coordinate;
import pl.hackyeah2018.orlentransport.domain.RoadPath;

public class PathGraph {
	public static final Graph<Long, PathEdge> SIMPLE_GRAPH = new SimpleDirectedGraph<>(PathEdge.class);
	public static final ThreadLocal<Graph<Long, PathEdge>> LOCAL_SIMPLE_GRAPH = new ThreadLocal<>();
	
	
	public static void createVertexesFrom(List<Coordinate> coordinates) {
		for (Coordinate coordinate : coordinates) {
			SIMPLE_GRAPH.addVertex(coordinate.getId());
		}
	}
	
	public static void createEdgesFrom(List<RoadPath> roadPaths) {
		for (RoadPath roadPath : roadPaths) {
			PathEdge pathEdge = createEdgeFrom(roadPath);
			SIMPLE_GRAPH.addEdge(roadPath.getPointA().getId(), roadPath.getPointB().getId(), pathEdge);
			SIMPLE_GRAPH.addEdge(roadPath.getPointB().getId(), roadPath.getPointA().getId(), pathEdge);
		}
	}

	public static PathEdge createEdgeFrom(RoadPath roadPath) {
		PathMeasures pathMeasures = new PathMeasures(roadPath.getId(), 
				roadPath.getWidth(), 
				roadPath.getMaxWeight(), 
				roadPath.getHeight(), 
				roadPath.getLenght(), 
				roadPath.getClosed(), 
				roadPath.getBusStop());
		return new PathEdge(pathMeasures);
	}
	
	public static void createLocalGraph() {
		LOCAL_SIMPLE_GRAPH.set(SIMPLE_GRAPH);
	}

	public static void removePaths(Set<RoadPath> forbiddenPaths) {
		createLocalGraph();
		Graph<Long, PathEdge> graph = LOCAL_SIMPLE_GRAPH.get();
		forbiddenPaths.stream()
			.forEach(forbiddenPath -> {
				graph.removeAllEdges(forbiddenPath.getPointA().getId(), forbiddenPath.getPointB().getId());
				graph.removeAllEdges(forbiddenPath.getPointB().getId(), forbiddenPath.getPointA().getId());
			});
	}

	public static List<GraphPath<Long, PathEdge>> possibleRoads(Long coordinateFromId, Long coordinateToId) {
		Graph<Long, PathEdge> graph = LOCAL_SIMPLE_GRAPH.get();
		AllDirectedPaths<Long, PathEdge> allDirectedPaths = new AllDirectedPaths<>(graph);
        List<GraphPath<Long, PathEdge>> allDirectedPathsGroup =
            allDirectedPaths.getAllPaths(coordinateFromId, coordinateToId, true, graph.edgeSet().size());
        
        return allDirectedPathsGroup;
	}
}
