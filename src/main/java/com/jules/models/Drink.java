package com.jules.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@Document(collection = Drink.COLLECTION_NAME)
public class Drink {
    public static final String COLLECTION_NAME = "drinks";
    private UUID id;
    private String name;
    @Nullable
    private Set<Characteristic> characteristics;
    @Nullable
    private Set<Component> components;
    private float price;
    private float amount;
    private float totalAmount;
    @Nullable Set<Component> nonDrinksComponent;
}
