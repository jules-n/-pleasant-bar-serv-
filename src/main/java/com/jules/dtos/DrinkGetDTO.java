package com.jules.dtos;

import com.jules.models.Characteristic;
import com.jules.models.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrinkGetDTO {
    private String name;
    @Nullable
    private Set<Characteristic> characteristics;
    @Nullable
    private Set<Component> components;
    private float price;
    private float amount;
    @Nullable Set<Component> nonDrinksComponent;
}
