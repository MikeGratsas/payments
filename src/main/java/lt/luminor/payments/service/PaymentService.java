package lt.luminor.payments.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.Currency;
import lt.luminor.payments.entity.Payment;
import lt.luminor.payments.entity.PaymentStatus;
import lt.luminor.payments.entity.PaymentType;
import lt.luminor.payments.event.PaymentSaveEvent;
import lt.luminor.payments.exceptions.CreditorBankRequiredException;
import lt.luminor.payments.exceptions.DetailsRequiredException;
import lt.luminor.payments.exceptions.InvalidCurrencyException;
import lt.luminor.payments.exceptions.InvalidPaymentDateException;
import lt.luminor.payments.exceptions.InvalidPaymentTypeException;
import lt.luminor.payments.exceptions.PaymentClientException;
import lt.luminor.payments.exceptions.PaymentNotFoundException;
import lt.luminor.payments.form.PaymentFeeModel;
import lt.luminor.payments.form.PaymentModel;
import lt.luminor.payments.repository.CurrencyRepository;
import lt.luminor.payments.repository.PaymentRepository;
import lt.luminor.payments.repository.PaymentStatusRepository;
import lt.luminor.payments.repository.PaymentTypeRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private PaymentStatusRepository paymentStatusRepository;
    @Autowired
    private AuditorAware<Long> auditorAware;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public List<PaymentModel> listPayments(final Long clientId, final Long paymentStatusId, final Pageable pageable) {
        Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(paymentStatusId);
        if (paymentStatusOptional.isPresent()) {
            Page<Payment> paymentEnties = paymentRepository.findByCreatedByAndPaymentStatusOrderByPaymentType(clientId, paymentStatusOptional.get(), pageable);
            return paymentEnties.stream().map(PaymentService::assemblePaymentModel).collect(Collectors.toList());
        }
        return Collections.emptyList();
	}

    public List<PaymentModel> listPaymentsOrderByAmount(final Long clientId, final String currency, final Long paymentStatusId, final Pageable pageable) {
        Optional<Currency> currencyOptional = currencyRepository.findByCode(currency);
        if (currencyOptional.isPresent()) {
			Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(paymentStatusId);
			if (paymentStatusOptional.isPresent()) {
				Page<Payment> paymentEnties = paymentRepository.findByCreatedByAndCurrencyAndPaymentStatusOrderByAmount(clientId, currencyOptional.get(), paymentStatusOptional.get(), pageable);
				return paymentEnties.stream().map(PaymentService::assemblePaymentModel).collect(Collectors.toList());
			} 
		}
		return Collections.emptyList();
	}

    public List<PaymentModel> listPaymentsLessThanAmount(final Long clientId, final String currency, final BigDecimal amount, final Long paymentStatusId, final Pageable pageable) {
        Optional<Currency> currencyOptional = currencyRepository.findByCode(currency);
        if (currencyOptional.isPresent()) {
			Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(paymentStatusId);
			if (paymentStatusOptional.isPresent()) {
				Page<Payment> paymentEnties = paymentRepository.findByCreatedByAndCurrencyAndPaymentStatusAndAmountLessThanOrderByAmount(clientId, currencyOptional.get(), paymentStatusOptional.get(), amount, pageable);
				return paymentEnties.stream().map(PaymentService::assemblePaymentModel).collect(Collectors.toList());
			} 
		}
		return Collections.emptyList();
	}

    public List<PaymentModel> listPaymentsGreaterThanAmount(final Long clientId, final String currency, final BigDecimal amount, final Long paymentStatusId, final Pageable pageable) {
        Optional<Currency> currencyOptional = currencyRepository.findByCode(currency);
        if (currencyOptional.isPresent()) {
			Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(paymentStatusId);
			if (paymentStatusOptional.isPresent()) {
				Page<Payment> paymentEnties = paymentRepository.findByCreatedByAndCurrencyAndPaymentStatusAndAmountGreaterThanOrderByAmount(clientId, currencyOptional.get(), paymentStatusOptional.get(), amount, pageable);
				return paymentEnties.stream().map(PaymentService::assemblePaymentModel).collect(Collectors.toList());
			} 
		}
		return Collections.emptyList();
	}

    public PaymentModel findPayment(Long id) throws PaymentNotFoundException {
        PaymentModel paymentModel;
        Optional<Payment> paymentEntity = paymentRepository.findById(id);
        if (paymentEntity.isPresent()) {
            paymentModel = assemblePaymentModel(paymentEntity.get());
        }
        else {
            throw new PaymentNotFoundException(id);
        }
        return paymentModel;
	}

    public PaymentFeeModel getPaymentFee(Long id) throws PaymentNotFoundException {
        Optional<Payment> paymentEntityOptional = paymentRepository.findById(id);
        if (!paymentEntityOptional.isPresent())
            throw new PaymentNotFoundException(id);
    	Payment paymentEntity = paymentEntityOptional.get();
        return new PaymentFeeModel(paymentEntity.getId(), paymentEntity.getFee());
	}

    public PaymentModel createPayment(PaymentModel paymentModel) throws DetailsRequiredException, CreditorBankRequiredException, InvalidCurrencyException, InvalidPaymentTypeException {
        Payment paymentEntity = new Payment();
        paymentEntity.setAmount(paymentModel.getAmount());
        paymentEntity.setDebtorIban(paymentModel.getDebtorIban());
        paymentEntity.setCreditorIban(paymentModel.getCreditorIban());
    	Currency currencyEntity = currencyRepository.findByCode(paymentModel.getCurrency()).orElse(null);
    	Optional<PaymentType> paymentTypeOptional = paymentTypeRepository.findByName(paymentModel.getPaymentType());
		if (!paymentTypeOptional.isPresent())
			throw new InvalidPaymentTypeException(paymentModel.getPaymentType());
    	PaymentType paymentTypeEntity = paymentTypeOptional.get();
		String details = paymentModel.getDetails();
    	if (paymentTypeEntity.isDetailsMandatory()) {
    		if (details == null || details.trim().isEmpty())
    			throw new DetailsRequiredException(paymentModel.getPaymentType());
    	}
        paymentEntity.setDetails(details);
		String creditorBic = paymentModel.getCreditorBic();
    	if (paymentTypeEntity.isCreditorBicMandatory()) {
    		if (creditorBic == null || creditorBic.trim().isEmpty())
    			throw new CreditorBankRequiredException(paymentModel.getPaymentType());
    	}
        paymentEntity.setCreditorBic(creditorBic);
    	Set<Currency> currencies = paymentTypeEntity.getCurrencies();
    	if (currencyEntity == null || !currencies.contains(currencyEntity))
			throw new InvalidCurrencyException(paymentModel.getCurrency(), paymentModel.getPaymentType());
        paymentEntity.setCurrency(currencyEntity);
    	paymentEntity.setPaymentType(paymentTypeEntity);
        Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(1L);
        if (paymentStatusOptional.isPresent())
        	paymentEntity.setPaymentStatus(paymentStatusOptional.get());
        PaymentModel payment = assemblePaymentModel(paymentRepository.save(paymentEntity));
        if (paymentTypeEntity.isNotified())
        	eventPublisher.publishEvent(new PaymentSaveEvent(payment));
        return payment;
    }
    
    public PaymentFeeModel cancelPayment(Long id) throws PaymentNotFoundException, PaymentClientException, InvalidPaymentDateException {
    	Optional<Long> currentClient = auditorAware.getCurrentAuditor();
    	if (!currentClient.isPresent())
    		throw new AuthenticationCredentialsNotFoundException("An Authentication object was not found in the SecurityContext");
    	Optional<Payment> paymentOptional = paymentRepository.findById(id);
    	if (!paymentOptional.isPresent())
    		throw new PaymentNotFoundException(id);
    	Payment paymentEntity = paymentOptional.get();
    	if (!currentClient.get().equals(paymentEntity.getCreatedBy()))
    		throw new PaymentClientException(id);
		final LocalDateTime now = LocalDateTime.now();
    	final LocalDateTime created = paymentEntity.getCreated();
    	final LocalDate creationDate = created.toLocalDate();
		if (!now.toLocalDate().isEqual(creationDate))
    		throw new InvalidPaymentDateException(creationDate);
    	PaymentType paymentTypeEntity = paymentEntity.getPaymentType();
    	if (paymentTypeEntity != null) {
			long hours = ChronoUnit.HOURS.between(created, now);
    		paymentEntity.setFee(BigDecimal.valueOf(paymentTypeEntity.getFeeCoefficient().doubleValue()).multiply(BigDecimal.valueOf(hours)));
    	}
        Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(2L);
        if (paymentStatusOptional.isPresent())
        	paymentEntity.setPaymentStatus(paymentStatusOptional.get());
        paymentRepository.save(paymentEntity);
        return new PaymentFeeModel(paymentEntity.getId(), paymentEntity.getFee());
    }
    
    public void deletePayments(Long[] ids) {
        for (Long id: ids) {
            paymentRepository.deleteById(id);
        }
    }

    private static PaymentModel assemblePaymentModel(Payment paymentEntity) {
    	String currency = null;
        Currency c = paymentEntity.getCurrency();
        if (c != null) {
        	currency = c.getCode();
        }
        String paymentType = null;
        PaymentType t = paymentEntity.getPaymentType();
        if (t != null) {
        	paymentType = t.getName();
        }
        return new PaymentModel(paymentEntity.getId(), paymentType, currency, paymentEntity.getAmount(), paymentEntity.getDebtorIban(), paymentEntity.getCreditorIban(), paymentEntity.getCreditorBic(), paymentEntity.getDetails(), paymentEntity.getCreatedBy(), paymentEntity.getCreated(), paymentEntity.getLastUpdated());
    }
}
