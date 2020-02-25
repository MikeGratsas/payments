package lt.luminor.payments.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lt.luminor.payments.entity.Client;
import lt.luminor.payments.exceptions.UserEmailExistsException;
import lt.luminor.payments.form.ClientModel;
import lt.luminor.payments.form.RegistrationModel;
import lt.luminor.payments.repository.ClientRepository;

@Service
@Transactional
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientModel createClient(RegistrationModel registrationModel) throws UserEmailExistsException {
        final String email = registrationModel.getEmail();
        Optional<Client> userOptional = clientRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserEmailExistsException(email);
        }
        Client clientEntity = new Client();
        clientEntity.setName(registrationModel.getName());
        clientEntity.setUsername(registrationModel.getUsername());
        clientEntity.setPassword(passwordEncoder.encode(registrationModel.getPassword()));
        clientEntity.setEmail(email);
        clientEntity.setPhone(registrationModel.getPhone());
        clientEntity.setLanguage(registrationModel.getLanguage());
        clientEntity.setEnabled(registrationModel.isEnabled());
        Client c = clientRepository.save(clientEntity);
        return assembleClientModel(c);
    }

    public ClientModel findUser(Long id) {
        ClientModel userModel = null;
        Optional<Client> userEntity = clientRepository.findById(id);
        if (userEntity.isPresent()) {
            Client u = userEntity.get();
            userModel = assembleClientModel(u);
        }
        return userModel;
    }

    public ClientModel findByUsername(String name) {
        ClientModel userModel = null;
        Optional<Client> userEntity = clientRepository.findByUsername(name);
        if (userEntity.isPresent()) {
            Client u = userEntity.get();
            userModel = assembleClientModel(u);
        }
        return userModel;
    }

    public ClientModel findByEmail(String email) {
        ClientModel userModel = null;
        Optional<Client> userEntity = clientRepository.findByEmail(email);
        if (userEntity.isPresent()) {
            Client u = userEntity.get();
            userModel = assembleClientModel(u);
        }
        return userModel;
    }

    public void deleteUsers(Long[] ids) {
        for (Long id: ids) {
            clientRepository.deleteById(id);
        }
    }

    private static ClientModel assembleClientModel(Client clientEntity) {
        return new ClientModel(clientEntity.getId(), clientEntity.getName(), clientEntity.getUsername(), clientEntity.getEmail(), clientEntity.getPhone(), clientEntity.getLanguage(), clientEntity.isEnabled(), clientEntity.isLocked(), clientEntity.getCreated(), clientEntity.getLastUpdated());
    }
}
