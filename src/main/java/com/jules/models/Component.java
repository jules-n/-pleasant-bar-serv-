package com.jules.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Component {
    private String name;
    private float amount;
}
