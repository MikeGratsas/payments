package lt.luminor.payments.event;

import org.springframework.context.ApplicationEvent;

public class CountryLogEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4997211161870205010L;
	
	private final String address;
	
	public CountryLogEvent(String address) {
		super(address);
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}
