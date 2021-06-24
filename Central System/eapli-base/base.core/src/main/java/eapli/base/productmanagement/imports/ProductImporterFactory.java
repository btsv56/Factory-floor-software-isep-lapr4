package eapli.base.productmanagement.imports;

public final class ProductImporterFactory {

    /**
     *	Creates an instance of the respective product importer
     */
    public ProductImporter build(FileFormat format){
        switch(format){
            case CSV:
                return new CSVProductImporter();
        }
        throw new IllegalStateException("Unknown Format");
    }

}
