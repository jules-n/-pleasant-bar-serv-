package com.jules.dtos;

import com.jules.models.Drink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPostDTO {
    private Map<Drink, Integer> drinks;
    private LocalDateTime creationTime;
    @Nullable
    private int table;
}
