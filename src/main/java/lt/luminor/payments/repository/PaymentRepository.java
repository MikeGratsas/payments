package lt.luminor.payments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.Currency;
import lt.luminor.payments.entity.Payment;
import lt.luminor.payments.entity.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Page<Payment> findByCreatedByAndPaymentStatusOrderByPaymentType(Long createdBy, PaymentStatus paymentStatus, Pageable pageable);
	Page<Payment> findByCreatedByAndCurrencyAndPaymentStatusOrderByAmount(Long createdBy, Currency currency, PaymentStatus paymentStatus, Pageable pageable);
	Page<Payment> findByCreatedByAndCurrencyAndPaymentStatusAndAmountLessThanOrderByAmount(Long createdBy, Currency currency, PaymentStatus paymentStatus, Double amount, Pageable pageable);
	Page<Payment> findByCreatedByAndCurrencyAndPaymentStatusAndAmountGreaterThanOrderByAmount(Long createdBy, Currency currency, PaymentStatus paymentStatus, Double amount, Pageable pageable);
}
