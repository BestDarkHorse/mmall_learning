package com.mmall.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Test {


    /**
     * id : 0
     * title_hello : aaa
     * children : [{"id":9,"title_hello":"QA测试12","children":null},{"id":21,"title_hello":"鱿鱼","children":null}]
     */

    private int id;
    private String titleHello;
    private List<ChildrenBean> children;
}
