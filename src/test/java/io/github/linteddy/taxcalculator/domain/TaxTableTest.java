package io.github.linteddy.taxcalculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TaxTableTest {
    private final TaxTable taxTable = DomainTestUtil.createTaxTable();

    @Test
    @DisplayName("calculate tax for primary tax payer without medical aid credits")
    void calculateTaxForPrimaryTaxPayerWithoutMedicalAidCredits() {
        final BigDecimal incomeTax = taxTable.calculateAnnualIncomeTax(64, BigDecimal.valueOf(360_000));
        assertThat(incomeTax).hasToString("64090.00");
    }

    @Test
    @DisplayName("calculate tax for secondary tax payer without medical aid credits")
    void calculateTaxForSecondaryTaxPayerWithoutMedicalAidCredits() {
        final BigDecimal incomeTax = taxTable.calculateAnnualIncomeTax(70, BigDecimal.valueOf(360_000));
        assertThat(incomeTax).hasToString("55891.00");
    }

    @Test
    @DisplayName("calculate tax for tertiary tax payer without medical aid credits")
    void calculateTaxForTertiaryTaxPayerWithoutMedicalAidCredits() {
        final BigDecimal incomeTax = taxTable.calculateAnnualIncomeTax(75, BigDecimal.valueOf(360_000));
        assertThat(incomeTax).hasToString("53155.00");
    }

    @Test
    @DisplayName("calculate tax for primary tax payer with income below the threshold")
    void calculateTaxForPrimaryTaxPayerWithIncomeBelowTaxThreshold() {
        final BigDecimal incomeTax = taxTable.calculateAnnualIncomeTax(64, BigDecimal.valueOf(83100));
        assertThat(incomeTax).hasToString("0");
    }

    @Test
    @DisplayName("calculate tax for secondary tax payer with income below the threshold")
    void calculateTaxForSecondaryTaxPayerWithIncomeBelowTaxThreshold() {
        final BigDecimal incomeTax = taxTable.calculateAnnualIncomeTax(65, BigDecimal.valueOf(128_650));
        assertThat(incomeTax).hasToString("0");
    }

    @Test
    @DisplayName("calculate tax for tertiary tax payer with income below the threshold")
    void calculateTaxForTertiaryTaxPayerWithIncomeBelowTaxThreshold() {
        final BigDecimal incomeTax = taxTable.calculateAnnualIncomeTax(75, BigDecimal.valueOf(143_850));
        assertThat(incomeTax).hasToString("0");
    }
}
