package lt.luminor.payments.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.PaymentStatus;

@Repository
public interface PaymentStatusRepository extends CrudRepository<PaymentStatus, Long> {
	Optional<PaymentStatus> findByName(String name);
}
