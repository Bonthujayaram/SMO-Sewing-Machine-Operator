package com.cutm.smo.dto;

import lombok.Data;

@Data
public class CuttingRequest {
    private String qrid;
    private String type;
    private String size;
    private int quantity;
}