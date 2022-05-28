package be.technifutur.auth.business.service;

import be.technifutur.auth.business.mapper.UserAccountMapper;
import be.technifutur.auth.model.dto.SimpleUserAccountDTO;
import be.technifutur.auth.model.entity.UserAccount;
import be.technifutur.auth.model.form.SignUpForm;
import be.technifutur.auth.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findUserAccountByEmail(email);
    }

    public SimpleUserAccountDTO getUserRoles(String email) {
        return userAccountMapper.entityToDTO(findUserAccountByEmail(email));
    }

    public void toggleUserAccount(UUID userRef) {
        UserAccount account = findUserAccountByRef(userRef);
        account.setAccountActive(!account.isAccountActive()); // toggle 'active' boolean
        userAccountRepository.save(account);
    }

    private UserAccount findUserAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("There is no user with the email " + email)
                );
    }

    private UserAccount findUserAccountByRef(UUID userRef) {
        return userAccountRepository.findByRef(userRef)
                .orElseThrow(
                        () -> new UsernameNotFoundException("There is no user with ref " + userRef)
                );
    }

    public void addUserAccount(SignUpForm form) {
        UserAccount userAccount = userAccountMapper.formToEntity(form);
        userAccountRepository.save(userAccount);
        // TODO: Send a message to RabbitMQ queue to ask for user creation inside of user-service (async)
    }
}
