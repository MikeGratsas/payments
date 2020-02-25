package lt.luminor.payments.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.Country;
import lt.luminor.payments.exceptions.CountryNotFoundException;
import lt.luminor.payments.exceptions.CountryUpdatedException;
import lt.luminor.payments.form.CountryModel;
import lt.luminor.payments.repository.CountryRepository;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<CountryModel> listCountries() {
        List<Country> countryList = (List<Country>)countryRepository.findAll();
        return countryList.stream().map(CountryService::assembleCountryModel).collect(Collectors.toList());
    }

    public CountryModel createCountry(String name) {
        Country countryEntity = new Country();
        countryEntity.setCode(name);
        Country c = countryRepository.save(countryEntity);
        return assembleCountryModel(c);
    }

    public CountryModel createCountry(CountryModel countryModel) {
        Country countryEntity = new Country();
        countryEntity.setCode(countryModel.getCode());
        countryEntity.setDescription(countryModel.getDescription());
        Country c = countryRepository.save(countryEntity);
        return assembleCountryModel(c);
    }

    public CountryModel saveCountry(CountryModel countryModel) throws CountryNotFoundException, CountryUpdatedException {
        Country countryEntity;
        Long id = countryModel.getId();
        if (id != null) {
            Optional<Country> countryOptional = countryRepository.findById(id);
            if (countryOptional.isPresent()) {
                countryEntity = countryOptional.get();
                if (!countryEntity.getLastUpdated().equals(countryModel.getLastUpdated())) {
                    throw new CountryUpdatedException(id);
                }
            }
            else {
                throw new CountryNotFoundException(id);
            }
        }
        else {
            countryEntity = new Country();
        }
        countryEntity.setCode(countryModel.getCode());
        countryEntity.setDescription(countryModel.getDescription());
        Country c = countryRepository.save(countryEntity);
        return assembleCountryModel(c);
    }

    public CountryModel findCountry(Long id) {
        CountryModel countryModel = null;
        Optional<Country> countryEntity = countryRepository.findById(id);
        if (countryEntity.isPresent()) {
            Country c = countryEntity.get();
            countryModel = assembleCountryModel(c);
        }
        return countryModel;
    }

    public CountryModel findByCode(String code) {
        CountryModel countryModel = null;
        Optional<Country> countryEntity = countryRepository.findByCode(code);
        if (countryEntity.isPresent()) {
        	Country c = countryEntity.get();
            countryModel = assembleCountryModel(c);
        }
        return countryModel;
    }

    public void deleteCountries(Long[] ids) {
        for (Long id: ids) {
            countryRepository.deleteById(id);
        }
    }

    private static CountryModel assembleCountryModel(Country countryEntity) {
        return new CountryModel(countryEntity.getId(), countryEntity.getCode(), countryEntity.getDescription(), countryEntity.getCreated(), countryEntity.getLastUpdated());
    }

}
