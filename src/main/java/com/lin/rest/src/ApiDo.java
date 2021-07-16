package com.lin.rest.src;

import lombok.*;

import java.util.*;

/**
 * @Author linjing
 * @create 2021/7/15 11:03
 */
@Data
@Builder
public class ApiDo {
    private String api;
    private String desc;
    private String type;
    private List<ParamDo> params;
}
