package wpProject.repository;

import org.springframework.data.repository.CrudRepository;
import wpProject.model.Procurement;

import java.util.List;

public interface ProcurementRepository extends CrudRepository<Procurement, Long> {

    List<Procurement> findAll();


}
