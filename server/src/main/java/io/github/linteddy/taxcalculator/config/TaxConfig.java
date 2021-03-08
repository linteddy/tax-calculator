package io.github.linteddy.taxcalculator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "tax")
@Data
@Validated
public class TaxConfig {
    @NotEmpty
    private Map<@NotNull Integer,TaxTable> taxTables = new HashMap<>();

    @Data
    static class TaxTable {
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
        @Data
        static class TaxPayerAgeCategory {
            @Min(1)
            private int primary;
            @Min(1)
            private int secondary;
            @Min(1)
            private int tertiary;
        }

        static class TaxThreshold extends TaxPayerAgeCategory {
        }
        static class TaxRebate extends TaxPayerAgeCategory {
        }

        @Data
        static class MedicalAidTaxCredits {
            @Min(1)
            private int main;
            @Min(1)
            private int firstDependant;
            @Min(1)
            private int additionalDependant;
        }
    }
}
