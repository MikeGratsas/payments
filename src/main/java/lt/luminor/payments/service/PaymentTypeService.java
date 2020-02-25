package lt.luminor.payments.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.PaymentType;
import lt.luminor.payments.exceptions.PaymentTypeNotFoundException;
import lt.luminor.payments.exceptions.PaymentTypeUpdatedException;
import lt.luminor.payments.form.CurrencyModel;
import lt.luminor.payments.form.PaymentTypeModel;
import lt.luminor.payments.repository.CurrencyRepository;
import lt.luminor.payments.repository.PaymentTypeRepository;

@Service
@Transactional
public class PaymentTypeService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    public List<PaymentTypeModel> listPaymentTypes() {
        List<PaymentType> paymentTypeList = (List<PaymentType>)paymentTypeRepository.findAll();
        return paymentTypeList.stream().map(PaymentTypeService::assemblePaymentTypeModel).collect(Collectors.toList());
    }

    public PaymentTypeModel createPaymentType(String name) {
        PaymentType paymentTypeEntity = new PaymentType();
        paymentTypeEntity.setName(name);
        PaymentType c = paymentTypeRepository.save(paymentTypeEntity);
        return assemblePaymentTypeModel(c);
    }

    public PaymentTypeModel createPaymentType(PaymentTypeModel paymentTypeModel) {
        PaymentType paymentTypeEntity = new PaymentType();
        paymentTypeEntity.setName(paymentTypeModel.getName());
        paymentTypeEntity.setFeeCoefficient(paymentTypeModel.getFeeCoefficient());
        paymentTypeEntity.setDetailsMandatory(paymentTypeModel.isDetailsMandatory());
        paymentTypeEntity.setCreditorBicMandatory(paymentTypeModel.isCreditorBicMandatory());
        paymentTypeEntity.setNotified(paymentTypeModel.isNotified());
        List<CurrencyModel> currencies = paymentTypeModel.getCurrencies();
        if (currencies != null) {
        	paymentTypeEntity.setCurrencies(currencies.stream().map(c -> currencyRepository.findById(c.getId())).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet()));
        }
        PaymentType c = paymentTypeRepository.save(paymentTypeEntity);
        return assemblePaymentTypeModel(c);
    }

    public PaymentTypeModel savePaymentType(PaymentTypeModel paymentTypeModel) throws PaymentTypeNotFoundException, PaymentTypeUpdatedException {
        PaymentType paymentTypeEntity;
        Long id = paymentTypeModel.getId();
        if (id != null) {
            Optional<PaymentType> paymentTypeOptional = paymentTypeRepository.findById(id);
            if (paymentTypeOptional.isPresent()) {
                paymentTypeEntity = paymentTypeOptional.get();
                if (!paymentTypeEntity.getLastUpdated().equals(paymentTypeModel.getLastUpdated())) {
                    throw new PaymentTypeUpdatedException(id);
                }
            }
            else {
                throw new PaymentTypeNotFoundException(id);
            }
        }
        else {
            paymentTypeEntity = new PaymentType();
        }
        paymentTypeEntity.setName(paymentTypeModel.getName());
        paymentTypeEntity.setFeeCoefficient(paymentTypeModel.getFeeCoefficient());
        paymentTypeEntity.setDetailsMandatory(paymentTypeModel.isDetailsMandatory());
        paymentTypeEntity.setCreditorBicMandatory(paymentTypeModel.isCreditorBicMandatory());
        paymentTypeEntity.setNotified(paymentTypeModel.isNotified());
        List<CurrencyModel> currencies = paymentTypeModel.getCurrencies();
        if (currencies != null) {
        	paymentTypeEntity.setCurrencies(currencies.stream().map(c -> currencyRepository.findById(c.getId())).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet()));
        }
        PaymentType c = paymentTypeRepository.save(paymentTypeEntity);
        return assemblePaymentTypeModel(c);
    }

    public PaymentTypeModel findPaymentType(Long id) {
        PaymentTypeModel paymentTypeModel = null;
        Optional<PaymentType> paymentTypeEntity = paymentTypeRepository.findById(id);
        if (paymentTypeEntity.isPresent()) {
            PaymentType c = paymentTypeEntity.get();
            paymentTypeModel = assemblePaymentTypeModel(c);
        }
        return paymentTypeModel;
    }

    public PaymentTypeModel findByName(String name) {
        PaymentTypeModel paymentTypeModel = null;
        Optional<PaymentType> paymentTypeEntity = paymentTypeRepository.findByName(name);
        if (paymentTypeEntity.isPresent()) {
            PaymentType c = paymentTypeEntity.get();
            paymentTypeModel = assemblePaymentTypeModel(c);
        }
        return paymentTypeModel;
    }

    public void deletePaymentTypes(Long[] ids) {
        for (Long id: ids) {
            paymentTypeRepository.deleteById(id);
        }
    }

    private static PaymentTypeModel assemblePaymentTypeModel(PaymentType paymentTypeEntity) {
        return new PaymentTypeModel(paymentTypeEntity.getId(), paymentTypeEntity.getName(), paymentTypeEntity.getFeeCoefficient(), paymentTypeEntity.isDetailsMandatory(), paymentTypeEntity.isCreditorBicMandatory(), paymentTypeEntity.isNotified(), 
        		paymentTypeEntity.getCurrencies().stream().map(c -> new CurrencyModel(c.getId(), c.getCode(), c.getDescription(), c.getCreated(), c.getLastUpdated())).collect(Collectors.toList()), paymentTypeEntity.getCreated(), paymentTypeEntity.getLastUpdated());
    }

}
