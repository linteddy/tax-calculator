package io.github.linteddy.taxcalculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaxRebateTest {

    private final TaxRebate taxRebate = DomainTestUtil.createTaxRebate();

    @Test
    @DisplayName("calculate tax rebates for primary tax payer")
    void calculateTaxRebatesForPrimaryTaxPayer() {
        final var taxRebates = taxRebate.calculateTaxRebates(60);
        assertThat(taxRebates).hasToString("14958");
    }

    @Test
    @DisplayName("calculate tax rebates for secondary tax payer")
    void calculateTaxRebatesForSecondaryTaxPayer() {
        final var taxRebates = taxRebate.calculateTaxRebates(65);
        assertThat(taxRebates).hasToString("23157");
    }

    @Test
    @DisplayName("calculate tax rebates for tertiary tax payer")
    void calculateTaxRebatesForTertiaryTaxPayer() {
        final var taxRebates = taxRebate.calculateTaxRebates(75);
        assertThat(taxRebates).hasToString("25893");
    }

}
