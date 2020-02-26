package lt.luminor.payments.listener;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lt.luminor.payments.event.CountryLogEvent;
import lt.luminor.payments.exceptions.CountryNotFoundException;
import lt.luminor.payments.form.CountryModel;
import lt.luminor.payments.service.CountryService;
import lt.luminor.payments.service.GeoLocationService;

public class CountryLogListener implements ApplicationListener<CountryLogEvent> {
	private final static Logger LOGGER = Logger.getLogger(CountryLogListener.class.getName()); 
	
    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private CountryService countryService;

    private CountryModel findCountryByAddress(String address) {
    	CountryModel countryModel = null;
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> resultAsset = template.getForEntity("https://ipinfo.io/" + address + "/country", String.class);
		if (resultAsset.getStatusCode() == HttpStatus.OK) {
			String countryCode = resultAsset.getBody();
			if (countryCode != null) {
				countryModel = countryService.findByCode(countryCode);
				if (countryModel == null) {
					Locale locale = new Locale("en", countryCode);
					countryModel = countryService.createCountry(new CountryModel(locale.getCountry(), locale.getDisplayCountry()));
				} 
			}
		}
		return countryModel;		
	}
    
	@Override
	public void onApplicationEvent(CountryLogEvent event) {
		final String address = event.getAddress();
		LOGGER.info("Resolving country from IP address: " + address);
		final CountryModel countryModel = findCountryByAddress(address);
		try {
			geoLocationService.saveGeoLocation(countryModel);
		}
		catch (CountryNotFoundException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

}
