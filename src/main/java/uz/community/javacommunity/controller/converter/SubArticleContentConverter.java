//package uz.community.javacommunity.controller.converter;
//
//import lombok.experimental.UtilityClass;
//import uz.community.javacommunity.controller.domain.SubArticleContent;
//import uz.community.javacommunity.controller.dto.SubArticleContentCreateRequest;
//import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
//
//import java.time.Instant;
//import java.util.List;
//import java.util.UUID;
//@UtilityClass
//public class SubArticleContentConverter {
//    public SubArticleContent convertToEntity(SubArticleContentCreateRequest subArticleContentCreateRequest) {
//        Instant now = Instant.now();
//        return SubArticleContent.builder()
//                .content(subArticleContentCreateRequest.getContent())
//                .articleId(subArticleContentCreateRequest.getArticleId())
//                .categoryId(subArticleContentCreateRequest.getCategoryId())
//                .isParagraph(subArticleContentCreateRequest.isParagraph())
//                .modifiedBy(name)
//                .modifiedDate(now)
//                .subArticleContentKey(SubArticleContent.SubArticleContentKey.of(id, subArticleContentCreateRequest.getSubArticleId()))
//                .build();
//
//    }
//
//    public SubArticleContent convertRequestToEntity(SubArticleContentCreateRequest subArticleContentCreateRequest, String name) {
//        Instant now = Instant.now();
//        return SubArticleContent.builder()
//                .content(subArticleContentCreateRequest.getContent())
//                .articleId(subArticleContentCreateRequest.getArticleId())
//                .categoryId(subArticleContentCreateRequest.getCategoryId())
//                .isParagraph(subArticleContentCreateRequest.isParagraph())
//                .createdBy(name)
//                .modifiedBy(name)
//                .createdDate(now)
//                .modifiedDate(now)
//                .subArticleContentKey(SubArticleContent.SubArticleContentKey.of(UUID.randomUUID(), subArticleContentCreateRequest.getSubArticleId()))
//                .build();
//    }
//
//    @Override
//    public SubArticleContentResponse convertEntityToResponse(SubArticleContent subArticleContent) {
//        return SubArticleContentResponse.builder()
//                .id(subArticleContent.getSubArticleContentKey().getId())
//                .categoryId(subArticleContent.getCategoryId())
//                .articleId(subArticleContent.getArticleId())
//                .subArticleId(subArticleContent.getSubArticleContentKey().getSubArticleId())
//                .content(subArticleContent.getContent())
//                .isParagraph(subArticleContent.isParagraph())
//                .createdBy(subArticleContent.getCreatedBy())
//                .modifiedBy(subArticleContent.getModifiedBy())
//                .createdDate(subArticleContent.getCreatedDate())
//                .modifiedDate(subArticleContent.getModifiedDate())
//                .build();
//    }
//
//    @Override
//    public List<SubArticleContentResponse> convertEntitiesToResponse(List<SubArticleContent> subArticleContents) {
//        return subArticleContents.stream().map(this::convertEntityToResponse).toList();
//    }
//}
