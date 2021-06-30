package br.com.rchlo.store.domain;

import br.com.rchlo.store.repository.CategoryRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String slug;
    
    private Integer position;
    
    public Long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSlug() {
        return this.slug;
    }
    
    public Integer getPosition() {
        return this.position;
    }
    
    /**
     * @deprecated
     */
    protected Category() {
    }
    
    public Category(String name, String slug, Integer position) {
        this.name = name;
        this.slug = slug;
        this.position = position;
    }
    
    public void generatePosition(final CategoryRepository categoryRepository) {
        this.position = categoryRepository.findMaxPosition() + 1;
    }
}
