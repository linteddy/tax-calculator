package io.github.linteddy.taxcalculator.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
abstract class TaxPayerAgeCategory {
    @Min(1)
    private BigDecimal primary;
    @Min(1)
    private BigDecimal secondary;
    @Min(1)
    private BigDecimal tertiary;

    enum AgeCategory{
        PRIMARY,SECONDARY,TERTIARY
    }

    AgeCategory determineAgeCategory(int age){
        if(age < 65 ){
            return AgeCategory.PRIMARY;
        }else if(age >= 75){
            return AgeCategory.TERTIARY;
        }else {
            return AgeCategory.SECONDARY;
        }
    }
}
