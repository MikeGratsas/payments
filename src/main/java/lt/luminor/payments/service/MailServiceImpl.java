package lt.luminor.payments.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lt.luminor.payments.form.MessageModel;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Override
	public int send(MessageModel messageModel) {
    	int result = -1;
		try {
			JSONObject messagesJsonObject = new JSONObject()
					.put("Messages", new JSONArray()
			           .put(new JSONObject()
			               .put("From", new JSONObject()
			                   .put("Email", messageModel.getFromAddress())
			                   .put("Name", messageModel.getFromName()))
			               .put("To", new JSONArray()
			                   .put(new JSONObject()
			                       .put("Email", messageModel.getToAddress())
			                       .put("Name", messageModel.getToName())))
			               .put("Subject", messageModel.getSubject())
			               .put("TextPart", messageModel.getBody())
			               )
			           );
	        final HttpHeaders headers = new HttpHeaders();
	        headers.setBasicAuth(username, password);
	        headers.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<String> request = new HttpEntity<>(messagesJsonObject.toString(), headers);
			final RestTemplate template = new RestTemplate();
	    	ResponseEntity<String> response = template.postForEntity("https://api.mailjet.com/v3.1/send", request, String.class);
	    	result = response.getStatusCode().value();
	    	if (response.hasBody())
	    		LOGGER.info(response.getBody());
		}
		catch (JSONException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		catch (RestClientException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		return result;
    }
}
