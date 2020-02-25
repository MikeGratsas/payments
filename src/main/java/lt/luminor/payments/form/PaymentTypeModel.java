package lt.luminor.payments.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PaymentTypeModel {

    private Long id;

    @NotBlank(message = "{paymentType.name.required}")
    private String name;

    @PositiveOrZero(message = "{payment.amount.notnegative}")
    private Double feeCoefficient;

    private boolean detailsMandatory;
    
    private boolean creditorBicMandatory;

    private boolean notified;

    private List<CurrencyModel> currencies;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;

    public PaymentTypeModel() {
    }

    public PaymentTypeModel(Long id) {
        this.id = id;
    }

    public PaymentTypeModel(String name) {
        this.name = name;
    }

    public PaymentTypeModel(String name, Double feeCoefficient, boolean detailsMandatory, boolean creditorBicMandatory, boolean notified, List<CurrencyModel> currencies) {
        this.name = name;
        this.feeCoefficient = feeCoefficient;
        this.detailsMandatory = detailsMandatory;
        this.creditorBicMandatory = creditorBicMandatory;
        this.notified = notified;
        this.currencies = currencies;
    }

    public PaymentTypeModel(Long id, String name, Double feeCoefficient, boolean detailsMandatory, boolean creditorBicMandatory, boolean notified, List<CurrencyModel> currencies, LocalDateTime created, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.feeCoefficient = feeCoefficient;
        this.detailsMandatory = detailsMandatory;
        this.creditorBicMandatory = creditorBicMandatory;
        this.notified = notified;
        this.currencies = currencies;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFeeCoefficient() {
		return feeCoefficient;
	}

	public void setFeeCoefficient(Double feeCoefficient) {
		this.feeCoefficient = feeCoefficient;
	}

	public boolean isDetailsMandatory() {
		return detailsMandatory;
	}

	public void setDetailsMandatory(boolean detailsMandatory) {
		this.detailsMandatory = detailsMandatory;
	}

	public boolean isCreditorBicMandatory() {
		return creditorBicMandatory;
	}

	public void setCreditorBicMandatory(boolean creditorBicMandatory) {
		this.creditorBicMandatory = creditorBicMandatory;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	public List<CurrencyModel> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<CurrencyModel> currencies) {
		this.currencies = currencies;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTypeModel that = (PaymentTypeModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created) &&
                Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created, lastUpdated);
    }
}
