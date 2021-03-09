package io.github.linteddy.taxcalculator.service;

import io.github.linteddy.taxcalculator.domain.IncomeTax;
import io.github.linteddy.taxcalculator.domain.Period;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class IncomeTaxService {
    public Mono<IncomeTax> calculateIncomeTax(int taxYear, int age, BigDecimal totalTaxableEarnings, Period period, int medicalAidMembers) {
        return null;
    }

}
