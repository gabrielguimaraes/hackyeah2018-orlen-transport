package pl.hackyeah2018.orlentransport.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.hackyeah2018.orlentransport.domain.Coordinate;
import pl.hackyeah2018.orlentransport.domain.RoadPath;
import pl.hackyeah2018.orlentransport.domain.Vehicle;
import pl.hackyeah2018.orlentransport.graph.PathEdge;
import pl.hackyeah2018.orlentransport.graph.PathGraph;
import pl.hackyeah2018.orlentransport.repository.RoadPathRepository;
import pl.hackyeah2018.orlentransport.repository.VehicleRepository;

@Service
public class RouteService {
	
	private VehicleRepository vehicleRepository;
	private RoadPathRepository roadPathRepository;
	
	@Autowired
	public RouteService(VehicleRepository vehicleRepository, RoadPathRepository roadPathRepository) {
		this.vehicleRepository = vehicleRepository;
		this.roadPathRepository = roadPathRepository;
	}
	

	public List<List<Long>> calculateRoute(Long vehicleId, Long coordinateFromId, Long coordinateToId) {
		Set<RoadPath> forbiddenPaths = listOfForbiddenPaths(vehicleRepository.findOne(vehicleId));
		removePaths(forbiddenPaths);
		return possibleRoads(coordinateFromId, coordinateToId);
	}


	private Set<RoadPath> listOfForbiddenPaths(Vehicle vehicle) {
		return roadPathRepository.findIdWhereWidthGreatherThanOrWeightGreaterThanOrHeightGreaterThanOrIsClosed(
				vehicle.getWidth(), 
				vehicle.getWeight(), 
				vehicle.getHeight());
	}

	private void removePaths(Set<RoadPath> forbiddenPaths) {
		PathGraph.removePaths(forbiddenPaths);
		
	}

	private List<List<Long>> possibleRoads(Long coordinateFromId, Long coordinateToId) {
		List<GraphPath<Long, PathEdge>> possibleRoads = PathGraph.possibleRoads(coordinateFromId, coordinateToId);
		return possibleRoads.stream()
			.map(graphPath -> graphPath.getVertexList())
			.collect(Collectors.toList());
	}
}
