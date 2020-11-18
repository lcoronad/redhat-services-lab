package co.com.redhat.integration.consultaSaldo.rest;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.redhat.integration.consultaSaldo.model.ConsultaSaldoRequest;
import co.com.redhat.integration.consultaSaldo.model.ConsultaSaldoResponse;

@RestController
public class ConsultaSaldoController {
	
	@PostMapping("/consultaSaldo")
	public ConsultaSaldoResponse consultaSaldo( ConsultaSaldoRequest request ) {
		ConsultaSaldoResponse response = new ConsultaSaldoResponse();
		response.setSaldo(BigDecimal.valueOf(2300000L));
		response.setFecha(new Date());
		
	    return response;
	  }
}
