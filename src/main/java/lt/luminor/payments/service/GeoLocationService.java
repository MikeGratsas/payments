package lt.luminor.payments.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.luminor.payments.entity.Country;
import lt.luminor.payments.entity.GeoLocation;
import lt.luminor.payments.exceptions.CountryNotFoundException;
import lt.luminor.payments.form.CountryModel;
import lt.luminor.payments.repository.CountryRepository;
import lt.luminor.payments.repository.GeoLocationRepository;

@Service
public class GeoLocationService {
	
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    public Long saveGeoLocation(CountryModel countryModel) throws CountryNotFoundException {
    	Long resultId = null; 
    	if (countryModel != null) {
        	final Long id = countryModel.getId();
    		Optional<Country> countryOptional = countryRepository.findById(id);
        	if (!countryOptional.isPresent())
        		throw new CountryNotFoundException(id);
        	Country countryEntity = countryOptional.get();
        	GeoLocation geoLocationEntity = new GeoLocation();
            geoLocationEntity.setCountry(countryEntity);
            geoLocationRepository.save(geoLocationEntity);
            resultId = geoLocationEntity.getId();
    	} 
    	return resultId;
    }

    public void deleteGeoLocation(Long[] ids) {
        for (Long id: ids) {
        	geoLocationRepository.deleteById(id);
        }
    }
}
