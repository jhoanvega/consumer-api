package org.heroes.consumer;

import lombok.Data;

import java.util.List;

@Data
public class ResponseMarvel {

    private String code;
    private String status;
    private DataMarvel data;
}
