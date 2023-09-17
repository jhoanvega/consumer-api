package org.heroes.consumer;

import lombok.Data;

import java.util.List;

@Data
public class DataMarvel {

    private Integer total;
    private List<Characters> results;
}
