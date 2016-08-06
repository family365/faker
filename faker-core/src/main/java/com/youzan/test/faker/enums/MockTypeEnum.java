package com.youzan.test.faker.enums;

/**
 * Created by libaixian on 16/8/5.
 */
public enum MockTypeEnum {
    Manual(1, "manual", "手动添加的自定义而mock接口"),
    Coded(2, "coded", "编码处理的mock接口");

    private int type;
    private String code;
    private String desc;
    private MockTypeEnum(int type, String code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public static MockTypeEnum getByCode(String code) {
        for(MockTypeEnum each : values()) {
            if (each.code.equalsIgnoreCase(code)) {
                return each;
            }
        }

        return null;
    }
}
