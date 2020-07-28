package com.mezo.chaos.eros.system.domain;

import java.time.LocalDateTime;

/**
 * 部门
 * @author mezo
 */
public class Depart {
    /**
     * 部门id
     */
    private Integer id;
    /**
     * 机构id
     */
    private Integer orgId;
    /**
     * 部门代码
     */
    private String departCode;
    /**
     * 部门名称
     */
    private String departName;
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
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
