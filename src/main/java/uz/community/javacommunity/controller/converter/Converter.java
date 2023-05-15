package uz.community.javacommunity.controller.converter;

import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;
public interface Converter <Entity, Request, Response>{
    Entity convertRequestToEntity(Request request, String name, UUID id);
    Entity convertRequestToEntity(Request request, String name);
    Response convertEntityToResponse(Entity entity);
    List<Response> convertEntitiesToResponse(List<Entity> entities);
}
