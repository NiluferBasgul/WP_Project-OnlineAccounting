package wpProject.service;

import java.util.List;

import wpProject.model.Procurement;

public interface ProcurementService {
	Procurement createProcurement(Procurement procurement);

    List<Procurement> findAll();

    Procurement findProcurement(Long id);

    void confirmProcurement(Long id);
}
