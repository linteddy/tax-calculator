package io.github.linteddy.taxcalculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TaxTableTest {
    private final TaxTable taxTable = DomainTestUtil.createTaxTable();

    @Test
    @DisplayName("calculate tax for seconday tax payer without medical aid credits")
    void calculateTaxForSecondaryTaxPayerWithoutMedicalAidCredits() {
        final BigDecimal incomeTax = taxTable.calculateIncomeTax(70, BigDecimal.valueOf(360_000), 0);
        assertThat(incomeTax).hasToString("55891");
    }

}
