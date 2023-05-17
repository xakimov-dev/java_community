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

    public void validateSubArticle(UUID id) {
        validateUUID(id, "subArticle");
        if (!subArticleRepository.existsById(id)) {
            throw new RecordNotFoundException(String.format("sub article not found for id %s", id));
        }
    }

    public void validateSubArticleExistByParentId(String name, UUID parentId, UUID id) {
        boolean exists = subArticleRepository.existsByNameAndParentSubArticleId(name, parentId);
        if (exists && (id == null || subArticleRepository.existsByNameAndParentSubArticleIdAndIdNot(name, parentId, id))) {
            throw new AlreadyExistsException(String.format("Sub article with name %s already exists in", name));
        }
    }

    public void validateSubArticleExistByArticleId(String name, UUID articleId, UUID id) {
        boolean exists = subArticleRepository.existsByNameAndArticleId(name, articleId);
        if (exists && (id == null || subArticleRepository.existsByNameAndArticleIdAndIdNot(name, articleId, id))) {
            throw new AlreadyExistsException(String.format("Sub article with name %s already exists in this Article", name));
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
        if (categoryRepository.existsByNameAndParentIdAndIdNot(name, parentId, categoryId)) {
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }
}
