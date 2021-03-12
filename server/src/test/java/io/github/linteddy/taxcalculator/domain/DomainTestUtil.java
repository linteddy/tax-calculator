package io.github.linteddy.taxcalculator.domain;

import java.math.BigDecimal;

public class DomainTestUtil {

    static MedicalAidTaxCredits createMedicalAidTaxCredits() {
        MedicalAidTaxCredits medicalAidTaxCredits = new MedicalAidTaxCredits();
        medicalAidTaxCredits.setMain(BigDecimal.valueOf(319));
        medicalAidTaxCredits.setFirstDependant(BigDecimal.valueOf(319));
        medicalAidTaxCredits.setAdditionalDependant(BigDecimal.valueOf(215));
        return medicalAidTaxCredits;
    }
}
