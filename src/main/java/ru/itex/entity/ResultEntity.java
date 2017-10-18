package ru.itex.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by PC-020 on 18.01.2017.
 */
@Entity
@Table(name = "result", schema = "qa", catalog = "sbend")
public class ResultEntity {

    private Long id;
    private Long addressId;
    private Integer score;
    private String type;

    private java.sql.Timestamp date;

    private Serializable rawData;

    @SequenceGenerator(name="Emp_Gen", sequenceName="Emp_Seq")
    @Id @GeneratedValue(generator = "Emp_Gen")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address_id")
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "date")

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {

        this.date = date;
    }

    @Basic
    @Column(name = "raw_data")
    public Serializable getRawData() {
        return rawData;
    }

    public void setRawData(Serializable rawData) {
        this.rawData = rawData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultEntity that = (ResultEntity) o;

        if (id != that.id) {
            return false;
        }
        if (addressId != that.addressId) {
            return false;
        }
        if (score != that.score) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        if (rawData != null ? !rawData.equals(that.rawData) : that.rawData != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + score;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (rawData != null ? rawData.hashCode() : 0);
        return result;
    }
}