package com.jules.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = Order.COLLECTION_NAME)
public class Order {
    public static final String COLLECTION_NAME = "orders";
    @Id
    private Long number;
    private Map<Drink, Integer> drinks;
    private Status status;
    private double check;
    private LocalDateTime creationTime;
    @Nullable
    private int table;
}
