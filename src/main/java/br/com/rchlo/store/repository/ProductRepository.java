package br.com.rchlo.store.repository;

import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductByColorDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // essa query pode ter problemas de performance: @see https://vladmihalcea.com/hibernate-multiplebagfetchexception/ */
    @Query("select distinct p from Product p left join fetch p.images join fetch p.category left join fetch p.availableSizes")
    List<Product> findAllWithImagesCategoryAndSizes(final Pageable pageable);
    
    @Query(value = "select p.color color, count(p.color) amount from product p group by p.color", nativeQuery = true)
    List<ProductByColorDto> productsByColor();
    
}