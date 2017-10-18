package ru.itex.entity;

import javax.persistence.*;

/**
 * Created by PC-020 on 24.01.2017.
 */
@Entity
@Table(name = "option", schema = "qa", catalog = "sbend")
public class OptionEntity {

    Long id;
    String key;
    String value;

    @SequenceGenerator(name="Option_Gen", sequenceName="Option_Seq")
    @Id
    @GeneratedValue(generator = "Option_Gen")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionEntity that = (OptionEntity) o;

        if (!id.equals(that.id)) return false;
        if (!key.equals(that.key)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
