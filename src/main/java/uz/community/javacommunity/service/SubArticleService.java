package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;
import uz.community.javacommunity.controller.repository.SubArticleRepository;

@Service
@RequiredArgsConstructor
public class SubArticleService {

    private final SubArticleRepository repository;

    public SubArticleResponse create(SubArticleRequest dto) {
        if (repository.existsByName(dto.name())) {
            throw new AlreadyExistsException("SubArticle name already exist");
        }
        SubArticle savedSubArticle = repository.save(SubArticle.of(dto));
        return SubArticleResponse.of(savedSubArticle);
    }

}
