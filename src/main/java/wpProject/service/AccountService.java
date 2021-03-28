package wpProject.service;

import java.security.Principal;

import wpProject.model.Cost;
import wpProject.model.Invoice;

public interface AccountService {
	Invoice createInvoice();
    Cost createCost();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);
    
    
}
