package io.github.linteddy.taxcalculator.controller;

import io.github.linteddy.taxcalculator.domain.IncomeTaxResult;
import io.github.linteddy.taxcalculator.domain.Period;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaxCalculatorIntegrationTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Should calculate 2021 monthly income tax")
    void monthlyIncomeTaxFor2021() throws Exception {
        webClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/api/tax/income-tax")
                                .queryParam("year", 2021)
                                .queryParam("age", 70)
                                .queryParam("income", BigDecimal.valueOf(30000))
                                .queryParam("period", Period.MONTHLY)
                                .queryParam("medicalAidMembers", 3)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(IncomeTaxResult.class)
                .value(incomeTaxResult -> {
                    assertThat(incomeTaxResult.getPayAsYouEarnBeforeTaxCredit()).isEqualTo("4657.58");
                    assertThat(incomeTaxResult.getTaxCredits()).isEqualTo("853.00");
                    assertThat(incomeTaxResult.getPayAsYouEarnAfterTaxCredit()).isEqualTo("3804.58");
                    assertThat(incomeTaxResult.getNetCash()).isEqualTo("26195.42");
                });
    }

    @Test
    @DisplayName("Should calculate 2021 annual income tax")
    void annualIncomeTaxFor2021() throws Exception {
        webClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/api/tax/income-tax")
                                .queryParam("year", 2021)
                                .queryParam("age", 70)
                                .queryParam("income", BigDecimal.valueOf(360_000))
                                .queryParam("period", Period.ANNUALLY)
                                .queryParam("medicalAidMembers", 3)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(IncomeTaxResult.class)
                .value(incomeTaxResult -> {
                    assertThat(incomeTaxResult.getPayAsYouEarnBeforeTaxCredit()).isEqualTo("55891.00");
                    assertThat(incomeTaxResult.getTaxCredits()).isEqualTo("10236");
                    assertThat(incomeTaxResult.getPayAsYouEarnAfterTaxCredit()).isEqualTo("45655.00");
                    assertThat(incomeTaxResult.getNetCash()).isEqualTo("314345.00");
                });
    }

    @Test
    @DisplayName("Should calculate 2020 annual income tax")
    void annualIncomeTaxFor2020() throws Exception {
        webClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/api/tax/income-tax")
                                .queryParam("year", 2020)
                                .queryParam("age", 70)
                                .queryParam("income", BigDecimal.valueOf(360_000))
                                .queryParam("period", Period.ANNUALLY)
                                .queryParam("medicalAidMembers", 3)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(IncomeTaxResult.class)
                .value(incomeTaxResult -> {
                    assertThat(incomeTaxResult.getPayAsYouEarnBeforeTaxCredit()).isEqualTo("58625.50");
                    assertThat(incomeTaxResult.getTaxCredits()).isEqualTo("9948");
                    assertThat(incomeTaxResult.getPayAsYouEarnAfterTaxCredit()).isEqualTo("48677.50");
                    assertThat(incomeTaxResult.getNetCash()).isEqualTo("311322.50");
                });
    }


}
