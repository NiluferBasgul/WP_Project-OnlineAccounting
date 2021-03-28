package wpProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import wpProject.model.CostTransaction;

public interface CostTransactionRepository extends CrudRepository<CostTransaction, Long> {

    List<CostTransaction> findAll();
}

