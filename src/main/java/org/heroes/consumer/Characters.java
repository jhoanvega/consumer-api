package org.heroes.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Characters {
    private Integer id;
    private String name;
    private String description;
}
