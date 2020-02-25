package lt.luminor.payments.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.PaymentStatus;
import lt.luminor.payments.exceptions.PaymentStatusNotFoundException;
import lt.luminor.payments.exceptions.PaymentStatusUpdatedException;
import lt.luminor.payments.form.PaymentStatusModel;
import lt.luminor.payments.repository.PaymentStatusRepository;

@Service
public class PaymentStatusService {
    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public List<PaymentStatusModel> listPaymentStatuss() {
        List<PaymentStatus> paymentStatusList = (List<PaymentStatus>)paymentStatusRepository.findAll();
        return paymentStatusList.stream().map(PaymentStatusService::assemblePaymentStatusModel).collect(Collectors.toList());
    }

    public PaymentStatusModel createPaymentStatus(String name) {
        PaymentStatus paymentStatusEntity = new PaymentStatus();
        paymentStatusEntity.setName(name);
        PaymentStatus c = paymentStatusRepository.save(paymentStatusEntity);
        return assemblePaymentStatusModel(c);
    }

    public PaymentStatusModel createPaymentStatus(PaymentStatusModel paymentStatusModel) {
        PaymentStatus paymentStatusEntity = new PaymentStatus();
        paymentStatusEntity.setName(paymentStatusModel.getName());
        PaymentStatus c = paymentStatusRepository.save(paymentStatusEntity);
        return assemblePaymentStatusModel(c);
    }

    public PaymentStatusModel savePaymentStatus(PaymentStatusModel paymentStatusModel) throws PaymentStatusNotFoundException, PaymentStatusUpdatedException {
        PaymentStatus paymentStatusEntity;
        Long id = paymentStatusModel.getId();
        if (id != null) {
            Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(id);
            if (paymentStatusOptional.isPresent()) {
                paymentStatusEntity = paymentStatusOptional.get();
                if (!paymentStatusEntity.getLastUpdated().equals(paymentStatusModel.getLastUpdated())) {
                    throw new PaymentStatusUpdatedException(id);
                }
            }
            else {
                throw new PaymentStatusNotFoundException(id);
            }
        }
        else {
            paymentStatusEntity = new PaymentStatus();
        }
        paymentStatusEntity.setName(paymentStatusModel.getName());
        PaymentStatus c = paymentStatusRepository.save(paymentStatusEntity);
        return assemblePaymentStatusModel(c);
    }

    public PaymentStatusModel findPaymentStatus(Long id) {
        PaymentStatusModel paymentStatusModel = null;
        Optional<PaymentStatus> paymentStatusEntity = paymentStatusRepository.findById(id);
        if (paymentStatusEntity.isPresent()) {
            PaymentStatus c = paymentStatusEntity.get();
            paymentStatusModel = assemblePaymentStatusModel(c);
        }
        return paymentStatusModel;
    }

    public PaymentStatusModel findByName(String name) {
        PaymentStatusModel paymentStatusModel = null;
        Optional<PaymentStatus> paymentStatusEntity = paymentStatusRepository.findByName(name);
        if (paymentStatusEntity.isPresent()) {
            PaymentStatus c = paymentStatusEntity.get();
            paymentStatusModel = assemblePaymentStatusModel(c);
        }
        return paymentStatusModel;
    }

    public void deletePaymentStatuss(Long[] ids) {
        for (Long id: ids) {
            paymentStatusRepository.deleteById(id);
        }
    }

    private static PaymentStatusModel assemblePaymentStatusModel(PaymentStatus paymentStatusEntity) {
        return new PaymentStatusModel(paymentStatusEntity.getId(), paymentStatusEntity.getName(), paymentStatusEntity.getCreated(), paymentStatusEntity.getLastUpdated());
    }

}
