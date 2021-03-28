package wpProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import wpProject.model.Procurement;

public interface ProcurementRepository extends CrudRepository<Procurement, Long> {

    List<Procurement> findAll();
}
