package co.com.redhat.integration.consulta.saldo.rest;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.redhat.integration.consulta.saldo.model.ConsultaSaldoRequest;
import co.com.redhat.integration.consulta.saldo.model.ConsultaSaldoResponse;

@RestController
public class ConsultaSaldoController {
	
	@PostMapping(path = "/consultaSaldo", produces = "application/json")
	public ConsultaSaldoResponse consultaSaldo( ConsultaSaldoRequest request ) {
		ConsultaSaldoResponse response = new ConsultaSaldoResponse();
		response.setSaldo(BigDecimal.valueOf(2300000L));
		response.setFecha(new Date());
		
	    return response;
	  }
}
