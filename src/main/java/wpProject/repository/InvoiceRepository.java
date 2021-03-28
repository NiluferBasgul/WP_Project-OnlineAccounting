package wpProject.repository;

import wpProject.model.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice,Long> {

    Invoice findByAccountNumber (int accountNumber);
}
