package io.github.linteddy.taxcalculator.config;

import io.github.linteddy.taxcalculator.domain.TaxTable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "tax")
@Data
@Validated
public class TaxConfig {
    @NotEmpty
    private Map<@NotNull Integer, TaxTable> taxableIncomeTable = new HashMap<>();

}
