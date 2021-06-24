package br.com.rchlo.store.repository;

import br.com.rchlo.store.builder.CategoryBuilder;
import br.com.rchlo.store.builder.ProductBuilder;
import br.com.rchlo.store.domain.Category;
import br.com.rchlo.store.domain.Color;
import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductByColorDto;
import br.com.rchlo.store.repository.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private EntityManager entityManager;
    private ProductRepository productRepository;

    @BeforeEach
    public void beforeEach() {
        this.entityManager = JPAUtil.getEntityManager();
        this.productRepository = new ProductRepository(this.entityManager);
        this.entityManager.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        this.entityManager.getTransaction().rollback();
    }

    @Test
    void shouldListAllProductsOrderedByName() {
        Category infantil = aCategory();

        aProduct(infantil);
        anotherProduct(infantil);

        List<Product> products = productRepository.findAllWithImagesCategoryAndSizesOrderByName();

        assertEquals(2, products.size());

        Product firstProduct = products.get(0);
        assertEquals(7L, firstProduct.getCode());
        assertEquals("Jaqueta Puffer Juvenil Com Capuz Super Mario Branco", firstProduct.getName());

        Product secondProduct = products.get(1);
        assertEquals(1L, secondProduct.getCode());
        assertEquals("Regata Infantil Mario Bros Branco", secondProduct.getName());
    }

    @Test
    void shouldRetrieveProductsByColor() {
        Category infantil = aCategory();

        aProduct(infantil);
        anotherProduct(infantil);
        yetAnotherProduct(infantil);

        List<ProductByColorDto> productsByColor = productRepository.productsByColor();
        Collections.sort(productsByColor, Comparator.comparing(ProductByColorDto::getColor));

        assertEquals(2, productsByColor.size());

        ProductByColorDto firstProductDto = productsByColor.get(0);
        assertEquals(Color.WHITE.getDescription(), firstProductDto.getColor());
        assertEquals(2, firstProductDto.getAmount());

        ProductByColorDto secondProductDto = productsByColor.get(1);
        assertEquals(Color.RED.getDescription(), secondProductDto.getColor());
        assertEquals(1, secondProductDto.getAmount());

    }

    private Category aCategory() {
        Category infantil = new CategoryBuilder().withName("Infantil").withSlug("infantil").withPosition(1).build();
        entityManager.persist(infantil);
        return infantil;
    }

    private void aProduct(Category category) {
        Color color = Color.WHITE;
        entityManager.persist(new ProductBuilder().withCode(7L)
                .withName("Jaqueta Puffer Juvenil Com Capuz Super Mario Branco")
                .withDescription("A Jaqueta Puffer Juvenil Com Capuz Super Mario Branco é confeccionada em material sintético.")
                .withSlug("jaqueta-puffer-juvenil-com-capuz-super-mario-branco-13834193_sku").withBrand("Nintendo")
                .withPrice(new BigDecimal("199.90")).withDiscount(null).withColor(color).withWeightInGrams(147)
                .withCategory(category).build());
    }

    private void anotherProduct(Category category) {
        Color color = Color.WHITE;
        entityManager.persist(new ProductBuilder().withCode(1L).withName("Regata Infantil Mario Bros Branco")
                .withDescription("A Regata Infantil Mario Bros Branco é confeccionada em fibra natural. Aposte!")
                .withSlug("regata-infantil-mario-bros-branco-14040174_sku").withBrand("Nintendo")
                .withPrice(new BigDecimal("29.90")).withDiscount(null).withColor(color).withWeightInGrams(98)
                .withCategory(category).build());
    }

    private void yetAnotherProduct(Category infantil) {
        Color color = Color.RED;
        entityManager.persist(new ProductBuilder().withCode(2L).withName("Blusa de Moletom Infantil Mario Bros Vermelho")
                .withDescription("A Blusa de Moletom Infantil Mario Bros Vermelho é quentinha e divertida!")
                .withSlug("blusa-infantil-moletom-mario-bros-vermelho-14125129_sku").withBrand("Nintendo")
                .withPrice(new BigDecimal("79.90")).withDiscount(null).withColor(color).withWeightInGrams(126)
                .withCategory(infantil).build());
    }
}