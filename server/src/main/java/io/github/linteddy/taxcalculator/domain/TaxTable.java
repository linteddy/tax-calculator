package io.github.linteddy.taxcalculator.domain;

import io.github.linteddy.taxcalculator.exception.TaxTableException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxTable {
    @NotEmpty
    private Set<TaxBracket> taxBrackets = new HashSet<>();
    @NotNull
    private TaxRebate taxRebates;
    @NotNull
    private TaxThreshold taxThresholds;
    @NotNull
    private MedicalAidTaxCredits medicalAidTaxCredits;

    public BigDecimal calculateIncomeTax(final int age, BigDecimal totalTaxableIncome, final int medicalAidMembers) {
        if (isIncomeLessThanTaxThresholds(totalTaxableIncome, age)) {
            return BigDecimal.ZERO;
        }
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream().filter(bracket -> bracket.isInTaxBracket(totalTaxableIncome)).findAny();
        if (optionalTaxBracket.isPresent()) {
            final TaxBracket taxBracket = optionalTaxBracket.get();
            BigDecimal tax = totalTaxableIncome.subtract(taxBracket.getMin()).add(BigDecimal.ONE);
            tax = tax.multiply(BigDecimal.valueOf(taxBracket.getPercentage() / 100));
            tax = tax.add(taxBracket.getAmount());
            tax = tax.subtract(taxRebates.calculateTaxRebates(age));
            return tax;
        }
        throw new TaxTableException("Error finding tax bracket for total taxable amount : "+totalTaxableIncome.toString());
    }

    private boolean isIncomeLessThanTaxThresholds(final BigDecimal totalTaxableIncome, final int age) {
        return totalTaxableIncome.compareTo(taxThresholds.getTaxThreshold(age)) < 1;
    }

}
