package lt.luminor.payments.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

public class PaymentModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2638836202961216653L;
	
	private Long id;
    @NotBlank(message = "{payment.paymentType.required}")
    private String paymentType;
    @NotBlank(message = "{payment.currency.required}")
    private String currency;
    @Positive(message = "{payment.amount.positive}")
    private BigDecimal amount;
    @NotBlank(message = "{payment.debtorIban.required}")
    private String debtorIban;
    @NotBlank(message = "{payment.creditorIban.required}")
    private String creditorIban;
    private String creditorBic;
    private String details;
    private Long createdBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
    
    public PaymentModel() {
	}
    
	public PaymentModel(Long id, String paymentType, String currency, BigDecimal amount, String debtorIban, String creditorIban, String creditorBic,
			String details, Long createdBy, LocalDateTime created, LocalDateTime lastUpdated) {
		this.id = id;
		this.paymentType = paymentType;
		this.currency = currency;
		this.amount = amount;
		this.debtorIban = debtorIban;
		this.creditorIban = creditorIban;
		this.creditorBic = creditorBic;
		this.details = details;
		this.createdBy = createdBy;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentModel [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (paymentType != null) {
			builder.append("paymentType=");
			builder.append(paymentType);
			builder.append(", ");
		}
		if (currency != null) {
			builder.append("currency=");
			builder.append(currency);
			builder.append(", ");
		}
		if (amount != null) {
			builder.append("amount=");
			builder.append(amount);
			builder.append(", ");
		}
		if (debtorIban != null) {
			builder.append("debtorIban=");
			builder.append(debtorIban);
			builder.append(", ");
		}
		if (creditorIban != null) {
			builder.append("creditorIban=");
			builder.append(creditorIban);
			builder.append(", ");
		}
		if (creditorBic != null) {
			builder.append("creditorBic=");
			builder.append(creditorBic);
			builder.append(", ");
		}
		if (details != null) {
			builder.append("details=");
			builder.append(details);
		}
		builder.append("]");
		return builder.toString();
	}

}
