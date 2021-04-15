package wpProject.service.Impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wpProject.model.*;
import wpProject.repository.InvoiceRepository;
import wpProject.repository.CostRepository;
import wpProject.model.CostTransaction;
import wpProject.service.AccountService;
import wpProject.service.TransactionService;
import wpProject.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 11223145;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private TransactionService transactionService;

    public Invoice createInvoice() {
        Invoice invoice = new Invoice();
        invoice.setAccountBalance(new BigDecimal(0.0));
        invoice.setAccountNumber(accountGen());

        invoiceRepository.save(invoice);

        return invoiceRepository.findByAccountNumber(invoice.getAccountNumber());
    }

    public Cost createCost() {
        Cost cost = new Cost();
        cost.setAccountBalance(new BigDecimal(0.0));
        cost.setAccountNumber(accountGen());

        costRepository.save(cost);

        return costRepository.findByAccountNumber(cost.getAccountNumber());
    }
    
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Invoice")) {
            Invoice invoice = user.getInvoice();
            invoice.setAccountBalance(invoice.getAccountBalance().add(new BigDecimal(amount)));
            invoiceRepository.save(invoice);

            Date date = new Date();

            InvoiceTransaction invoiceTransaction = new InvoiceTransaction(date, "Deposit to Invoice Account", "Account", "Finished", amount, invoice.getAccountBalance(), invoice);
            transactionService.savePrimaryDepositTransaction(invoiceTransaction);
            
        } else if (accountType.equalsIgnoreCase("Costs")) {
            Cost cost = user.getCost();
            cost.setAccountBalance(cost.getAccountBalance().add(new BigDecimal(amount)));
            costRepository.save(cost);

            Date date = new Date();
            CostTransaction costTransaction = new CostTransaction(date, "Deposit to costs Account", "Account", "Finished", amount, cost.getAccountBalance(), cost);
            transactionService.saveSavingsDepositTransaction(costTransaction);
        }
    }
    
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Invoice")) {
            Invoice invoice = user.getInvoice();
            invoice.setAccountBalance(invoice.getAccountBalance().subtract(new BigDecimal(amount)));
            invoiceRepository.save(invoice);

            Date date = new Date();

            InvoiceTransaction invoiceTransaction = new InvoiceTransaction(date, "Withdraw from Invoice Account", "Account", "Finished", amount, invoice.getAccountBalance(), invoice);
            transactionService.savePrimaryWithdrawTransaction(invoiceTransaction);
        } else if (accountType.equalsIgnoreCase("Costs")) {
            Cost cost = user.getCost();
            cost.setAccountBalance(cost.getAccountBalance().subtract(new BigDecimal(amount)));
            costRepository.save(cost);

            Date date = new Date();
            CostTransaction costTransaction = new CostTransaction(date, "Withdraw from costs Account", "Account", "Finished", amount, cost.getAccountBalance(), cost);
            transactionService.saveSavingsWithdrawTransaction(costTransaction);
        }
    }
    
    private int accountGen() {
        return ++nextAccountNumber;
    }

	

}
