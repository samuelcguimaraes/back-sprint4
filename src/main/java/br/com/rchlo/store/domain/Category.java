package br.com.rchlo.store.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

    private Integer position;

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    /** @deprecated */
    protected Category () {
    }

    public Category(String name, String slug, Integer position) {
        this.name = name;
        this.slug = slug;
        this.position = position;
    }
}
