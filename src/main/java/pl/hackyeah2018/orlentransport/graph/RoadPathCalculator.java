package pl.hackyeah2018.orlentransport.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import pl.hackyeah2018.orlentransport.domain.Coordinate;
import pl.hackyeah2018.orlentransport.domain.RoadPath;

public class RoadPathCalculator {
	
	private List<List<Coordinate>> possiblePaths = new ArrayList<>();
	private List<Long> currentPath = new ArrayList<>();
	private Map<Long,Coordinate> coordinates;
	private Map<Long, Set<RoadPath>> roadPossibilities;
	
	public RoadPathCalculator(Map<Long, Set<RoadPath>> roadPossibilities, Map<Long,Coordinate> coordinates) {
		this.roadPossibilities = roadPossibilities;
		this.coordinates = coordinates;
	}
	
	public List<List<Coordinate>> findPossiblePaths(Long coordinateFromId, Long coordinateToId) {
		possibleRoadsMatrix(coordinateFromId, coordinateToId);
		return possiblePaths;
	}
	
	private void possibleRoadsMatrix(Long coordinateFromId, Long coordinateToId) {
		currentPath.add(coordinateFromId);
		Set<RoadPath> roadPaths = roadPossibilities.get(coordinateFromId);
		
		if (coordinateFromId.equals(coordinateToId)) {
			addCurrentPath();
			return;
		}
		
		for (RoadPath roadPath : roadPaths) {
			Long pointA = roadPath.getPointA().getId();
			Long pointB = roadPath.getPointB().getId();
			
			if (!coordinateFromId.equals(pointA)) {
				if (!currentPath.contains(pointA)) {
					possibleRoadsMatrix(pointA, coordinateToId);
				}
			} 
			if (!coordinateFromId.equals(pointB)) {
				if (!currentPath.contains(pointB)) {
					possibleRoadsMatrix(pointB, coordinateToId);
				}
			}
		}
		if (currentPath.size() == 0) {
			return;
		}
		currentPath.remove(currentPath.size()-1);
	}

	private void addCurrentPath() {
		possiblePaths.add(currentPath.stream()
			.map(vertix -> coordinates.get(vertix))
			.collect(Collectors.toList()));
		
		currentPath.remove(currentPath.size()-1);
	}
	
	
	
	

	
	
}
