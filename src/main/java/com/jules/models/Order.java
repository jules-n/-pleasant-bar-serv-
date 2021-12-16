package com.jules.models;

import com.jules.dtos.DrinkNumberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = Order.COLLECTION_NAME)
public class Order {
    public static final String COLLECTION_NAME = "orders";
    @Id
    private Long number;
    private List<DrinkNumberDTO> drinks;
    private Status status;
    private double check;
    private LocalDateTime creationTime;
    @Nullable
    private int table;
}
