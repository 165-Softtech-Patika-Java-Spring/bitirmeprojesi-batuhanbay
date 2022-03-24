package com.softtechbootcamp.bitirme.app.set.entity;

import com.softtechbootcamp.bitirme.app.gen.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "set_settings")
public class SetSettings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "set_settings_key", length = 255)
    private String key;

    @Column(name = "set_settings_value", length = 255)
    private String value;
}
