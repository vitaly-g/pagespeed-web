package ru.itex.vo;

/**
 * Created by PC-020 on 19.01.2017.
 */
public class AddressVO implements java.io.Serializable {

    private java.lang.Long id;
    private java.lang.String url;
    private java.lang.String description;

    public AddressVO(Long id, String url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;
    }

    public AddressVO(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public AddressVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public boolean hasEmptyFields() {
//        return  this.getId() == null &&  this.getUrl() == null;
//    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressVO addressVO = (AddressVO) o;

        if (!id.equals(addressVO.id)) return false;
        if (!url.equals(addressVO.url)) return false;
        return description != null ? description.equals(addressVO.description) : addressVO.description == null;
    }
}