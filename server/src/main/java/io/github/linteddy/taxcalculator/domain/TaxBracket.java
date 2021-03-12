package io.github.linteddy.taxcalculator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "min")
@AllArgsConstructor
@NoArgsConstructor
class TaxBracket {
    @NotNull
    private BigDecimal min;
    private BigDecimal max;
    @Min(1)
    @Max(100)
    private double percentage;
    private BigDecimal amount;

    boolean isInTaxBracket(final BigDecimal totalTaxableIncome) {
        if (this.max == null) {
            return isEqualToOrGreaterThanTaxBracketMinimum(totalTaxableIncome);
        }
        return isEqualToOrGreaterThanTaxBracketMinimum(totalTaxableIncome)  && isEqualToOrLessThanTaxBracketMaximum(totalTaxableIncome);
    }

    private boolean isEqualToOrLessThanTaxBracketMaximum(final BigDecimal totalTaxableIncome) {
        return totalTaxableIncome.compareTo(this.max) < 1;
    }

    private boolean isEqualToOrGreaterThanTaxBracketMinimum(final BigDecimal totalTaxableIncome) {
        return totalTaxableIncome.compareTo(this.min) > -1;
    }

}
