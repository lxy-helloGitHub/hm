package com.bs.glr.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author gonglqb
 * @name
 * @description
 * @create
 **/
@Data
@Resource
@Slf4j
public class VChechVo {
    private Boolean status;
    private String msg;

    public VChechVo() {
    }

    public VChechVo(Boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
