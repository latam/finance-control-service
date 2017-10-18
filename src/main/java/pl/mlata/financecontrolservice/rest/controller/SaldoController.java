package pl.mlata.financecontrolservice.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.rest.dto.SaldoSummary;
import pl.mlata.financecontrolservice.rest.service.SaldoService;

import java.security.Principal;

@Controller
@RequestMapping("/saldo-summary")
public class SaldoController {
	private SaldoService saldoService;

	public SaldoController(SaldoService saldoService) {
		this.saldoService = saldoService;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<SaldoSummary> getTotalSummary(@AuthenticationPrincipal User activeUser) throws Exception {
		SaldoSummary summary = saldoService.calculateSaldoSummary(activeUser);
		return new ResponseEntity<>(summary, HttpStatus.OK);
	}

}
