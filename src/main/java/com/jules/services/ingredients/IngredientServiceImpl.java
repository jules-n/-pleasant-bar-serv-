package com.jules.services.ingredients;

import com.jules.dtos.IngredientPostDTO;
import com.jules.models.Ingredient;
import com.jules.persistence.IngredientRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Setter(onMethod_ = @Autowired)
    private IngredientRepository repository;

    @Override
    public void save(IngredientPostDTO dto) {
        var ingredient = Ingredient.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .totalAmount(dto.getTotalAmount())
                .build();
        repository.save(ingredient);
    }

    @Override
    public Optional<Ingredient> findByName(String name) {
        return Optional.of(repository.findByName(name));
    }

    @Override
    public void update(String name, IngredientPostDTO dto) {
        delete(name);
        save(dto);
    }

    @Override
    public void delete(String name) {
        var ingredient = repository.findByName(name);
        repository.deleteById(ingredient.getId());
    }
}
