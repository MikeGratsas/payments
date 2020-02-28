package lt.luminor.payments.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.Notification;
import lt.luminor.payments.entity.Payment;
import lt.luminor.payments.exceptions.PaymentNotFoundException;
import lt.luminor.payments.form.PaymentModel;
import lt.luminor.payments.repository.NotificationRepository;
import lt.luminor.payments.repository.PaymentRepository;

@Service
public class NotificationService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public Integer saveNotification(int result, PaymentModel paymentModel) throws PaymentNotFoundException {
    	final Long id = paymentModel.getId();
		Optional<Payment> paymentOptional = paymentRepository.findById(id);
    	if (!paymentOptional.isPresent())
    		throw new PaymentNotFoundException(id);
    	Payment paymentEntity = paymentOptional.get();
        Notification notificationEntity = new Notification();
        notificationEntity.setPayment(paymentEntity);
        notificationEntity.setStatus(result);
        Notification c = notificationRepository.save(notificationEntity);
        return c.getStatus();
    }

    public void deleteNotifications(Long[] ids) {
        for (Long id: ids) {
            notificationRepository.deleteById(id);
        }
    }

}
