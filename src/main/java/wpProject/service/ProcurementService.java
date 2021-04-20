package wpProject.service;

import wpProject.model.Procurement;

import java.util.List;

public interface ProcurementService {
    Procurement createProcurement(Procurement procurement);

    List<Procurement> findAll();

    Procurement findProcurement(Long id);

    void confirmProcurement(Long id);

//    List<Object> createProcurements(String test, Long id, Long id1);
}
