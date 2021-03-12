package io.github.linteddy.taxcalculator.domain;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
class TaxBracketTest {

    private static Set<TaxBracket> taxBrackets;

    @BeforeAll
    static void setUp() {
        taxBrackets = new HashSet<>();
        taxBrackets.add(new TaxBracket(BigDecimal.ZERO, BigDecimal.valueOf(205900), 18, null));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(205901), BigDecimal.valueOf(321600), 26, BigDecimal.valueOf(37062)));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(1577301), null, 45, BigDecimal.valueOf(559464)));
    }

    @Test()
    @DisplayName("TaxBracket meets the minimum requirements for HashSet")
    @Order(1)
    void testEquality() {
        assertThat(taxBrackets).hasSize(3);
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(1577301), null, 45, BigDecimal.valueOf(559464)));
        assertThat(taxBrackets).hasSize(3);
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(321601), BigDecimal.valueOf(445100), 31, BigDecimal.valueOf(67144)));
        assertThat(taxBrackets).hasSize(4);
    }

    @Test
    @DisplayName("should find tax bracket when taxable income is in lowest tax bracket")
    void incomeInLowestTaxBracket() {
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream()
                .filter(taxBracket -> taxBracket.isInTaxBracket(BigDecimal.TEN)).findAny();
        assertThat(optionalTaxBracket).hasValue((new TaxBracket(BigDecimal.ZERO, BigDecimal.valueOf(205900), 18, null)));
    }

    @Test
    @DisplayName("should find tax bracket when taxable income is in highest tax bracket")
    void incomeInHighestTaxBracket() {
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream()
                .filter(taxBracket -> taxBracket.isInTaxBracket(BigDecimal.valueOf(1577600))).findAny();
        assertThat(optionalTaxBracket).hasValue((
                new TaxBracket(BigDecimal.valueOf(1577301), null, 45, BigDecimal.valueOf(559464)))
        );
    }

    @Test
    @DisplayName("should find tax bracket when taxable income is neither in the highest nor in the lowest tax bracket")
    void incomeNotInHighestAndLowestTaxBracket() {
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream()
                .filter(taxBracket -> taxBracket.isInTaxBracket(BigDecimal.valueOf(300_000))).findAny();
        assertThat(optionalTaxBracket).hasValue((
                new TaxBracket(BigDecimal.valueOf(205901), BigDecimal.valueOf(321600), 26, BigDecimal.valueOf(37062)))
        );
    }



}
