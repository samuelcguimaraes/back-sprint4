package br.com.rchlo.store.repository;

import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductByColorDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProductRepository {

    private final EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // essa query pode ter problemas de performance: @see https://vladmihalcea.com/hibernate-multiplebagfetchexception/ */
    public List<Product> findAllWithImagesCategoryAndSizesOrderByName() {
        return entityManager.createQuery("select distinct p from Product p left join fetch p.images join fetch p.category left join fetch p.availableSizes order by p.name", Product.class).getResultList();
    }

    public List<ProductByColorDto> productsByColor() {
        return entityManager.createQuery("select new br.com.rchlo.store.dto.ProductByColorDto(p.color, count(p)) from Product p group by p.color", ProductByColorDto.class).getResultList();
    }

}
