package com.example.blogchipo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CtpxEntityId implements Serializable {
    private static final long serialVersionUID = 8981586137150735207L;
    @Nationalized
    @Column(name = "MaPX", nullable = false)
    private String maPX;

    @Nationalized
    @Column(name = "MaVT")
    private String maVT;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CtpxEntityId entity = (CtpxEntityId) o;
        return Objects.equals(this.maVT, entity.maVT) &&
                Objects.equals(this.maPX, entity.maPX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVT, maPX);
    }

}