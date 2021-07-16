package com.lin.rest.src;

import lombok.*;

/**
 * @Author linjing
 * @create 2021/7/15 11:05
 */
@Data
@Builder
public class ParamDo {
    private String name;
    private String desc;
    private String type;
    private Boolean isRequire;
}
