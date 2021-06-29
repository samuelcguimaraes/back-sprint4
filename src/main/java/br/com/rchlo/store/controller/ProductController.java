package br.com.rchlo.store.controller;

import br.com.rchlo.store.dto.ProductByColorDto;
import br.com.rchlo.store.dto.ProductDto;
import br.com.rchlo.store.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @GetMapping("/products")
    @Cacheable(value = "listProducts")
    public List<ProductDto> productsByColorReport(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) final Pageable pageable) {
        return this.productRepository.findAllWithImagesCategoryAndSizesOrderByName(pageable)
                                     .stream()
                                     .map(ProductDto::new)
                                     .collect(Collectors.toList());
    }
    
    @GetMapping("/reports/products/by-color")
    public List<ProductByColorDto> productByColorReport() {
        return this.productRepository.productsByColor();
    }
    
    @GetMapping("/products/clear-cache")
    @CacheEvict(value = "listProducts")
    public void productsClearCache() {
    }
}
