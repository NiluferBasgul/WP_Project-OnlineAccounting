package wpProject.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wpProject.model.Procurement;
import wpProject.service.ProcurementService;

import java.util.List;

@RestController
@RequestMapping("/api/procurement")
@PreAuthorize("hasRole('ADMIN')")
public class ProcurementResource {

    private final ProcurementService procurementService;

    public ProcurementResource(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

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
