package com.demkiv.pet.project.service.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PetProjectTaskServiceTest {
    @Mock
    private WebClient webClient;
    @InjectMocks
    PetProjectTaskService service;

    private final String PART_PATH_API = "api/ping";
    private final String PONG_RESPONSE = "pong";

//    @Test
    public void whenTestApiHealsCheck() {
       // when
        WebClient.RequestHeadersUriSpec headersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        Mono<String> monoMock = mock(Mono.class);
        when(webClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri(PART_PATH_API)).thenReturn(headersUriSpec);
        when(headersUriSpec.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(String.class)).thenReturn(monoMock);
        when(monoMock.block()).thenReturn(PONG_RESPONSE);

       // given
        boolean result = service.healthCheckApi();

       // then
        then(webClient).should(times(1)).get();
        then(headersUriSpec).should(times(1)).uri(PART_PATH_API);
        then(headersUriSpec).should(times(1)).retrieve();
        then(responseSpecMock).should(times(1)).bodyToMono(String.class);
        then(monoMock).should(times(1)).block();

        assertThat(result).isTrue();
    }
}
