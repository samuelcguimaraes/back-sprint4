package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Category;
import br.com.rchlo.store.dto.CategoryDto;
import br.com.rchlo.store.form.CategoryForm;
import br.com.rchlo.store.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @GetMapping("/categories")
    public List<CategoryDto> categories() {
        return this.categoryRepository.findAllByOrderByPositionAsc().stream().map(CategoryDto::new).collect(
                Collectors.toList());
    }
    
    @GetMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDto> detail(@PathVariable("id") final Long id) {
        final var category = this.categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        return ResponseEntity.ok(new CategoryDto(category));
    }
    
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryForm form, UriComponentsBuilder uriBuilder) {
        
        Category category = form.converter();
        category.generatePosition(this.categoryRepository);
        this.categoryRepository.save(category);
        
        URI uri = uriBuilder.path("/admin/categories/{id}").buildAndExpand(category.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new CategoryDto(category));
    }
    
}
