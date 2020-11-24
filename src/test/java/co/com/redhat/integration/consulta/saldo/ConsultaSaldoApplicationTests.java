package co.com.redhat.integration.consulta.saldo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.redhat.integration.consulta.saldo.model.ConsultaSaldoRequest;
import co.com.redhat.integration.consulta.saldo.model.ConsultaSaldoResponse;

@RunWith(SpringRunner.class)
@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = { "server.port=8080" })
class ConsultaSaldoApplicationTests {

	@Value("${server.port}")
    private String serverPort;

    @Autowired
    private TestRestTemplate restTemplate;
    
    private Logger log = LoggerFactory.getLogger(ConsultaSaldoApplicationTests.class);
        
    @Test       
    public void testConsultarRangosGuias() throws Exception {
        
    	ConsultaSaldoRequest request = new ConsultaSaldoRequest();
    	request.setNumeroCuenta("1254515151");
    	
    	ResponseEntity<ConsultaSaldoResponse> response = restTemplate.postForEntity("http://localhost:8080/consultaSaldo", request, ConsultaSaldoResponse.class);
    	
        log.info("testConsultarRangosGuias response :", response );
        
        assertThat( response.getBody().getSaldo().equals( BigDecimal.valueOf(2300000L) ) ).isTrue();
    }
}
