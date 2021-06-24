package eapli.base.productionordermanagement.imports;

public enum FileFormat {
    CSV("csv");

    private String value;

    FileFormat(String avalue) {
        this.value=avalue;
    }

    public String getValue() {
        return value;
    }
}
