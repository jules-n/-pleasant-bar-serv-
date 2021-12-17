package com.jules.dtos;

import com.jules.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    @Id
    private Long number;
    private int table;
    private Status status;
}
