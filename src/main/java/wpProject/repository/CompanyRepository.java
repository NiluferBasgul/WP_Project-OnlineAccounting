package wpProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import wpProject.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAll();

    Company findByName(String companyName);

    void deleteByName(String companyName);
}
