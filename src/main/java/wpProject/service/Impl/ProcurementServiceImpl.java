package wpProject.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wpProject.model.Procurement;
import wpProject.repository.ProcurementRepository;
import wpProject.service.ProcurementService;

@Service
public class ProcurementServiceImpl implements ProcurementService {

    @Autowired
    private ProcurementRepository procurementRepository;

    public Procurement createProcurement(Procurement procurement) {
       return procurementRepository.save(procurement);
    }

    public List<Procurement> findAll() {
        return procurementRepository.findAll();
    }

    public Procurement findProcurement(Long id) {
        return null;
    }

    public void confirmProcurement(Long id) {
        Procurement procurement = findProcurement(id);
        procurement.setConfirmed(true);
        procurementRepository.save(procurement);
    }
}
