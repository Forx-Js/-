package ht.service.common.po;

import java.io.Serializable;

/**
 * Created by zhong on 2017/11/28.
 */
public class SerialNoPo implements Serializable {
    private String serialName;
    private Integer currentValue;
    private Integer valueLength;
    private String prefix;
    private String suffix;
    private Integer ver;

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getValueLength() {
        return valueLength;
    }

    public void setValueLength(Integer valueLength) {
        this.valueLength = valueLength;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }
}
