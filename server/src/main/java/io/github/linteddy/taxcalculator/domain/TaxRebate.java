package io.github.linteddy.taxcalculator.domain;

import java.math.BigDecimal;

import static io.github.linteddy.taxcalculator.domain.TaxPayerAgeCategory.AgeCategory.PRIMARY;
import static io.github.linteddy.taxcalculator.domain.TaxPayerAgeCategory.AgeCategory.SECONDARY;

class TaxRebate extends TaxPayerAgeCategory {

    BigDecimal calculateTaxRebates(final int age){
        final AgeCategory ageCategory = determineAgeCategory(age);
        if(PRIMARY == ageCategory){
            return getPrimary();
        }else if (SECONDARY == ageCategory){
            return getPrimary().add(getSecondary());
        }else {
            return getPrimary().add(getSecondary()).add(getTertiary());
        }
    }
}
