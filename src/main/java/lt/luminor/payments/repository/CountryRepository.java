package lt.luminor.payments.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
	Optional<Country> findByCode(String code);
}
