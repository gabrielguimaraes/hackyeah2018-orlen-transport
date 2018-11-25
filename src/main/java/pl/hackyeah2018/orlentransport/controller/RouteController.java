package pl.hackyeah2018.orlentransport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.hackyeah2018.orlentransport.service.RouteService;

@RestController
@RequestMapping("routes")
public class RouteController {

	private RouteService routeService;

	@Autowired
	public RouteController(RouteService routeService) {
		this.routeService = routeService;
	}

	@GetMapping("/vehicles/{vehicleId}/from/{coordinateFromId}/to/{coordinateToId}")
	public List<List<Long>> getRoute(@PathVariable("vehicleId") Long vehicleId,
			@PathVariable("coordinateFromId") Long coordinateFromId,
			@PathVariable("coordinateToId") Long coordinateToId) {
		
		return routeService.calculateRoute(vehicleId, coordinateFromId, coordinateToId);
	}

}
