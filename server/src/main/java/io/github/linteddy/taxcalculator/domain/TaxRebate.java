package io.github.linteddy.taxcalculator.domain;

import java.math.BigDecimal;

import static io.github.linteddy.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.PRIMARY;
import static io.github.linteddy.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.SECONDARY;

class TaxRebate extends TaxForAgeGroup {

    BigDecimal calculateTaxRebates(final int age){
        final TaxAgeGroup taxAgeGroup = determineTaxAgeGroup(age);
        if(PRIMARY == taxAgeGroup){
            return getPrimary();
        }else if (SECONDARY == taxAgeGroup){
            return getPrimary().add(getSecondary());
        }else {
            return getPrimary().add(getSecondary()).add(getTertiary());
        }
    }
}
