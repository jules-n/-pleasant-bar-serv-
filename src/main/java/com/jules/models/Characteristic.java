package com.jules.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Characteristic<T> {
    private String name;
    private T value;
}
