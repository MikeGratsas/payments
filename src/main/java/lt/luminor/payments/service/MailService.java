package lt.luminor.payments.service;

import lt.luminor.payments.form.MessageModel;

public interface MailService {

	int send(MessageModel messageModel);

}