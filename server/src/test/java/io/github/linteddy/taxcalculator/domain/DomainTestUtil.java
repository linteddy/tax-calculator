package io.github.linteddy.taxcalculator.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class DomainTestUtil {

    static MedicalAidTaxCredits createMedicalAidTaxCredits() {
        MedicalAidTaxCredits medicalAidTaxCredits = new MedicalAidTaxCredits();
        medicalAidTaxCredits.setMain(BigDecimal.valueOf(319));
        medicalAidTaxCredits.setFirstDependant(BigDecimal.valueOf(319));
        medicalAidTaxCredits.setAdditionalDependant(BigDecimal.valueOf(215));
        return medicalAidTaxCredits;
    }

    static Set<TaxBracket> createTaxBrackets() {
        Set<TaxBracket> taxBrackets = new HashSet<>();
        taxBrackets.add(new TaxBracket(BigDecimal.ZERO, BigDecimal.valueOf(205900), 18, null));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(205901), BigDecimal.valueOf(321600), 26, BigDecimal.valueOf(37062)));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(321601), BigDecimal.valueOf(445_100), 31, BigDecimal.valueOf(67_144)));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(1577301), null, 45, BigDecimal.valueOf(559464)));
        return taxBrackets;
    }

    public static TaxRebate createTaxRebate() {
        var taxRebate = new TaxRebate();
        taxRebate.setPrimary(BigDecimal.valueOf(14_958));
        taxRebate.setSecondary(BigDecimal.valueOf(8_199));
        taxRebate.setTertiary(BigDecimal.valueOf(2_736));
        return taxRebate;
    }

    public static TaxThreshold createTaxThreshold() {
        var taxThreshold = new TaxThreshold();
        taxThreshold.setPrimary(BigDecimal.valueOf(83_100));
        taxThreshold.setSecondary(BigDecimal.valueOf(128_650));
        taxThreshold.setTertiary(BigDecimal.valueOf(143_850));
        return taxThreshold;
    }

    public static TaxTable createTaxTable() {
        return TaxTable.builder()
                .medicalAidTaxCredits(createMedicalAidTaxCredits())
                .taxBrackets(createTaxBrackets())
                .taxRebates(createTaxRebate())
                .taxThresholds(createTaxThreshold())
                .build();
    }
}
