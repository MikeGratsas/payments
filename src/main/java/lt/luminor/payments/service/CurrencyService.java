package lt.luminor.payments.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.Currency;
import lt.luminor.payments.exceptions.CurrencyNotFoundException;
import lt.luminor.payments.exceptions.CurrencyUpdatedException;
import lt.luminor.payments.form.CurrencyModel;
import lt.luminor.payments.repository.CurrencyRepository;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<CurrencyModel> listCurrencies() {
        List<Currency> currencyList = (List<Currency>)currencyRepository.findAll();
        return currencyList.stream().map(CurrencyService::assembleCurrencyModel).collect(Collectors.toList());
    }

    public CurrencyModel createCurrency(String name) {
        Currency currencyEntity = new Currency();
        currencyEntity.setCode(name);
        Currency c = currencyRepository.save(currencyEntity);
        return assembleCurrencyModel(c);
    }

    public CurrencyModel createCurrency(CurrencyModel currencyModel) {
        Currency currencyEntity = new Currency();
        currencyEntity.setCode(currencyModel.getCode());
        currencyEntity.setDescription(currencyModel.getDescription());
        Currency c = currencyRepository.save(currencyEntity);
        return assembleCurrencyModel(c);
    }

    public CurrencyModel saveCurrency(CurrencyModel currencyModel) throws CurrencyNotFoundException, CurrencyUpdatedException {
        Currency currencyEntity;
        Long id = currencyModel.getId();
        if (id != null) {
            Optional<Currency> currencyOptional = currencyRepository.findById(id);
            if (currencyOptional.isPresent()) {
                currencyEntity = currencyOptional.get();
                if (!currencyEntity.getLastUpdated().equals(currencyModel.getLastUpdated())) {
                    throw new CurrencyUpdatedException(id);
                }
            }
            else {
                throw new CurrencyNotFoundException(id);
            }
        }
        else {
            currencyEntity = new Currency();
        }
        currencyEntity.setCode(currencyModel.getCode());
        currencyEntity.setDescription(currencyModel.getDescription());
        Currency c = currencyRepository.save(currencyEntity);
        return assembleCurrencyModel(c);
    }

    public CurrencyModel findCurrency(Long id) {
        CurrencyModel currencyModel = null;
        Optional<Currency> currencyEntity = currencyRepository.findById(id);
        if (currencyEntity.isPresent()) {
            Currency c = currencyEntity.get();
            currencyModel = assembleCurrencyModel(c);
        }
        return currencyModel;
    }

    public CurrencyModel findByCode(String code) {
        CurrencyModel currencyModel = null;
        Optional<Currency> currencyEntity = currencyRepository.findByCode(code);
        if (currencyEntity.isPresent()) {
            Currency c = currencyEntity.get();
            currencyModel = assembleCurrencyModel(c);
        }
        return currencyModel;
    }

    public void deleteCurrencies(Long[] ids) {
        for (Long id: ids) {
            currencyRepository.deleteById(id);
        }
    }

    private static CurrencyModel assembleCurrencyModel(Currency currencyEntity) {
        return new CurrencyModel(currencyEntity.getId(), currencyEntity.getCode(), currencyEntity.getDescription(), currencyEntity.getCreated(), currencyEntity.getLastUpdated());
    }

}
