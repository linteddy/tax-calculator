package io.github.linteddy.taxcalculator.service;

import io.github.linteddy.taxcalculator.config.TaxConfig;
import io.github.linteddy.taxcalculator.domain.TaxTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxTableService {
    private final TaxConfig taxConfig;

    public TaxTable getTaxTable(final int taxYear){
        return taxConfig.getTaxableIncomeTable().get(taxYear);
    }
}
