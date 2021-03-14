package io.github.linteddy.taxcalculator.controller;

import io.github.linteddy.taxcalculator.domain.IncomeTaxResult;
import io.github.linteddy.taxcalculator.domain.Period;
import io.github.linteddy.taxcalculator.service.TaxCalculatorService;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebFluxTest(TaxCalculatorController.class)
class TaxCalculatorControllerTest {

    @MockBean
    private TaxCalculatorService taxCalculatorService;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext applicationContext, RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("https://linteddy-tax-calculator.herokuapp.com")
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("calculate income tax")
    @Test
    void calculateIncomeTax() {
        var incomeTax = IncomeTaxResult.builder()
                .payAsYouEarnBeforeTaxCredit(BigDecimal.valueOf(4657.58))
                .netCash(BigDecimal.valueOf(30000).subtract(BigDecimal.valueOf(4657.58)))
                .taxCredits(BigDecimal.ZERO)
                .payAsYouEarnAfterTaxCredit(BigDecimal.valueOf(4657.58))
                .build();
        when(taxCalculatorService.calculateIncomeTax(anyInt(), anyInt(), any(), any(), anyInt())).thenReturn(Mono.just(incomeTax));
        webTestClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/api/tax/income-tax")
                                .queryParam("year", 2021)
                                .queryParam("age", 70)
                                .queryParam("income", BigDecimal.valueOf(30000))
                                .queryParam("period", Period.MONTHLY)
                                .queryParam("medicalAidMembers", 0)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus()
                .isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
                .consumeWith(
                        document("get-income-tax", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("year").description("Tax year"),
                                        parameterWithName("age").description("Tax age of the tax payer"),
                                        parameterWithName("income").description("Total taxable monthly or annual income." +
                                                " See the period request parameter"),
                                        parameterWithName("period").description("How often the tax payer gets the total taxable income." +
                                                "Values : MONTHLY, ANNUALLY"),
                                        parameterWithName("medicalAidMembers").description("The number of medical aid members plus main.")

                                ),
                                responseFields(
                                        fieldWithPath("payAsYouEarnBeforeTaxCredit")
                                                .description("Calculated pay as you earn before Tax Credits." +
                                                        " Annually or monthly depending on the request's period value"),
                                        fieldWithPath("payAsYouEarnAfterTaxCredit")
                                                .description("Calculated pay as you earn after Tax Credits." +
                                                        " Annually or monthly depending on the request's period value"),
                                        fieldWithPath("taxCredits").description("Medical Aid Tax Credits"),
                                        fieldWithPath("netCash").description("The take home cash after pay as you earn due")
                                )
                        )
                );
    }
}
