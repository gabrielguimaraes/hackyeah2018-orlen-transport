package pl.hackyeah2018.orlentransport;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.hackyeah2018.orlentransport.domain.Coordinate;
import pl.hackyeah2018.orlentransport.domain.RoadPath;
import pl.hackyeah2018.orlentransport.graph.RoadEdge;
import pl.hackyeah2018.orlentransport.graph.RoadMeasures;
import pl.hackyeah2018.orlentransport.graph.RouteGraph;
import pl.hackyeah2018.orlentransport.json.Path;
import pl.hackyeah2018.orlentransport.repository.CoordinateRepository;
import pl.hackyeah2018.orlentransport.repository.RoadPathRepository;

@SpringBootApplication
public class OrlenTransportApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrlenTransportApplication.class, args);
	}

	@Bean
	@Primary
	CommandLineRunner runner(ResourceLoader resourceLoader, RoadPathRepository roadPathRepository,
			CoordinateRepository coordinateRepository) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			Resource resource = resourceLoader.getResource("classpath:paths.json");
			List<Path> paths = mapper.readValue(resource.getInputStream(), new TypeReference<List<Path>>() {
			});
			
			
			Set<RoadPath> roadPaths = paths.stream()
				.map(path -> RoadPath.fromPath(path))
				.collect(Collectors.toSet());
			
			Set<Coordinate> coordinates = roadPaths.stream()
					.map(roadPath -> roadPath.getPointA())
					.collect(Collectors.toSet());

			coordinates.addAll(roadPaths.stream()
					.map(roadPath -> roadPath.getPointB())
					.collect(Collectors.toSet()));
			
			coordinateRepository.save(coordinates);
			
			roadPaths.stream()
				.forEach(roadPath -> coordinates.stream()
					.filter(cordinate -> cordinate.hasSameGeoPosition(roadPath.getPointA()))
					.forEach(coordinate -> roadPath.setPointA(coordinate)));
			
			roadPaths.stream()
			.forEach(roadPath -> coordinates.stream()
				.filter(cordinate -> cordinate.hasSameGeoPosition(roadPath.getPointB()))
				.forEach(coordinate -> roadPath.setPointB(coordinate)));
			
			roadPathRepository.save(roadPaths);
		};
	}
	
	@Bean
	CommandLineRunner createGraph(CoordinateRepository coordinateRepository, RoadPathRepository roadPathRepository) {
		return args -> graphInstance(coordinateRepository, roadPathRepository);
	}

	private void graphInstance(CoordinateRepository coordinateRepository, RoadPathRepository roadPathRepository) {
		List<Coordinate> coordinates = coordinateRepository.findAll();
		RouteGraph.createVertexesFrom(coordinates);
		List<RoadPath> roadPaths = roadPathRepository.findAll();
		RouteGraph.createEdgesFrom(roadPaths);
		
			
//		Graph<Long, RoadEdge> graph = new SimpleDirectedGraph<>(RoadEdge.class);
		Graph<Long, RoadEdge> graph = GraphTypeBuilder
				.undirected()
				.allowingMultipleEdges(false)
				.allowingSelfLoops(false)
				.vertexSupplier(SupplierUtil.createLongSupplier())
				.edgeClass(RoadEdge.class)
				.buildGraph();
		
		for (Coordinate coordinate : coordinates) {
			graph.addVertex(coordinate.getId());
		}
		
		for (RoadPath roadPath : roadPaths) {
//			RoadEdge roadEdge = RouteGraph.createEdgeFrom(roadPath);
			System.out.println(
					roadPath.getPointA().getId() 
						+"("+roadPath.getPointA().getLatitude()+","+roadPath.getPointA().getLongitude()+") - "
					+ roadPath.getPointB().getId()
						+"("+roadPath.getPointB().getLatitude()+","+roadPath.getPointB().getLongitude()+")");

			
			
			graph.addEdge(roadPath.getPointA().getId(), roadPath.getPointB().getId(), 
					new RoadEdge(RoadMeasures.builder().id(roadPath.getId()).build()));
			graph.addEdge(roadPath.getPointB().getId(), roadPath.getPointA().getId(),
					new RoadEdge(RoadMeasures.builder().id(roadPath.getId()).build()));
		}
		
		System.out.println("ALL EDGES: " + graph.getAllEdges(10L, 6L));
		System.out.println("ALL EDGES: " + graph.getAllEdges(4L, 10L));
		
		
		DijkstraShortestPath<Long, RoadEdge> dijkstraAlg =
	            new DijkstraShortestPath<>(graph);
	        SingleSourcePaths<Long, RoadEdge> iPaths = dijkstraAlg.getPaths(5L);
	        System.out.println(iPaths.getPath(7L) + "\n");
		
		
Graph<Long, RoadEdge> simpleGraph = new SimpleDirectedGraph<>(RoadEdge.class);
		
		simpleGraph.addVertex(1L);
		simpleGraph.addVertex(2L);
		simpleGraph.addVertex(4L);
		simpleGraph.addVertex(5L);
		simpleGraph.addVertex(6L);
		simpleGraph.addVertex(3L);
		
		simpleGraph.addEdge(1L, 2L, new RoadEdge(RoadMeasures.builder().id(1L).build()));
		simpleGraph.addEdge(2L, 1L, new RoadEdge(RoadMeasures.builder().id(1L).build()));
		
		simpleGraph.addEdge(1L, 4L, new RoadEdge(RoadMeasures.builder().id(2L).build()));
		simpleGraph.addEdge(4L, 1L, new RoadEdge(RoadMeasures.builder().id(2L).build()));
		
		simpleGraph.addEdge(1L, 5L, new RoadEdge(RoadMeasures.builder().id(3L).build()));
		simpleGraph.addEdge(5L, 1L, new RoadEdge(RoadMeasures.builder().id(3L).build()));
		
		simpleGraph.addEdge(1L, 6L, new RoadEdge(RoadMeasures.builder().id(4L).build()));
		simpleGraph.addEdge(6L, 1L, new RoadEdge(RoadMeasures.builder().id(4L).build()));
		
		simpleGraph.addEdge(4L,5L, new RoadEdge(RoadMeasures.builder().id(5L).build()));
		simpleGraph.addEdge(5L,4L, new RoadEdge(RoadMeasures.builder().id(5L).build()));
		
		simpleGraph.addEdge(2L, 3L, new RoadEdge(RoadMeasures.builder().id(6L).build()));
		simpleGraph.addEdge(3L, 2L, new RoadEdge(RoadMeasures.builder().id(6L).build()));
		
		simpleGraph.addEdge(4L, 3L, new RoadEdge(RoadMeasures.builder().id(6L).build()));
		simpleGraph.addEdge(3L, 4L, new RoadEdge(RoadMeasures.builder().id(6L).build()));
		
		AllDirectedPaths<Long, RoadEdge> all = new AllDirectedPaths<>(simpleGraph);
        List<GraphPath<Long, RoadEdge>> allPaths =
            all.getAllPaths(1L, 6L, true, simpleGraph.edgeSet().size());
		
        for (GraphPath<Long, RoadEdge> graphPath : allPaths) {
			System.out.println("Length: " + graphPath.getLength() + " - Vertex : " + graphPath.getVertexList());
//			graphPath.getVertexList().
			
		}
	}
}
