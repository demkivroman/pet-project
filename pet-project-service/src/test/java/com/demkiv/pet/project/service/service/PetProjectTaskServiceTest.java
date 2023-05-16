package com.demkiv.pet.project.service.service;



import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PetProjectTaskServiceTest {
    @Value("${pet.project.practice.api.url.test}")
    private String host;
    @Value("${server.port.impl.test}")
    private String port;
//    private WireMockServer wireMockServer = new WireMockServer();
    @Mock
    private WebClient webClient;
    @InjectMocks
    PetProjectTaskService service;

    private final String PART_PATH_API = "api/ping";
    private final String PONG_RESPONSE = "pong";

    @Autowired
    public PetProjectTaskServiceTest(PetProjectTaskService service) {
        this.service = service;
    }

    @Test
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

//    @Test
//    public void givenProgrammaticallyManagedServer_whenUsingSimpleStubbing_thenCorrect() throws IOException {
//        wireMockServer.start();
//
//        configureFor("localhost/api/", 8082);
//        stubFor(get(urlEqualTo("ping/")).willReturn(aResponse().withBody("pong")));
//
//        HttpGet request = new HttpGet("http://localhost:8082/api/ping");
//        boolean result = service.healthCheckApi();
//
//
//        wireMockServer.stop();
//    }
}
