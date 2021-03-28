package wpProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import wpProject.model.InvoiceTransaction;

public interface InvoiceTransactionRepository extends CrudRepository<InvoiceTransaction, Long> {

    List<InvoiceTransaction> findAll();
}
