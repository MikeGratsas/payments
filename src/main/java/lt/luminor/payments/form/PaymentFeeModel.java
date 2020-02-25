package lt.luminor.payments.form;

public class PaymentFeeModel {

	private Long id;
    private Double fee;
    
    public PaymentFeeModel() {
	}
    
	public PaymentFeeModel(Long id, Double fee) {
		this.id = id;
		this.fee = fee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

}
