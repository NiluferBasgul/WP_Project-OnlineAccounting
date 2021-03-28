package wpProject.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import wpProject.model.Company;
import wpProject.model.Cost;
import wpProject.model.Invoice;
import wpProject.model.User;
import wpProject.service.TransactionService;
import wpProject.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
    public String betweenAccounts(Model model) {
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
    public String betweenAccountsPost(
            @ModelAttribute("transferFrom") String transferFrom,
            @ModelAttribute("transferTo") String transferTo,
            @ModelAttribute("amount") String amount,
            Principal principal
    ) throws Exception {
        User user = userService.findByUsername(principal.getName());
        Invoice invoice = user.getInvoice();
        Cost cost = user.getCost();
        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, invoice, cost);

        return "redirect:/home";
    }
    
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public String company(Model model, Principal principal) {
        List<Company> companyList = transactionService.findCompanyList(principal);

        Company company = new Company();

        model.addAttribute("companyList", companyList);
        model.addAttribute("company", company);

        return "company";
    }

    @RequestMapping(value = "/company/save", method = RequestMethod.POST)
    public String companyPost(@ModelAttribute("company") Company company, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        company.setUser(user);
        transactionService.saveCompany(company);

        return "redirect:/transfer/company";
    }

    @RequestMapping(value = "/company/edit", method = RequestMethod.GET)
    public String companyEdit(@RequestParam(value = "companyName") String companyName, Model model, Principal principal){

        Company company = transactionService.findCompanyByName(companyName);
        List<Company> companyList = transactionService.findCompanyList(principal);

        model.addAttribute("companyList", companyList);
        model.addAttribute("company", company);

        return "company";
    }

    @RequestMapping(value = "/company/delete", method = RequestMethod.GET)
    @Transactional
    public String companyDelete(@RequestParam(value = "companyName") String companyName, Model model, Principal principal){

        transactionService.deleteCompanyByName(companyName);

        List<Company> companyList = transactionService.findCompanyList(principal);

        Company company = new Company();
        model.addAttribute("company", company);
        model.addAttribute("companyList", companyList);


        return "company";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.GET)
    public String toSomeoneElse(Model model, Principal principal) {
        List<Company> companyList = transactionService.findCompanyList(principal);

        model.addAttribute("companyList", companyList);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.POST)
    public String toSomeoneElsePost(@ModelAttribute("companyName") String companyName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Company company = transactionService.findCompanyByName(companyName);
        transactionService.toSomeoneElseTransfer(company, accountType, amount, user.getInvoice(), user.getCost());

        return "redirect:/home";
    }
}
