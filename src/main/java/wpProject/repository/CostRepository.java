package wpProject.repository;

import wpProject.model.Cost;
import org.springframework.data.repository.CrudRepository;

public interface CostRepository extends CrudRepository<Cost, Long> {

    Cost findByAccountNumber (int accountNumber);
}
