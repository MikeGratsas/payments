package lt.luminor.payments.form;

import java.time.LocalDateTime;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

public class PaymentModel {

	private Long id;
    @NotBlank(message = "{payment.paymentType.required}")
    private String paymentType;
    @NotBlank(message = "{payment.currency.required}")
    private String currency;
    @Positive(message = "{payment.amount.positive}")
    private Double amount;
    @NotBlank(message = "{payment.debtorIban.required}")
    private String debtorIban;
    @NotBlank(message = "{payment.creditorIban.required}")
    private String creditorIban;
    private String creditorBic;
    private String details;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
    
    public PaymentModel() {
	}
    
	public PaymentModel(Long id, String paymentType, String currency, Double amount, String debtorIban, String creditorIban, String creditorBic,
			String details, LocalDateTime created, LocalDateTime lastUpdated) {
		this.id = id;
		this.paymentType = paymentType;
		this.currency = currency;
		this.amount = amount;
		this.debtorIban = debtorIban;
		this.creditorIban = creditorIban;
		this.creditorBic = creditorBic;
		this.details = details;
		this.created = created;
		this.lastUpdated = lastUpdated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDebtorIban() {
		return debtorIban;
	}

	public void setDebtorIban(String debtorIban) {
		this.debtorIban = debtorIban;
	}

	public String getCreditorIban() {
		return creditorIban;
	}

	public void setCreditorIban(String creditorIban) {
		this.creditorIban = creditorIban;
	}

	public String getCreditorBic() {
		return creditorBic;
	}

	public void setCreditorBic(String creditorBic) {
		this.creditorBic = creditorBic;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
