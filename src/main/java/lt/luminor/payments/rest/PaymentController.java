package lt.luminor.payments.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import lt.luminor.payments.config.CustomUserDetails;
import lt.luminor.payments.event.CountryLogEvent;
import lt.luminor.payments.exceptions.CreditorBankRequiredException;
import lt.luminor.payments.exceptions.DetailsRequiredException;
import lt.luminor.payments.exceptions.InvalidCurrencyException;
import lt.luminor.payments.exceptions.InvalidPaymentDateException;
import lt.luminor.payments.exceptions.InvalidPaymentTypeException;
import lt.luminor.payments.exceptions.PaymentClientException;
import lt.luminor.payments.exceptions.PaymentNotFoundException;
import lt.luminor.payments.form.PaymentFeeModel;
import lt.luminor.payments.form.PaymentModel;
import lt.luminor.payments.service.PaymentService;
import lt.luminor.payments.util.RequestUtil;

@RestController
@RequestMapping("/api")     
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping(path="/payments/{paymentId}")
    public ResponseEntity<PaymentModel> getPaymentById(@PathVariable("paymentId") final Long id)
    {
    	PaymentModel payment;
        try {
        	payment = paymentService.findPayment(id);
        }
        catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
        }
        return new ResponseEntity<PaymentModel>(payment, HttpStatus.OK);
    }

    @GetMapping(path="/payments/{paymentId}/fee")
    public ResponseEntity<PaymentFeeModel> getPaymentFee(@PathVariable("paymentId") final Long id)
    {
    	PaymentFeeModel payment;
        try {
        	payment = paymentService.getPaymentFee(id);
        }
        catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
        }
        return new ResponseEntity<PaymentFeeModel>(payment, HttpStatus.OK);
    }

    @GetMapping(path="/payments")
    public ResponseEntity<List<PaymentModel>> listPayments(final Pageable pageable)
    {
        return new ResponseEntity<List<PaymentModel>>(paymentService.listPayments(getCurrentClientId(), 1L, pageable), HttpStatus.OK);
    }

    @GetMapping(path="/payments/lessThanAmount")
    public ResponseEntity<List<PaymentModel>> listPaymentsLessThanAmount(@RequestParam final String currency, @RequestParam final Double amount, final Pageable pageable)
    {
        return new ResponseEntity<List<PaymentModel>>(paymentService.listPaymentsLessThanAmount(getCurrentClientId(), currency, amount, 1L, pageable), HttpStatus.OK);
    }

    @GetMapping(path="/payments/greaterThanAmount")
    public ResponseEntity<List<PaymentModel>> listPaymentsGreaterThanAmount(@RequestParam final String currency, @RequestParam final Double amount, final Pageable pageable)
    {
        return new ResponseEntity<List<PaymentModel>>(paymentService.listPaymentsGreaterThanAmount(getCurrentClientId(), currency, amount, 1L, pageable), HttpStatus.OK);
    }

    @GetMapping(path="/payments/{paymentId}/cancel")
    public ResponseEntity<PaymentFeeModel> cancelPayment(@PathVariable("paymentId") final Long id)
    {
    	PaymentFeeModel payment;
        try {
        	payment = paymentService.cancelPayment(id);
        }
        catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
        }
        catch (PaymentClientException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getLocalizedMessage(), e);
		}
        catch (InvalidPaymentDateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage(), e);
		}
        return new ResponseEntity<PaymentFeeModel>(payment, HttpStatus.OK);
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentModel> createPayment(@Valid @RequestBody final PaymentModel payment)
    {
    	PaymentModel paymentModel;
        try {
        	final String remoteAddress = RequestUtil.getRemoteIP(RequestContextHolder.currentRequestAttributes());
        	paymentModel = paymentService.createPayment(payment);
        	eventPublisher.publishEvent(new CountryLogEvent(remoteAddress));
        }
        catch (DetailsRequiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage(), e);
        }
        catch (CreditorBankRequiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage(), e);
		}
        catch (InvalidCurrencyException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage(), e);
		}
        catch (InvalidPaymentTypeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage(), e);
		}
        return new ResponseEntity<PaymentModel>(paymentModel, HttpStatus.CREATED);
    }

	private Long getCurrentClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getId();
	}
}
