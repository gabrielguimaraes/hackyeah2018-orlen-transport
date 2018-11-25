package pl.hackyeah2018.orlentransport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.hackyeah2018.orlentransport.domain.Vehicle;
import pl.hackyeah2018.orlentransport.exception.VehicleNotFoundException;
import pl.hackyeah2018.orlentransport.json.Transport;
import pl.hackyeah2018.orlentransport.repository.VehicleRepository;

@RestController
@RequestMapping("vehicles")
public class VehicleController {
	
	private VehicleRepository vehicleRepository;
	
	@Autowired
	public VehicleController(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}
	
	
	@PostMapping
	public Long createNewVehicle(@RequestBody Transport transport) {
		Vehicle vehicle = Vehicle.fromTransport(transport);
		vehicleRepository.save(vehicle);
		
		return vehicle.getId();
	}
	
	@GetMapping("/{id}")
	public Vehicle createNewVehicle(@PathVariable Long id) {
		Vehicle vehicle = vehicleRepository.findOne(id);
		
		if (vehicle == null) {
			throw new VehicleNotFoundException();
		}
		
		return vehicle;
	}


}
