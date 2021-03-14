package io.github.linteddy.taxcalculator.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
class MedicalAidTaxCredits {
    @Min(1)
    private BigDecimal main;
    @Min(1)
    private BigDecimal firstDependant;
    @Min(1)
    private BigDecimal additionalDependant;

    public BigDecimal calculateAnnualTaxCredits(int medicalAidMembers) {
        BigDecimal taxCredits;
        if (medicalAidMembers == 1) {
            taxCredits = main;
        } else if (medicalAidMembers == 2) {
           taxCredits = main.add(firstDependant);
        } else if (medicalAidMembers > 2) {
            final BigDecimal additionalDependantsAmount = additionalDependant.multiply(BigDecimal.valueOf(medicalAidMembers - 2));
           taxCredits =  main.add(firstDependant).add(additionalDependantsAmount);
        } else {
            return BigDecimal.ZERO;
        }
        return taxCredits.multiply(BigDecimal.valueOf(12));
    }
}
