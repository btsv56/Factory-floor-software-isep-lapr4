package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.domain.model.general.Designation;

public class ProductBuilder implements DomainFactory<Product> {

    private Designation fabricationCode;
    private String comercialCode;
    private String briefDescription;
    private String completeDescription;
    private String category;
    private String unity;

    public ProductBuilder withoutProductionSheet(final Designation fabricationCode, final String comercialCode, final String briefDescription, final String completeDescription, final String category, final String unity){
        this.fabricationCode = fabricationCode;
        this.comercialCode = comercialCode;
        this.briefDescription = briefDescription;
        this.completeDescription = completeDescription;
        this.category = category;
        this.unity = unity;
        return this;
    }

    public ProductBuilder withProductionSheet(){
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Product build() {
        return new Product(this.fabricationCode, this.comercialCode, this.briefDescription, this.completeDescription, this.category, this.unity);
    }
}
