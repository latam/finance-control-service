package pl.mlata.financecontrolservice.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.mlata.financecontrolservice.persistance.model.Operation;
import pl.mlata.financecontrolservice.rest.dto.OperationData;
import pl.mlata.financecontrolservice.rest.dto.mapper.OperationMapper;
import pl.mlata.financecontrolservice.rest.service.OperationService;

@RestController("/operation")
public class OperationController {
	private OperationService operationService;
	private OperationMapper operationMapper;
	
	public OperationController(OperationService operationService, OperationMapper operationMapper) {
		this.operationService = operationService;
		this.operationMapper = operationMapper;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<OperationData>> getOperations() throws Exception{
		List<OperationData> operationsData = new ArrayList<>();
		for(Operation operation : operationService.getAll()) {
			operationsData.add(operationMapper.mapFrom(operation));
		}
		
		return new ResponseEntity<>(operationsData, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<OperationData> saveOperation(@RequestBody @Valid OperationData operationData) throws Exception {
		Operation operation = operationMapper.mapTo(operationData);
		
		operation = operationService.saveOperation(operation);
		
		operationData = operationMapper.mapFrom(operation);
		return new ResponseEntity<>(operationData, HttpStatus.CREATED);
	}
	
}
