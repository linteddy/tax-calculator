package io.github.linteddy.taxcalculator.domain;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
abstract class TaxPayerAgeCategory {
    @Min(1)
    private int primary;
    @Min(1)
    private int secondary;
    @Min(1)
    private int tertiary;
}
