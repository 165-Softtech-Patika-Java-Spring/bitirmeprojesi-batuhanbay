package com.softtechbootcamp.bitirme.app.gen.entity;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class BaseEntity implements BaseModel, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    @Embedded
    BaseAdditionalFields baseAdditionalFields;
}
