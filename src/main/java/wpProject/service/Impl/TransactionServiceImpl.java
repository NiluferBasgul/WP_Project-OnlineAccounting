package wpProject.service.Impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wpProject.model.*;
import wpProject.repository.InvoiceRepository;
import wpProject.repository.InvoiceTransactionRepository;
import wpProject.repository.CompanyRepository;
import wpProject.repository.CostRepository;
import wpProject.repository.CostTransactionRepository;
import wpProject.model.Company;
import wpProject.service.TransactionService;
import wpProject.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvoiceTransactionRepository invoiceTransactionRepository;
	
	@Autowired
	private CostTransactionRepository costTransactionRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private CostRepository costRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	

	public List<InvoiceTransaction> findInvoiceTransactionList(String username){
        User user = userService.findByUsername(username);
        List<InvoiceTransaction> invoiceTransactionList = user.getInvoice().getInvoiceTransactionList();

        return invoiceTransactionList;
    }

    public List<CostTransaction> findCostTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<CostTransaction> costTransactionList = user.getCost().getCostTransactionList();

        return costTransactionList;
    }

    public void savePrimaryDepositTransaction(InvoiceTransaction invoiceTransaction) {
        invoiceTransactionRepository.save(invoiceTransaction);
    }

    public void saveSavingsDepositTransaction(CostTransaction costTransaction) {
        costTransactionRepository.save(costTransaction);
    }
    
    public void savePrimaryWithdrawTransaction(InvoiceTransaction invoiceTransaction) {
        invoiceTransactionRepository.save(invoiceTransaction);
    }

    public void saveSavingsWithdrawTransaction(CostTransaction costTransaction) {
        costTransactionRepository.save(costTransaction);
    }
    
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, Invoice invoice, Cost cost) throws Exception {
        if (transferFrom.equalsIgnoreCase("Invoice") && transferTo.equalsIgnoreCase("Cost")) {
            invoice.setAccountBalance(invoice.getAccountBalance().subtract(new BigDecimal(amount)));
            cost.setAccountBalance(cost.getAccountBalance().add(new BigDecimal(amount)));
            invoiceRepository.save(invoice);
            costRepository.save(cost);

            Date date = new Date();

            InvoiceTransaction invoiceTransaction = new InvoiceTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), invoice.getAccountBalance(), invoice);
            invoiceTransactionRepository.save(invoiceTransaction);
        } else if (transferFrom.equalsIgnoreCase("Cost") && transferTo.equalsIgnoreCase("Invoice")) {
            invoice.setAccountBalance(invoice.getAccountBalance().add(new BigDecimal(amount)));
            cost.setAccountBalance(cost.getAccountBalance().subtract(new BigDecimal(amount)));
            invoiceRepository.save(invoice);
            costRepository.save(cost);

            Date date = new Date();

            CostTransaction costTransaction = new CostTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), cost.getAccountBalance(), cost);
            costTransactionRepository.save(costTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }
    
    public List<Company> findCompanyList(Principal principal) {
        String username = principal.getName();
        List<Company> companyList = companyRepository.findAll().stream() 			//convert list to stream
                .filter(company -> username.equals(company.getUser().getUsername()))	//filters the line, equals to username
                .collect(Collectors.toList());

        return companyList;
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company findCompanyByName(String companyName) {
        return companyRepository.findByName(companyName);
    }

    public void deleteCompanyByName(String companyName) {
        companyRepository.deleteByName(companyName);
    }
    
    public void toSomeoneElseTransfer(Company company, String accountType, String amount, Invoice invoice, Cost cost) {
        if (accountType.equalsIgnoreCase("Invoice")) {
            invoice.setAccountBalance(invoice.getAccountBalance().subtract(new BigDecimal(amount)));
            invoiceRepository.save(invoice);

            Date date = new Date();

            InvoiceTransaction invoiceTransaction = new InvoiceTransaction(date, "Transfer to company "+ company.getName(), "Transfer", "Finished", Double.parseDouble(amount), invoice.getAccountBalance(), invoice);
            invoiceTransactionRepository.save(invoiceTransaction);
        } else if (accountType.equalsIgnoreCase("Costs")) {
            cost.setAccountBalance(cost.getAccountBalance().subtract(new BigDecimal(amount)));
            costRepository.save(cost);

            Date date = new Date();

            CostTransaction costTransaction = new CostTransaction(date, "Transfer to company "+ company.getName(), "Transfer", "Finished", Double.parseDouble(amount), cost.getAccountBalance(), cost);
            costTransactionRepository.save(costTransaction);
        }
    }
}
