package com.cyyaw.coco.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FriendsEntity implements Serializable {


    private Integer id;
    private String tid;
    private Date createTime;
    private Integer del;
    private String note;
    private String appId;
    private String account;
    private String password;
    private String trueName;
    private String phone;
    private String nickName;
    private String face;
    private String sex;
    private Date canLoginTime;
    private String email;
    private String ip;
    private Date lastLoginTime;
    private String salt;
    private Integer status;
    private Integer type;
    private String adminId;
    private BigDecimal balance;
    private Integer integral;
    private String openId;
    private String unionId;
    private String introduceSign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
