package io.github.linteddy.taxcalculator.domain;

import io.github.linteddy.taxcalculator.exception.TaxTableException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class TaxTable {
    @NotEmpty
    private Set<TaxBracket> taxBrackets = new HashSet<>();
    @NotNull
    private TaxRebate taxRebates;
    @NotNull
    private TaxThreshold taxThresholds;
    @NotNull
    private MedicalAidTaxCredits medicalAidTaxCredits;

    public BigDecimal calculateAnnualIncomeTax(final int age, BigDecimal totalAnnualTaxableIncome) {
        if (isIncomeLessThanTaxThresholds(totalAnnualTaxableIncome, age)) {
            return BigDecimal.ZERO;
        }
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream().filter(bracket -> bracket.isInTaxBracket(totalAnnualTaxableIncome)).findAny();
        if (optionalTaxBracket.isPresent()) {
            final TaxBracket taxBracket = optionalTaxBracket.get();
            BigDecimal incomeTax = totalAnnualTaxableIncome.subtract(taxBracket.getMin()).add(BigDecimal.ONE);
            incomeTax = incomeTax.multiply(BigDecimal.valueOf(taxBracket.getPercentage() / 100));
            incomeTax = incomeTax.add(taxBracket.getAmount());
            incomeTax = incomeTax.subtract(taxRebates.calculateTaxRebates(age));
            return incomeTax;
        }
        throw new TaxTableException("Error finding tax bracket for total taxable amount : "+totalAnnualTaxableIncome.toString());
    }

    public BigDecimal calculateAnnualTaxCredits(int medicalAidMembers) {
        return medicalAidTaxCredits.calculateAnnualTaxCredits(medicalAidMembers);
    }

    private boolean isIncomeLessThanTaxThresholds(final BigDecimal totalTaxableIncome, final int age) {
        return totalTaxableIncome.compareTo(taxThresholds.getTaxThreshold(age)) < 1;
    }

}
