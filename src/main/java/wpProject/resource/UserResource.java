package wpProject.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wpProject.model.CostTransaction;
import wpProject.model.InvoiceTransaction;
import wpProject.model.User;
import wpProject.service.TransactionService;
import wpProject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserResource {

    private final UserService userService;

    private final TransactionService transactionService;

    public UserResource(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.findUserList();
    }

    @RequestMapping(value = "/user/invoice/transaction", method = RequestMethod.GET)
    public List<InvoiceTransaction> getInvoiceTransactionList(@RequestParam("username") String username) {
        return transactionService.findInvoiceTransactionList(username);
    }

    @RequestMapping(value = "/user/cost/transaction", method = RequestMethod.GET)
    public List<CostTransaction> getCostTransactionList(@RequestParam("username") String username) {
        return transactionService.findCostTransactionList(username);
    }

    @RequestMapping("/user/{username}/enable")
    public void enableUser(@PathVariable("username") String username) {
        userService.enableUser(username);
    }

    @RequestMapping("/user/{username}/disable")
    public void diableUser(@PathVariable("username") String username) {
        userService.disableUser(username);
    }
}
