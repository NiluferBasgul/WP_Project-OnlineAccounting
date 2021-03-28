package wpProject.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wpProject.model.Procurement;
import wpProject.service.ProcurementService;

@RestController
@RequestMapping("/api/procurement")
@PreAuthorize("hasRole('ADMIN')")
public class ProcuremenetResource {

    @Autowired
    private ProcurementService procurementService;

    @RequestMapping("/all")
    public List<Procurement> findProcurementList() {
        List<Procurement> procurementList = procurementService.findAll();

        return procurementList;
    }

    @RequestMapping("/{id}/confirm")
    public void confirmProcurement(@PathVariable("id") Long id) {
        procurementService.confirmProcurement(id);
    }
}
