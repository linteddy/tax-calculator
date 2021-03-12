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

    static Set<TaxBracket> createTaxBrackets(){
        Set<TaxBracket> taxBrackets = new HashSet<>();
        taxBrackets.add(new TaxBracket(BigDecimal.ZERO, BigDecimal.valueOf(205900), 18, null));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(205901), BigDecimal.valueOf(321600), 26, BigDecimal.valueOf(37062)));
        taxBrackets.add(new TaxBracket(BigDecimal.valueOf(1577301), null, 45, BigDecimal.valueOf(559464)));
        return taxBrackets;
    }
}
