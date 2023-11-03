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
public class CtpnEntityId implements Serializable {
    private static final long serialVersionUID = 5959059698612030493L;
    @Nationalized
    @Column(name = "MaPN", nullable = false)
    private String maPN;

    @Nationalized
    @Column(name = "MaVT")
    private String maVT;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CtpnEntityId entity = (CtpnEntityId) o;
        return Objects.equals(this.maPN, entity.maPN) &&
                Objects.equals(this.maVT, entity.maVT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPN, maVT);
    }

}