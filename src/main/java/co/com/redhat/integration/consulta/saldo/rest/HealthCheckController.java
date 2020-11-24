package co.com.redhat.integration.consulta.saldo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	
	@GetMapping(path = "/health-check", produces = "text/plain")
	public String health( ) {
		return "Status Service: Ok";
	  }
}
