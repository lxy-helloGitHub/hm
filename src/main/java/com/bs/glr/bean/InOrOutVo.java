package com.bs.glr.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @作者 GLQ
 * @时间 2022-09-10 16:29
 * @描述 ${var}
 */
@Data
public class InOrOutVo implements Serializable {
    private static final long serialVersionUID = 1L;
    List<String> list;
    String inOutNo;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
