package io.github.linteddy.taxcalculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalAidTaxCreditsTest {

    private final MedicalAidTaxCredits medicalAidTaxCredits = DomainTestUtil.createMedicalAidTaxCredits();

    @Test
    @DisplayName("calculate tax credits with 0 medical aid members")
    void calculateTaxCreditsWithNoMembers() {
        final BigDecimal taxCredits = medicalAidTaxCredits.calculateTaxCredits(0);
        assertThat(taxCredits).hasToString("0");
    }


    @Test
    @DisplayName("calculate tax credits with 1 medical aid members")
    void calculateTaxCreditsWithOneMember() {
        final BigDecimal taxCredits = medicalAidTaxCredits.calculateTaxCredits(1);
        assertThat(taxCredits).hasToString("319");
    }

    @Test
    @DisplayName("calculate tax credits with 2 medical aid members")
    void calculateTaxCreditsWithTwoMembers() {
        final BigDecimal taxCredits = medicalAidTaxCredits.calculateTaxCredits(2);
        assertThat(taxCredits).hasToString("638");
    }

    @Test
    @DisplayName("calculate tax credits with 5 medical aid members")
    void calculateTaxCreditsWithFiveMembers() {
        final BigDecimal taxCredits = medicalAidTaxCredits.calculateTaxCredits(5);
        assertThat(taxCredits).hasToString("1283");
    }
}
