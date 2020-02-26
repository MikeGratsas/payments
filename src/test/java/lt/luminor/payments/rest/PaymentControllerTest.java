package lt.luminor.payments.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lt.luminor.payments.form.PaymentFeeModel;
import lt.luminor.payments.form.PaymentModel;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {

    @Autowired
    private TestRestTemplate template;

    private final String username = "tester";
    private final String password = "secret";
    MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();

    @BeforeEach
	public void setUp() throws Exception {
    	Assertions.assertNotNull(template);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
        HttpEntity<Object> payment1Entity = getHttpEntity("{\"paymentType\": \"TYPE1\", \"currency\": \"EUR\", \"amount\": 50.15, \"debtorIban\": \"LT123456789\", \"creditorIban\": \"LT123456780\", \"details\": \"TYPE1 payment\" }", headerMap);
        ResponseEntity<PaymentModel> resultAsset = template.withBasicAuth(username, password).postForEntity("/api/payments", payment1Entity, PaymentModel.class);
        Assertions.assertEquals(HttpStatus.CREATED, resultAsset.getStatusCode());
        PaymentModel payment1 = resultAsset.getBody();
        Assertions.assertNotNull(payment1);
        final Long payment1Id = payment1.getId();
        Assertions.assertNotNull(payment1Id);
        Assertions.assertNotNull(payment1.getLastUpdated());

        resultAsset = template.withBasicAuth(username, password).getForEntity("/api/payments/{paymentId}", PaymentModel.class, payment1Id);
        Assertions.assertEquals(HttpStatus.OK, resultAsset.getStatusCode());
        Assertions.assertEquals(payment1Id, resultAsset.getBody().getId());

        HttpEntity<Object> payment2Entity = getHttpEntity("{\"paymentType\": \"TYPE2\", \"currency\": \"USD\", \"amount\": 500.45, \"debtorIban\": \"LT023456789\", \"creditorIban\": \"LT023456780\", \"details\": \"TYPE2 payment\" }", headerMap);
        resultAsset = template.withBasicAuth(username, password).postForEntity("/api/payments", payment2Entity, PaymentModel.class);
        Assertions.assertEquals(HttpStatus.CREATED, resultAsset.getStatusCode());
        PaymentModel payment2 = resultAsset.getBody();
        Assertions.assertNotNull(payment2);
        final Long payment2Id = payment2.getId();
        Assertions.assertNotNull(payment2Id);
        Assertions.assertNotNull(payment2.getLastUpdated());
        
        HttpEntity<Object> payment3Entity = getHttpEntity("{\"paymentType\": \"TYPE3\", \"currency\": \"USD\", \"amount\": 200.65, \"debtorIban\": \"LT023456789\", \"creditorIban\": \"LT023456784\", \"creditorBic\": \"NDEAFIHH\" }", headerMap);
        resultAsset = template.withBasicAuth(username, password).postForEntity("/api/payments", payment3Entity, PaymentModel.class);
        Assertions.assertEquals(HttpStatus.CREATED, resultAsset.getStatusCode());
        PaymentModel payment3 = resultAsset.getBody();
        Assertions.assertNotNull(payment3);
        final Long payment3Id = payment3.getId();
        Assertions.assertNotNull(payment3Id);
        Assertions.assertNotNull(payment3.getLastUpdated());

        ResponseEntity<PaymentFeeModel> resultFeeAsset = template.withBasicAuth(username, password).getForEntity("/api/payments/{paymentId}/cancel", PaymentFeeModel.class, payment2Id);
        Assertions.assertEquals(HttpStatus.OK, resultFeeAsset.getStatusCode());
        PaymentFeeModel paymentFee = resultFeeAsset.getBody();
		Assertions.assertEquals(payment2Id, paymentFee.getId());
		Assertions.assertNotNull(paymentFee.getFee());

        resultFeeAsset = template.withBasicAuth(username, password).getForEntity("/api/payments/{paymentId}/fee", PaymentFeeModel.class, payment2Id);
        Assertions.assertEquals(HttpStatus.OK, resultFeeAsset.getStatusCode());
        paymentFee = resultFeeAsset.getBody();
		Assertions.assertEquals(payment2Id, paymentFee.getId());
		Assertions.assertNotNull(paymentFee.getFee());
		
        ResponseEntity<PaymentModel[]> resultList = template.withBasicAuth(username, password).getForEntity("/api/payments", PaymentModel[].class);
        Assertions.assertEquals(HttpStatus.OK, resultList.getStatusCode());
        Assertions.assertTrue(resultList.getBody().length > 0);

        resultList = template.withBasicAuth(username, password).getForEntity("/api/payments/lessThanAmount?currency=USD&amp;amount=250", PaymentModel[].class);
        Assertions.assertEquals(HttpStatus.OK, resultList.getStatusCode());
        Assertions.assertTrue(resultList.getBody().length > 0);
	}

    private HttpEntity<Object> getHttpEntity(Object body, MultiValueMap<String, String> headerMap)
    {
        final HttpHeaders headers = new HttpHeaders(headerMap);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
}
