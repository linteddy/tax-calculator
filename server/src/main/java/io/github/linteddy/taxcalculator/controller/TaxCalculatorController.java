package io.github.linteddy.taxcalculator.controller;

import io.github.linteddy.taxcalculator.domain.IncomeTax;
import io.github.linteddy.taxcalculator.domain.Period;
import io.github.linteddy.taxcalculator.service.IncomeTaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/tax")
public class TaxCalculatorController {
    private final IncomeTaxService incomeTaxService;

    @GetMapping("/income-tax")
    public Mono<IncomeTax> getIncomeTax(@RequestParam final int year, @RequestParam final int age,
                                        @RequestParam final BigDecimal income, @RequestParam final Period period,
                                        @RequestParam final int medicalAidMembers) {
        return incomeTaxService.calculateIncomeTax(year,age,income,period,medicalAidMembers);
    }

}
