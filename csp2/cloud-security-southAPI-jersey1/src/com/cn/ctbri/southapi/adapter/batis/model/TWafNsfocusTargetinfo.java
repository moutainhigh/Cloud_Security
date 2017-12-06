package com.cn.ctbri.southapi.adapter.batis.model;

public class TWafNsfocusTargetinfo extends TWafNsfocusTargetinfoKey {
	private String name;

    private String siteid;

    private String groupid;

    private String virtsiteid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getVirtsiteid() {
        return virtsiteid;
    }

    public void setVirtsiteid(String virtsiteid) {
        this.virtsiteid = virtsiteid;
    }
}