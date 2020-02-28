package lt.luminor.payments.listener;

import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lt.luminor.payments.event.PaymentSaveEvent;
import lt.luminor.payments.exceptions.PaymentNotFoundException;
import lt.luminor.payments.form.ClientModel;
import lt.luminor.payments.form.MessageModel;
import lt.luminor.payments.form.PaymentModel;
import lt.luminor.payments.service.ClientService;
import lt.luminor.payments.service.MailService;
import lt.luminor.payments.service.NotificationService;

@Component
@PropertySource("classpath:custom.properties")
public class PaymentSaveListener implements ApplicationListener<PaymentSaveEvent> {
	private final static Logger LOGGER = Logger.getLogger(PaymentSaveListener.class.getName()); 
	
	@Autowired
    private Environment environment;

	@Autowired
    private MessageSource messages;

    @Autowired
    private MailService mailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClientService clientService;

	@Override
	public void onApplicationEvent(PaymentSaveEvent event) {
		final PaymentModel payment = event.getPayment();
		try {
			final ClientModel clientModel = clientService.findUser(payment.getCreatedBy());
	        final MessageModel messageModel = constructMessage(clientModel, payment, Locale.forLanguageTag(clientModel.getLanguage()));
	        int result = mailService.send(messageModel);
			notificationService.saveNotification(result, payment);
		}
		catch (PaymentNotFoundException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

    private MessageModel constructMessage(final ClientModel client, final PaymentModel payment, final Locale locale) {
        final String body = messages.getMessage("message.paymentDetails", new Object[] { payment.getId(), payment.getPaymentType(), payment.getAmount(), payment.getCurrency(), payment.getDebtorIban(), payment.getCreditorIban() }, locale);
        MessageModel messageModel = new MessageModel(messages.getMessage("message.paymentReceived", null, locale));
        messageModel.setFromName(Objects.requireNonNull(environment.getProperty("support.name")));
        messageModel.setFromAddress(Objects.requireNonNull(environment.getProperty("support.email")));
        messageModel.setToName(client.getName());
        messageModel.setToAddress(client.getEmail());
        messageModel.setBody(body);
        return messageModel;
    }
}
