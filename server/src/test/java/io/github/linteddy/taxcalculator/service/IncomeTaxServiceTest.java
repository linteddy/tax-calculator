package io.github.linteddy.taxcalculator.service;


import io.github.linteddy.taxcalculator.domain.DomainTestUtil;
import io.github.linteddy.taxcalculator.domain.IncomeTaxResult;
import io.github.linteddy.taxcalculator.domain.Period;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(TaxCalculatorService.class)
class IncomeTaxServiceTest {
    @MockBean
    private TaxTableService taxTableService;

    @Autowired
    private TaxCalculatorService taxCalculatorService;

    @Test
    @DisplayName("should return annually calculated income tax when period in annual")
    void calculateAnnualIncomeTax() {
        when(taxTableService.getTaxTable(2021)).thenReturn(DomainTestUtil.createTaxTable());
        final Mono<IncomeTaxResult> incomeTaxResultMono = taxCalculatorService.calculateIncomeTax(2021, 70, BigDecimal.valueOf(360_000), Period.ANNUALLY, 3);
        StepVerifier.create(incomeTaxResultMono)
                .assertNext(incomeTaxResult -> {
                    assertThat(incomeTaxResult.getTaxCredits()).isEqualTo("10236");
                    assertThat(incomeTaxResult.getPayAsYouEarnBeforeTaxCredit()).isEqualTo("55891.00");
                    assertThat(incomeTaxResult.getPayAsYouEarnAfterTaxCredit()).isEqualTo("45655.00");
                    assertThat(incomeTaxResult.getNetCash()).isEqualTo("314345.00");
                })
                .expectComplete()
                .verify();

    }

    @Test
    @DisplayName("should return monthly calculated income tax when period in annual")
    void calculateMonthlyIncomeTax() {
        when(taxTableService.getTaxTable(2021)).thenReturn(DomainTestUtil.createTaxTable());
        final Mono<IncomeTaxResult> incomeTaxResultMono = taxCalculatorService.calculateIncomeTax(2021, 70, BigDecimal.valueOf(30_000), Period.MONTHLY, 3);
        StepVerifier.create(incomeTaxResultMono)
                .assertNext(incomeTaxResult -> {
                    assertThat(incomeTaxResult.getTaxCredits()).isEqualTo("853.00");
                    assertThat(incomeTaxResult.getPayAsYouEarnBeforeTaxCredit()).isEqualTo("4657.58");
                    assertThat(incomeTaxResult.getPayAsYouEarnAfterTaxCredit()).isEqualTo("3804.58");
                    assertThat(incomeTaxResult.getNetCash()).isEqualTo("26195.42");
                })
                .expectComplete()
                .verify();

    }
}
