package ru.itex.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by PC-020 on 19.01.2017.
 */
public class ResultVO implements java.io.Serializable {

    private long id;
    private long addressId;
    private int score;
    private String type;
    private Timestamp date;
    private String formatDate;
    private String address;
    private String rawData;

    public ResultVO() {
    }

    public ResultVO(long id, long addressId, int score, String type, Timestamp date, String address) {
        this.id = id;
        this.addressId = addressId;
        this.score = score;
        this.type = type;
        this.date = date;
        this.address = address;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = simpleDateFormat.format(this.getDate());
        this.formatDate = dateString;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultVO resultVO = (ResultVO) o;

        if (id != resultVO.id) return false;
        if (addressId != resultVO.addressId) return false;
        if (score != resultVO.score) return false;
        if (!type.equals(resultVO.type)) return false;
        if (!date.equals(resultVO.date)) return false;
        return formatDate.equals(resultVO.formatDate);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + score;
        result = 31 * result + type.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + formatDate.hashCode();
        return result;
    }
}
