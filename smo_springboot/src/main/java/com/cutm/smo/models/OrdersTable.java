package com.cutm.smo.models;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrdersTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String style;

    @Column(unique = true)
    private Long orderid;

    @Column(name = "customername", length = 255)
    private String customername;

    private ZonedDateTime orderdate;

    private ZonedDateTime shippingdate;

    private int quantity;

    @Column(name = "orderstatus", length = 50)
    private String orderstatus;
}