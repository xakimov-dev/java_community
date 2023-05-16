package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.AuthenticationException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Login;
import uz.community.javacommunity.controller.domain.User;
import uz.community.javacommunity.controller.dto.UserRequest;
import uz.community.javacommunity.controller.repository.LoginRepository;
import uz.community.javacommunity.controller.repository.UserRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CassandraTemplate cassandraTemplate;
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CommonSchemaValidator commonSchemaValidator;

    public User create(User user, Login login) {
        Optional<User> optionalUser = userRepository.findById(user.getUsername());
        if (optionalUser.isPresent()){
            throw new AlreadyExistsException("");
        }

        String hashedPassword = passwordEncoder.encode(login.getPassword());
        login.setPassword(hashedPassword);

        Instant now = Instant.now();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        batchOps.insert(login, user);
        batchOps.execute();
        return user;
    }

    public String login(String username, String password) {
        Login login = loginRepository.findById(username).orElseThrow(AuthenticationException::new);
        validatePassword(login, password);
        User user = userRepository.findById(username).orElseThrow(()-> new RecordNotFoundException("User with username [%s] not found ".formatted(username)));
        return jwtService.generateToken(user);
    }

    public User findByUserName(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("Can not found user with username: " + username));
    }

    private void validatePassword(Login login, String password) {
        if (!passwordEncoder.matches(password, login.getPassword())) {
            throw new AuthenticationException();
        }
    }
}
