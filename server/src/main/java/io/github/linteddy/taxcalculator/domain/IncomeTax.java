package io.github.linteddy.taxcalculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeTax {
    private BigDecimal monthlyPAYEBeforeTaxCredit;
    private BigDecimal annuallyPAYEBeforeTaxCredit;
    private BigDecimal taxCredits;
    private BigDecimal paye;
    private BigDecimal netCash;
}
