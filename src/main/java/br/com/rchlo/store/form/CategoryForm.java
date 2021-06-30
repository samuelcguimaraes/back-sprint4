package br.com.rchlo.store.form;

import br.com.rchlo.store.domain.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryForm {
	
	@NotBlank
	@Size(max = 255)
	private String name;
	
	@NotBlank
	@Size(max = 255)
	@Pattern(regexp = "^[a-z0-9-]+$", message = "deve conter apenas letras minúsculas, números e hífen")
	private String slug;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSlug() {
		return this.slug;
	}
	
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	public Category converter() {
		return new Category(this.getName(), this.getSlug(), null);
	}
}