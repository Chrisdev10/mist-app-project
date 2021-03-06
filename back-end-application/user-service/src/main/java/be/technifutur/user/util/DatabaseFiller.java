package be.technifutur.user.util;

import be.technifutur.user.model.entity.User;
import be.technifutur.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Configuration
@AllArgsConstructor
public class DatabaseFiller implements InitializingBean {

    private final UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        User user = User.builder()
                .ref(UUID.fromString("063f2794-a853-432d-a399-3b75df98f83d"))
                .firstName("lionel")
                .lastName("delsupexhe")
                .birthDate(LocalDate.of(1997, 4, 29))
                .phoneNumber("0412345678")
                .wallet(0)
                .loyaltyPoints(0)
                .wishlist(new ArrayList<>())
                .billingAddresses(new ArrayList<>())
                .build();
        userRepository.save(user);
    }
}
