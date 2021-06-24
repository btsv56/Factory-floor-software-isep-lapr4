package eapli.base.productionordermanagement.imports;

public final class ProductionOrderImporterFactory {

    /**
     *	Creates an instance of the respective production order importer
     */
    public ProductionOrderImporter build (String format){
        if (FileFormat.CSV.getValue().equals(format)) {
            return new CSVProductionOrderImporter();
        }
        throw new IllegalStateException("Unknown Format");
    }

}
