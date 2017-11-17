package com.cn.ctbri.model;

import java.util.Date;

public class Task {
    private Long taskid;

    private String orderid;

    private String assetid;

    private String asseturl;

    private Integer servicetype;

    private Integer origin;

    private Integer iscycle;

    private Integer periodic;

    private Date startdate;

    private Date enddate;

    private Integer engineid;

    private Integer status;

    private Integer totaltimes;

    private String errorinfo;

    private Date updatetime;

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getAssetid() {
        return assetid;
    }

    public void setAssetid(String assetid) {
        this.assetid = assetid == null ? null : assetid.trim();
    }

    public String getAsseturl() {
        return asseturl;
    }

    public void setAsseturl(String asseturl) {
        this.asseturl = asseturl == null ? null : asseturl.trim();
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getIscycle() {
        return iscycle;
    }

    public void setIscycle(Integer iscycle) {
        this.iscycle = iscycle;
    }

    public Integer getPeriodic() {
        return periodic;
    }

    public void setPeriodic(Integer periodic) {
        this.periodic = periodic;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getEngineid() {
        return engineid;
    }

    public void setEngineid(Integer engineid) {
        this.engineid = engineid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotaltimes() {
        return totaltimes;
    }

    public void setTotaltimes(Integer totaltimes) {
        this.totaltimes = totaltimes;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo == null ? null : errorinfo.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}