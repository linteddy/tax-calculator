package io.github.linteddy.taxcalculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeTaxResult {
    private BigDecimal payAsYouEarnBeforeTaxCredit;
    private BigDecimal taxCredits;
    private BigDecimal payAsYouEarnAfterTaxCredit;
    private BigDecimal netCash;
}
