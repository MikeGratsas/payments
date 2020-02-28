package lt.luminor.payments.form;

import java.math.BigDecimal;

public class PaymentFeeModel {

	private Long id;
    private BigDecimal fee;
    
    public PaymentFeeModel() {
	}
    
	public PaymentFeeModel(Long id, BigDecimal fee) {
		this.id = id;
		this.fee = fee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

}
