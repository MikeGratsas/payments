package lt.luminor.payments.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
	Optional<Currency> findByCode(String code);
}
