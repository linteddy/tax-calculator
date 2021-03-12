package io.github.linteddy.taxcalculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TaxThresholdTest {
    private final TaxThreshold taxThreshold = DomainTestUtil.createTaxThreshold();

    @Test
    @DisplayName("calculate tax rebates for primary tax payer")
    void calculateTaxRebatesForPrimaryTaxPayer() {
        final BigDecimal taxThresholdAmount = taxThreshold.getTaxThreshold(60);
        assertThat(taxThresholdAmount).hasToString("83100");
    }

    @Test
    @DisplayName("calculate tax rebates for secondary tax payer")
    void calculateTaxRebatesForSecondaryTaxPayer() {
        final BigDecimal taxThresholdAmount = taxThreshold.getTaxThreshold(65);
        assertThat(taxThresholdAmount).hasToString("128650");
    }

    @Test
    @DisplayName("calculate tax rebates for tertiary tax payer")
    void calculateTaxRebatesForTertiaryTaxPayer() {
        final BigDecimal taxThresholdAmount = taxThreshold.getTaxThreshold(75);
        assertThat(taxThresholdAmount).hasToString("143850");
    }
}
