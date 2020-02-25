package lt.luminor.payments.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import lt.luminor.payments.event.PaymentSaveEvent;
import lt.luminor.payments.exceptions.PaymentNotFoundException;
import lt.luminor.payments.form.PaymentModel;
import lt.luminor.payments.service.NotificationService;

public class PaymentSaveListener implements ApplicationListener<PaymentSaveEvent> {
	private final static Logger LOGGER = Logger.getLogger(PaymentSaveListener.class.getName()); 
	
    @Autowired
    private NotificationService notificationService;


	@Override
	public void onApplicationEvent(PaymentSaveEvent event) {
		final PaymentModel payment = event.getPayment();
		try {
			notificationService.sendNotification(payment);
		}
		catch (PaymentNotFoundException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

}
