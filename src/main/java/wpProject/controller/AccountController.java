package wpProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wpProject.model.*;
import wpProject.service.AccountService;
import wpProject.service.TransactionService;
import wpProject.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/invoice")
	public String invoice(Model model, Principal principal) {
		List<InvoiceTransaction> invoiceTransactionList = transactionService.findInvoiceTransactionList(principal.getName());
		
		User user = userService.findByUsername(principal.getName());
        Invoice invoice = user.getInvoice();
        model.addAttribute("invoice", invoice);
        model.addAttribute("invoiceTransactionList", invoiceTransactionList);

        return "invoice";
    }

    @RequestMapping("/cost")
    public String cost(Model model, Principal principal) {
        List<CostTransaction> costTransactionList = transactionService.findCostTransactionList(principal.getName());
        User user = userService.findByUsername(principal.getName());
        Cost cost = user.getCost();

        model.addAttribute("cost", cost);
        model.addAttribute("costTransactionList", costTransactionList);

        return "cost";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.deposit(accountType, Double.parseDouble(amount), principal);
        return "redirect:/home";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/home";
    }
}
