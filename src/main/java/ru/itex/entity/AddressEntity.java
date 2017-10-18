package ru.itex.entity;

import javax.persistence.*;

/**
 * Created by PC-020 on 18.01.2017.
 */
@Entity
@Table(name = "address", schema = "qa", catalog = "sbend")
public class AddressEntity {

    private Long id;
    private String url;
    private String description;

    @SequenceGenerator(name="Addr_Gen", sequenceName="Addr_Seq")
    @Id @GeneratedValue(generator = "Addr_Gen")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity that = (AddressEntity) o;

        if (id != that.id) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
