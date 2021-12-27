package com.jules.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@Document(collection = Ingredient.COLLECTION_NAME)
public class Ingredient {
    public static final String COLLECTION_NAME = "ingredients";
    private UUID id;
    private String name;
    private float totalAmount;
}
