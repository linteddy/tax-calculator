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

    public BigDecimal calculateAnnualIncomeTax(final int age, BigDecimal totalAnnualTaxableIncome, final int medicalAidMembers) {
        if (isIncomeLessThanTaxThresholds(totalAnnualTaxableIncome, age)) {
            return BigDecimal.ZERO;
        }
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream().filter(bracket -> bracket.isInTaxBracket(totalAnnualTaxableIncome)).findAny();
        if (optionalTaxBracket.isPresent()) {
            final TaxBracket taxBracket = optionalTaxBracket.get();
            BigDecimal tax = totalAnnualTaxableIncome.subtract(taxBracket.getMin()).add(BigDecimal.ONE);
            tax = tax.multiply(BigDecimal.valueOf(taxBracket.getPercentage() / 100));
            tax = tax.add(taxBracket.getAmount());
            tax = tax.subtract(taxRebates.calculateTaxRebates(age));
            final BigDecimal annualTaxCredits = medicalAidTaxCredits.calculateTaxCredits(medicalAidMembers).multiply(BigDecimal.valueOf(12));
            tax = tax.subtract(annualTaxCredits);
            return tax.compareTo(BigDecimal.ZERO) > 0 ? tax : BigDecimal.ZERO;
        }
        throw new TaxTableException("Error finding tax bracket for total taxable amount : "+totalAnnualTaxableIncome.toString());
    }

    private boolean isIncomeLessThanTaxThresholds(final BigDecimal totalTaxableIncome, final int age) {
        return totalTaxableIncome.compareTo(taxThresholds.getTaxThreshold(age)) < 1;
    }

}
