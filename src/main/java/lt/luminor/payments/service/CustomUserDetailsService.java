package lt.luminor.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.luminor.payments.config.CustomUserDetails;
import lt.luminor.payments.entity.Client;
import lt.luminor.payments.repository.ClientRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Client> optionalClient = clientRepository.findByUsername(username);

        if (!optionalClient.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        Client client = optionalClient.get();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(client.getId(), client.getUsername(), client.getPassword(), client.isEnabled(), client.isLocked(), authorities);
        //return new User(userAccount.getUsername(), userAccount.getPassword(), userAccount.isEnabled(), true, true, true, authorities);
    }
}