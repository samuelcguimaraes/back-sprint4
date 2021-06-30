package br.com.rchlo.store.dto;

import br.com.rchlo.store.domain.Color;

public interface ProductByColorDto {
    
    Color getColor();
    
    long getAmount();
    
    default String getColorDescription() {
        return this.getColor().getDescription();
    }
    
}
