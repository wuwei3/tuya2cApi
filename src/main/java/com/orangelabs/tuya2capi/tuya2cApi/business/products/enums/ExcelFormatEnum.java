package com.orangelabs.tuya2capi.tuya2cApi.business.products.enums;

public enum ExcelFormatEnum {
	XLS(0, "xls"),
    XLSX(1, "xlsx");

    private Integer key;
    private String value;

    ExcelFormatEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
