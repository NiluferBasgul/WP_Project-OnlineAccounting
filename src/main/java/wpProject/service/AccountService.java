package wpProject.service;

import wpProject.model.Cost;
import wpProject.model.Invoice;

import java.security.Principal;

public interface AccountService {
    Invoice createInvoice();

    Cost createCost();

    void deposit(String accountType, double amount, Principal principal);

    void withdraw(String accountType, double amount, Principal principal);


}
