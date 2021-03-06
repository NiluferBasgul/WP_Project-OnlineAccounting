package wpProject.repository;

import org.springframework.data.repository.CrudRepository;

import wpProject.model.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String firstname);
}
