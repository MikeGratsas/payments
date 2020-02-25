package lt.luminor.payments.component;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lt.luminor.payments.exceptions.UserEmailExistsException;
import lt.luminor.payments.form.ClientModel;
import lt.luminor.payments.form.CurrencyModel;
import lt.luminor.payments.form.PaymentStatusModel;
import lt.luminor.payments.form.PaymentTypeModel;
import lt.luminor.payments.form.RegistrationModel;
import lt.luminor.payments.service.ClientService;
import lt.luminor.payments.service.CurrencyService;
import lt.luminor.payments.service.PaymentStatusService;
import lt.luminor.payments.service.PaymentTypeService;

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private ClientService clientService;

    @Override
	public void run(ApplicationArguments args) throws Exception {
        createPaymentStatusIfNotFound("Created");
        createPaymentStatusIfNotFound("Canceled");
        createClientIfNotFound("Tester", "tester", "secret", "mgratsas@gmail.com", null, "en");
        final CurrencyModel eurCurrency = createCurrencyIfNotFound("EUR");
        final CurrencyModel usdCurrency = createCurrencyIfNotFound("USD");
		createPaymentTypeIfNotFound("TYPE1", 0.05, true, false, true, Arrays.asList(eurCurrency));
        createPaymentTypeIfNotFound("TYPE2", 0.1, false, false, true, Arrays.asList(usdCurrency));
        createPaymentTypeIfNotFound("TYPE3", 0.15, false, true, false, Arrays.asList(eurCurrency, usdCurrency));
	}

    private PaymentStatusModel createPaymentStatusIfNotFound(final String name) {
        PaymentStatusModel paymentStatusModel = paymentStatusService.findByName(name);
        if (paymentStatusModel == null) {
            paymentStatusModel = paymentStatusService.createPaymentStatus(name);
        }
        return paymentStatusModel;
    }
    
    private PaymentTypeModel createPaymentTypeIfNotFound(final String name, final double feeCoefficient, final boolean detailsMandatory, final boolean creditorBicMandatory, final boolean notified, final List<CurrencyModel> currencies) {
        PaymentTypeModel paymentTypeModel = paymentTypeService.findByName(name);
        if (paymentTypeModel == null) {
            paymentTypeModel = paymentTypeService.createPaymentType(new PaymentTypeModel(name, feeCoefficient, detailsMandatory, creditorBicMandatory, notified, currencies));
        }
        return paymentTypeModel;
    }
    
    private ClientModel createClientIfNotFound(final String name, final String userName, final String password, final String email, final String phone, final String language) {
    	ClientModel clientModel = clientService.findByUsername(userName);
        if (clientModel == null) {
            try {
				clientModel = clientService.createClient(new RegistrationModel(name, userName, password, email, phone, language, true));
			}
            catch (UserEmailExistsException e) {
				e.printStackTrace();
			}
        }
        return clientModel;
    }
    
    private CurrencyModel createCurrencyIfNotFound(final String code) {
    	CurrencyModel currencyModel = currencyService.findByCode(code);
        if (currencyModel == null) {
        	Currency currency = Currency.getInstance(code);
            currencyModel = currencyService.createCurrency(new CurrencyModel(currency.getCurrencyCode(), currency.getDisplayName()));
        }
        return currencyModel;
    }
}
