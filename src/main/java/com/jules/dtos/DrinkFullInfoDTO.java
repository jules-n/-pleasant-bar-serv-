package com.jules.dtos;

import com.jules.models.Characteristic;
import com.jules.models.Component;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DrinkFullInfoDTO {
    private String name;
    @Nullable
    private Set<Characteristic> characteristics;
    @Nullable
    private Set<Component> components;
    private float price;
    private float amount;
    private float totalAmount;
}
