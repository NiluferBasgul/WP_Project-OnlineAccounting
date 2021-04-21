package wpProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wpProject.model.Cost;
import wpProject.model.Invoice;
import wpProject.model.User;
import wpProject.model.security.UserRole;
import wpProject.repository.RoleRepository;
import wpProject.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
	
	private final UserService userService;
	
	private RoleRepository roleRepository;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public HomeController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
    public String index() {
        return "index";
    }

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user,  Model model) {
	    try {

            if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

                if (userService.checkEmailExists(user.getEmail())) {
                    model.addAttribute("emailExists", true);
                }

                if (userService.checkUsernameExists(user.getUsername())) {
                    model.addAttribute("usernameExists", true);
                }

                return "signup";
            } else {
                Set<UserRole> userRoles = new HashSet<>();
                userRoles.add(new UserRole(user, roleRepository.findByName("ROLE_USER")));

                userService.createUser(user, userRoles);

                return "redirect:/";
            }
        }
	    catch (Exception ex) {
	        return "index";
        }
    }
	
	@RequestMapping("/home")
	public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Invoice invoice = user.getInvoice();
        Cost cost = user.getCost();

        model.addAttribute("invoice", invoice);
        model.addAttribute("cost", cost);

        return "home";
    }
}
