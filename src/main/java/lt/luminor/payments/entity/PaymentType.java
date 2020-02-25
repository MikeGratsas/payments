package lt.luminor.payments.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "fee_coefficient")
    private Double feeCoefficient;

    private boolean detailsMandatory;
    
    private boolean creditorBicMandatory;

    private boolean notified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "payment_type_currencies", joinColumns = @JoinColumn(name = "payment_type", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "currency_id", referencedColumnName = "id"))
    private Set<Currency> currencies = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    public PaymentType() {
    }

    public PaymentType(String name) {
        this.name = name;
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

	public Set<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Set<Currency> currencies) {
		this.currencies = currencies;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentType that = (PaymentType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
