package io.github.linteddy.taxcalculator.controller;

import io.github.linteddy.taxcalculator.domain.IncomeTax;
import io.github.linteddy.taxcalculator.domain.Period;
import io.github.linteddy.taxcalculator.service.IncomeTaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebFluxTest(TaxCalculatorController.class)
class TaxCalculatorControllerTest {

    @MockBean
    private IncomeTaxService incomeTaxService;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext applicationContext, RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("calculate income tax")
    @Test
    void calculateIncomeTax() {
        var incomeTax = IncomeTax.builder()
                .monthlyPAYEBeforeTaxCredit(BigDecimal.valueOf(4657.58))
                .annuallyPAYEBeforeTaxCredit(BigDecimal.valueOf(4657.58).multiply(BigDecimal.valueOf(12)))
                .paye(BigDecimal.valueOf(4657.58))
                .netCash(BigDecimal.valueOf(30000).subtract(BigDecimal.valueOf(4657.58)))
                .taxCredits(BigDecimal.ZERO)
                .build();
        when(incomeTaxService.calculateIncomeTax(anyInt(), anyInt(), any(), any(), anyInt())).thenReturn(Mono.just(incomeTax));
        webTestClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/api/tax/income-tax")
                                .queryParam("year",2021)
                                .queryParam("age",70)
                                .queryParam("income", BigDecimal.valueOf(30000))
                                .queryParam("period", Period.MONTHLY)
                                .queryParam("medicalAidMembers", 0)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus()
                .isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
                .consumeWith(
                        document("get-income-tax", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("monthlyPAYEBeforeTaxCredit").description("Calculated monthly PAYE before Tax Credits"),
                                        fieldWithPath("annuallyPAYEBeforeTaxCredit").description("Calculated annually PAYE before Tax Credits"),
                                        fieldWithPath("taxCredits").description("Medical Aid Tax Credits"),
                                        fieldWithPath("paye").description("Pay as you earn due after Tax credits"),
                                        fieldWithPath("netCash").description("Net cash pay after PAYE due")
                                )
                        )
                );
    }
}
