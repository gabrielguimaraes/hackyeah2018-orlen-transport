package pl.hackyeah2018.orlentransport.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.hackyeah2018.orlentransport.domain.Coordinate;
import pl.hackyeah2018.orlentransport.domain.RoadPath;
import pl.hackyeah2018.orlentransport.domain.Vehicle;
import pl.hackyeah2018.orlentransport.graph.RoadPathCalculator;
import pl.hackyeah2018.orlentransport.graph.RoadVertex;
import pl.hackyeah2018.orlentransport.graph.RouteGraph;
import pl.hackyeah2018.orlentransport.repository.CoordinateRepository;
import pl.hackyeah2018.orlentransport.repository.RoadPathRepository;
import pl.hackyeah2018.orlentransport.repository.VehicleRepository;

@Service
public class RouteService {
	
	private VehicleRepository vehicleRepository;
	private CoordinateRepository coordinateRepository;
	private RoadPathRepository roadPathRepository;
	
	@Autowired
	public RouteService(VehicleRepository vehicleRepository, CoordinateRepository coordinateRepository,
			RoadPathRepository roadPathRepository) {
		this.vehicleRepository = vehicleRepository;
		this.coordinateRepository = coordinateRepository;
		this.roadPathRepository = roadPathRepository;
	}


	public List<List<Coordinate>> calculateRoute(Long vehicleId, Long coordinateFromId, Long coordinateToId) {
		Set<RoadPath> allowedPaths = listOfAllowedPaths(vehicleRepository.findOne(vehicleId));
		Map<Long, Set<RoadPath>> roadPossibilities = getRoadPossibilities(allowedPaths);
		Map<Long,Coordinate> coordinatesById = extractCoordinatesById();
		
//		removePaths(allowedPaths);
		
		RoadPathCalculator roadPathCalculator = new RoadPathCalculator(roadPossibilities, coordinatesById);
		return roadPathCalculator.findPossiblePaths(coordinateFromId, coordinateToId);
	}


	private Map<Long, Coordinate> extractCoordinatesById() {
		return coordinateRepository.findAll()
			.stream()
			.collect(Collectors.toMap(Coordinate::getId, coordinate -> coordinate));
	}


	private Map<Long, Set<RoadPath>> getRoadPossibilities(Set<RoadPath> allowedPaths) {
		Map<Long, Set<RoadPath>> roadPossibilities = new HashMap<>();

		for (RoadPath allowedPath : allowedPaths) {
			Set<RoadPath> roadPathA = roadPossibilities.get(allowedPath.getPointA().getId());
			if (roadPathA == null) {
				roadPathA = new HashSet<>();
			}
			roadPathA.add(allowedPath);
			roadPossibilities.put(allowedPath.getPointA().getId(), roadPathA);
			
			Set<RoadPath> roadPathB = roadPossibilities.get(allowedPath.getPointB().getId());
			if (roadPathB == null) {
				roadPathB = new HashSet<>();
			}
			roadPathB.add(allowedPath);
			roadPossibilities.put(allowedPath.getPointB().getId(), roadPathB);
		}
		return roadPossibilities;
	}


	private Set<RoadPath> listOfAllowedPaths(Vehicle vehicle) {
		return roadPathRepository.findIdWhereWidthGreatherThanAndWeightGreaterThanAndHeightGreaterThanAndIsNotClosed(
				vehicle.getWidth(), 
				vehicle.getWeight(), 
				vehicle.getHeight());
	}

	private void removePaths(Set<RoadPath> forbiddenPaths) {
		RouteGraph.removePaths(forbiddenPaths);
		
	}

	private List<RoadVertex> possibleRoads(Long coordinateFromId, Long coordinateToId) {
		Coordinate coordinateFrom = coordinateRepository.findOne(coordinateFromId);
		Coordinate coordinateTo = coordinateRepository.findOne(coordinateToId);
		return RouteGraph.possibleRoads(coordinateFrom, coordinateTo);
	}
}
