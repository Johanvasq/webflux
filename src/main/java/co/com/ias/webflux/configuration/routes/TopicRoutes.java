package co.com.ias.webflux.configuration.routes;

import co.com.ias.webflux.service.TopicHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class TopicRoutes {

    @Value("{PATH_BASE}") //El valor que tenga nuestra variable de entorno se la paso a la variable
    private String pathBase;

    @Bean
    public RouterFunction<ServerResponse> topicFunctionalEndPoints(TopicHandler handler) {
        return RouterFunctions.route(
                POST(pathBase.concat("/topic")).and(accept(MediaType.APPLICATION_JSON)), handler::createTopic)
                .andRoute(GET(pathBase.concat("/topic/{id}")).and(accept(MediaType.APPLICATION_JSON)), handler::readTopic)
                .andRoute(GET(pathBase.concat("/topic")).and(accept(MediaType.APPLICATION_JSON)), handler::readAllTopic)
                .andRoute(PUT(pathBase.concat("/topic/{id}")).and(accept(MediaType.APPLICATION_JSON)), handler::updateTopic)
                .andRoute(DELETE(pathBase.concat("/topic/{id}")).and(accept(MediaType.APPLICATION_JSON)), handler::deleteTopic)
                ;
    }
}
