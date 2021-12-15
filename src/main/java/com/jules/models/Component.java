package com.jules.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Component {
    private String drinkName;
    private float amount;
}
