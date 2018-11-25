package pl.hackyeah2018.orlentransport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.hackyeah2018.orlentransport.domain.RoadPath;
import pl.hackyeah2018.orlentransport.json.Path;
import pl.hackyeah2018.orlentransport.repository.RoadPathRepository;

@RestController
@RequestMapping("/roads")
public class RoadPathController {
	
	private RoadPathRepository roadPathRepository;

	@Autowired
	public RoadPathController(RoadPathRepository roadPathRepository) {
		this.roadPathRepository = roadPathRepository;
	}

	@GetMapping
	public List<RoadPath> getAllRoadPath() {
		return roadPathRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public RoadPath getRoadPath(@PathVariable Long id) {
		return roadPathRepository.findOne(id);
	}
	
	@PatchMapping("/{id}")
	public RoadPath updateRoadPath(@PathVariable Long id, @RequestBody Path path) {
		RoadPath roadPath = RoadPath.fromPath(path);
		roadPath.setId(id);
		return roadPathRepository.save(roadPath);
	}

}
