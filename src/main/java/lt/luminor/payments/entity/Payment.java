package lt.luminor.payments.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Payment extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PaymentType paymentType;

    @ManyToOne
    private Currency currency;

    @Column(nullable = false)
    private Double amount;
    
    @NotBlank
    @Column(name = "debtor_iban", nullable = false, length = 34)
    private String debtorIban;
    
    @NotBlank
    @Column(name = "creditor_iban", nullable = false, length = 34)
    private String creditorIban;

    @Column(name = "creditor_bic", length = 11)
    private String creditorBic;

    private String details;

    private Double fee;

    @ManyToOne
    private PaymentStatus paymentStatus;
    
    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
