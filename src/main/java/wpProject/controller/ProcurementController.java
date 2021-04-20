package wpProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wpProject.model.Procurement;
import wpProject.model.User;
import wpProject.service.ProcurementService;
import wpProject.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createProcurement(Model model, Principal principal) {
        Procurement procurement = new Procurement();
        User users = userService.findByUsername(principal.getName());
        List<Procurement> procurement1 = users.getProcurementList();
        model.addAttribute("procurement", procurement);//TODO bak silersen bunu nolur
        model.addAttribute("procurement1", procurement1);
        model.addAttribute("date", "");

        return "procurement";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProcurementPost(@ModelAttribute("procurement") Procurement procurement, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date d1 = format1.parse(date);
        procurement.setDate(d1);

        User user = userService.findByUsername(principal.getName());
        procurement.setUser(user);

        procurementService.createProcurement(procurement);

        return "redirect:/home";
    }


}
