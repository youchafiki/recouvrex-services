package com.recouvrex.process.model.enums;

public enum StatusEnum {
    PRE_DOUTEUX("Pré-douteux"),
    DOUTEUX("Douteux"),
    COMITE_IMPAYES("Comité des impayés"),
    RADIE("Radié"),
    CONTENTIEUX("Contentieux"),
    SAISIE_CONSERVATION_IMMOBILIERE_INITIEE("Saisie conservation immobilière initiée"),
    TERMINE("Terminé");


    public final String label;

    private StatusEnum(String label) {
        this.label = label;
    }
}