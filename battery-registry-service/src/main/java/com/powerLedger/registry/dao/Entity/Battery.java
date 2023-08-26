package com.powerLedger.registry.dao.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "battery", uniqueConstraints = { @UniqueConstraint(name = "uniquePostCodeAndName", columnNames = { "name", "post_code" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Battery{
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Column(name = "post_code")
    private Integer postCode;

    @Column(name = "watt_capacity")
    private Integer wattCapacity;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;
}
