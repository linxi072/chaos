package com.mezo.chaos.eros.system.domain;

import java.time.LocalDateTime;

/**
 * 机构
 * @author mezo
 */
public class Organ {
    /**
     * 机构id
     */
    private Integer id;
    /**
     * 上级id
     */
    private Integer upId;
    /**
     * 机构编码
     */
    private String organCode;
    /**
     * 机构名称
     */
    private String organName;
    /**
     * 创建时间
     */
    private LocalDateTime createDateTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpId() {
        return upId;
    }

    public void setUpId(Integer upId) {
        this.upId = upId;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
