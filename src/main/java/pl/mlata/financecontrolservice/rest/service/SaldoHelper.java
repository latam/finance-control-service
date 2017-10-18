package pl.mlata.financecontrolservice.rest.service;

import org.springframework.stereotype.Component;
import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.Operation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class SaldoHelper {
    OperationService operationService;

    public SaldoHelper(OperationService operationService) {
        this.operationService = operationService;
    }

    public BigDecimal calculateFundsForAccount(Account account) throws Exception {
        BigDecimal funds = new BigDecimal(0);

        List<Operation> operations = operationService.getAllForAccount(account.getId());
        for(Operation operation : operations) {
            funds = funds.add(operation.getFunds());
        }

        for(Account childAccount : account.getChildAccounts()) {
            BigDecimal childFunds = calculateFundsForAccount(childAccount);
            funds = funds.add(childFunds);
        }

        return funds;
    }
}
