package io.github.linteddy.taxcalculator.domain;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaxTable {
    @NotEmpty
    private List<TaxRate> taxableIncomeTaxRates = new ArrayList<>();
    @NotNull
    private TaxRebate taxRebates;
    @NotNull
    private TaxThreshold taxThresholds;
    @NotNull
    private MedicalAidTaxCredits medicalAidTaxCredits;

    @Data
    static class TaxRate {
        @Min(1)
        private int lowerTaxBracket;
        @Min(1)
        @Max(100)
        private double percentage;
        private int amount;
    }

}
