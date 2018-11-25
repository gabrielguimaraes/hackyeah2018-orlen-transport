package pl.hackyeah2018.orlentransport;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import pl.hackyeah2018.orlentransport.graph.PathGraph;
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
			
			roadPaths.forEach(System.out::println);
			
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
		return args -> {
			PathGraph.createVertexesFrom(coordinateRepository.findAll());
			PathGraph.createEdgesFrom(roadPathRepository.findAll());
		};
	}
}
