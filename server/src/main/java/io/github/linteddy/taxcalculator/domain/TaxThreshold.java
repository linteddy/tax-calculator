package io.github.linteddy.taxcalculator.domain;

import java.math.BigDecimal;

import static io.github.linteddy.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.PRIMARY;
import static io.github.linteddy.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.SECONDARY;

class TaxThreshold extends TaxForAgeGroup {

    BigDecimal getTaxThreshold(int age){
        final TaxAgeGroup taxAgeGroup = determineTaxAgeGroup(age);
        if(PRIMARY == taxAgeGroup){
            return getPrimary();
        }else if (SECONDARY == taxAgeGroup){
            return getSecondary();
        }else {
            return getTertiary();
        }

    }

}
