package uz.community.javacommunity.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.AuthenticationException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Login;
import uz.community.javacommunity.controller.repository.*;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommonSchemaValidator {
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final SubArticleRepository subArticleRepository;
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateCategory(UUID id) {
        validateUUID(id, "category");
        if (!categoryRepository.existsById(id)) {
            throw new RecordNotFoundException(String.format("category not found for id %s", id));
        }
    }

    public void validateArticle(UUID id) {
        validateUUID(id, "article");
        if (!articleRepository.existsById(id)) {
            throw new RecordNotFoundException(String.format("article not found for id %s", id));
        }
    }

    public void validateSubArticleExist(String name) {
        if (subArticleRepository.existsByName(name)) {
            throw new AlreadyExistsException(String.format("sub article with name %s already exists", name));
        }
    }

    public void validateSubArticle(UUID id) {
        validateUUID(id, "subArticle");
        if (!subArticleRepository.existsById(id)) {
            throw new RecordNotFoundException(String.format("sub article not found for id %s", id));
        }
    }

    public void validateUsernameExist(String username) {
        if (loginRepository.existsById(username)) {
            throw new AlreadyExistsException(String.format("user with username %s already exists", username));
        }
    }

    public void validateUUID(UUID id, String propertyName) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(String.format("%s id cannot be null value", propertyName));
        }
    }

    public void validatePassword(Login login, String password) {
        if (!passwordEncoder.matches(password, login.getPassword())) {
            throw new AuthenticationException();
        }
    }

    public void validateCategoryExist(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }

    public void validateCategoryExistByNameAndParentID(String name, UUID parentId, UUID categoryId) {
        if (categoryRepository.existsByNameAndParentIdAAndIdNot(name, parentId, categoryId)) {
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }
}
