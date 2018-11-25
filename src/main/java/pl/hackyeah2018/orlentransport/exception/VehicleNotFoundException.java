package pl.hackyeah2018.orlentransport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Vehicle Not Found")
public class VehicleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -2412362397549871905L;
}
