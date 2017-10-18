package ru.itex.vo;

/**
 * Created by devil4lived@gmail.com on 25.01.2017.
 */
public class OptionVO implements java.io.Serializable {

    private java.lang.Long id;
    private java.lang.String key;
    private java.lang.String value;

    public OptionVO() {
    }

    public OptionVO(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

        OptionVO optionVO = (OptionVO) o;

        if (!id.equals(optionVO.id)) return false;
        if (!key.equals(optionVO.key)) return false;
        return value.equals(optionVO.value);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
