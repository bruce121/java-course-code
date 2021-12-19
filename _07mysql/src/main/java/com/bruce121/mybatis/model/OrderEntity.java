package com.bruce121.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class OrderEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "order_status")
    private Boolean orderStatus;

    @Column(name = "order_amount_total")
    private BigDecimal orderAmountTotal;

    @Column(name = "created_time")
    private Long createdTime;

    @Column(name = "last_modified_time")
    private Long lastModifiedTime;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderAmountTotal=" + orderAmountTotal +
                ", createdTime=" + createdTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
