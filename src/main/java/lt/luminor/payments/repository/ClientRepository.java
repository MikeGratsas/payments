package lt.luminor.payments.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);
}
