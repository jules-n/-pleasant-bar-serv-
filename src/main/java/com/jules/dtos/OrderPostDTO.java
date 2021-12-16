package com.jules.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPostDTO {
    private List<DrinkNumberDTO> drinks;
    private LocalDateTime creationTime;
    @Nullable
    private int table;
}
