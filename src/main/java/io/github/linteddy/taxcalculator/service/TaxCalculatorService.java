package io.github.linteddy.taxcalculator.service;

import io.github.linteddy.taxcalculator.domain.IncomeTaxResult;
import io.github.linteddy.taxcalculator.domain.Period;
import io.github.linteddy.taxcalculator.domain.TaxTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TaxCalculatorService {
    private final TaxTableService taxTableService;

    public Mono<IncomeTaxResult> calculateIncomeTax(int taxYear, int age, BigDecimal totalTaxableEarnings, Period period, int medicalAidMembers) {
        final TaxTable taxTable = taxTableService.getTaxTable(taxYear);
        final BigDecimal payAsYouEarnBeforeTax = calculatePayAsYouEarnBeforeTax(age, totalTaxableEarnings, taxTable, period);
        final BigDecimal taxCredits = calculateTaxCredits(taxTable, medicalAidMembers, period);
        final BigDecimal payAsYouEarnAfterTaxCredits = calculatePayAsYouEarnAfterTaxCredits(payAsYouEarnBeforeTax, taxCredits);
        final BigDecimal netCash = totalTaxableEarnings.subtract(payAsYouEarnAfterTaxCredits);

        final IncomeTaxResult incomeTaxResult = IncomeTaxResult.builder()
                .payAsYouEarnBeforeTaxCredit(payAsYouEarnBeforeTax)
                .taxCredits(taxCredits)
                .payAsYouEarnAfterTaxCredit(payAsYouEarnAfterTaxCredits)
                .netCash(netCash)
                .build();
        return Mono.just(incomeTaxResult);
    }

    private BigDecimal calculateTaxCredits(final TaxTable taxTable, final int medicalAidMembers, final Period period) {
        BigDecimal annualTaxCredits = taxTable.calculateAnnualTaxCredits(medicalAidMembers);
        return getPeriodValue(period, annualTaxCredits);
    }

    private BigDecimal calculatePayAsYouEarnBeforeTax(final int age, final BigDecimal totalTaxableEarnings, final TaxTable taxTable, Period period) {
        final BigDecimal annualPayAsYouEarTax;
        if (period == Period.ANNUALLY) {
            annualPayAsYouEarTax =  taxTable.calculateAnnualIncomeTax(age, totalTaxableEarnings);
        } else {
            annualPayAsYouEarTax = taxTable.calculateAnnualIncomeTax(age, totalTaxableEarnings.multiply(BigDecimal.valueOf(12)));
        }
        return getPeriodValue(period, annualPayAsYouEarTax);
    }

    private BigDecimal getPeriodValue(Period period, BigDecimal annualAmount) {
        return period == Period.ANNUALLY ? annualAmount : annualAmount.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePayAsYouEarnAfterTaxCredits(final BigDecimal payAsYouEarnBeforeTaxCredit, final BigDecimal taxCredits) {
        final boolean isPayAsYouEarnGreaterThanTaxCredits = payAsYouEarnBeforeTaxCredit.compareTo(taxCredits) > 0;
        return isPayAsYouEarnGreaterThanTaxCredits ? payAsYouEarnBeforeTaxCredit.subtract(taxCredits) : BigDecimal.ZERO;
    }
}
