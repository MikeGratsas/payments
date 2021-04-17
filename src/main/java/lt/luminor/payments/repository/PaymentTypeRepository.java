package lt.luminor.payments.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.PaymentType;

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {
	Optional<PaymentType> findByName(String name);
}
