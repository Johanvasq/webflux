package co.com.ias.webflux.service;

import co.com.ias.webflux.model.Topic;
import co.com.ias.webflux.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TopicHandler {

    private final TopicRepository topicRepository;

    public TopicHandler(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Mono<ServerResponse> createTopic(ServerRequest request) {
        return request
                .bodyToMono(Topic.class)
                .flatMap(topicRepository::save)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response))
                .doOnNext(System.out::println);
    }

    public Mono<ServerResponse> updateTopic(ServerRequest request){
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(topicRepository
                        .findById(Integer.valueOf(request.pathVariable("id")))
                        .flatMap(topic ->
                             request
                                    .bodyToMono(Topic.class)
                                    .flatMap(topicRequest ->
                                            topicRepository
                                                    .save(Topic
                                                    .builder()
                                                    .id(topic.getId())
                                                    .name(topicRequest.getName())
                                                    .description(topicRequest.getDescription())
                                                    .build())

                                    )
                        ), Topic.class)
                .switchIfEmpty(
                        ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Not Found")
                );
    }

    public Mono<ServerResponse> readTopic(ServerRequest request){
        return topicRepository
                .findById(Integer.valueOf(request.pathVariable("id")))
                .flatMap(topic ->
                    ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(topic)
                            .switchIfEmpty(
                                    ServerResponse
                                            .status(HttpStatus.NO_CONTENT)
                                            .bodyValue("Not found")

                            )
                );
    }
    public Mono<ServerResponse> readAllTopic(ServerRequest reques){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(topicRepository.findAll(), Topic.class)
                .switchIfEmpty(
                        ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .bodyValue("Not found").cache()
                );
    }
    public Mono<ServerResponse> deleteTopic(ServerRequest request){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(topicRepository
                        .deleteById(Integer.valueOf(request.pathVariable("id"))), Topic.class)
                .onErrorResume(exception -> ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .bodyValue(exception.getMessage()));
    }

}
