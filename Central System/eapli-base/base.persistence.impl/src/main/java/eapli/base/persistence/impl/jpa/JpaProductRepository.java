package eapli.base.persistence.impl.jpa;

import javax.persistence.TypedQuery;

import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.model.general.Designation;

public class JpaProductRepository extends BasepaRepositoryBase<Product, Designation, Designation> implements ProductRepository {
    public JpaProductRepository() {
        super("fabricationCode");
    }

    @Override
    public Iterable<Product> findProductsWithoutProductionSheet() {
        final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p FROM Product p where p.productionSheet is null", Product.class
        );
        return query.getResultList();
    }
}
