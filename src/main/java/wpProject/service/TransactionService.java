package wpProject.service;

import java.security.Principal;
import java.util.List;

import wpProject.model.*;
import wpProject.model.Company;

public interface TransactionService {
    List<InvoiceTransaction> findInvoiceTransactionList(String username);

    List<CostTransaction> findCostTransactionList(String username);

    void savePrimaryDepositTransaction(InvoiceTransaction invoiceTransaction);

    void saveSavingsDepositTransaction(CostTransaction costTransaction);

    void savePrimaryWithdrawTransaction(InvoiceTransaction invoiceTransaction);

    void saveSavingsWithdrawTransaction(CostTransaction costTransaction);

    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, Invoice invoice, Cost cost) throws Exception;

    List<Company> findCompanyList(Principal principal);

    Company saveCompany(Company company);

    Company findCompanyByName(String companyName);

    void deleteCompanyByName(String companyName);

    void toSomeoneElseTransfer(Company company, String accountType, String amount, Invoice invoice, Cost cost);

    void create(String c2, String c21);
}
