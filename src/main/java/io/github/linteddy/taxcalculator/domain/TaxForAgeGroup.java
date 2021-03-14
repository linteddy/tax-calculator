package io.github.linteddy.taxcalculator.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
abstract class TaxForAgeGroup {
    @Min(1)
    private BigDecimal primary;
    @Min(1)
    private BigDecimal secondary;
    @Min(1)
    private BigDecimal tertiary;

    enum TaxAgeGroup {
        PRIMARY,SECONDARY,TERTIARY
    }

    TaxAgeGroup determineTaxAgeGroup(int age){
        if(age < 65 ){
            return TaxAgeGroup.PRIMARY;
        }else if(age >= 75){
            return TaxAgeGroup.TERTIARY;
        }else {
            return TaxAgeGroup.SECONDARY;
        }
    }
}
