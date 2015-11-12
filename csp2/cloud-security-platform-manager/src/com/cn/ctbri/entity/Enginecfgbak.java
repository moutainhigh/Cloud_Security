package com.cn.ctbri.entity;

public class Enginecfgbak {
    private Integer id;

    private String engineNumber;

    private String engineName;

    private String equipmentFactory;

    private String engineAddr;

    private String engineApi;

    private String username;

    private String password;

    private String engineCapacity;

    private String engineCapacityModel;

    private Integer maxtask;

    private Integer activity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber == null ? null : engineNumber.trim();
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName == null ? null : engineName.trim();
    }

    public String getEquipmentFactory() {
        return equipmentFactory;
    }

    public void setEquipmentFactory(String equipmentFactory) {
        this.equipmentFactory = equipmentFactory == null ? null : equipmentFactory.trim();
    }

    public String getEngineAddr() {
        return engineAddr;
    }

    public void setEngineAddr(String engineAddr) {
        this.engineAddr = engineAddr == null ? null : engineAddr.trim();
    }

    public String getEngineApi() {
        return engineApi;
    }

    public void setEngineApi(String engineApi) {
        this.engineApi = engineApi == null ? null : engineApi.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity == null ? null : engineCapacity.trim();
    }

    public String getEngineCapacityModel() {
        return engineCapacityModel;
    }

    public void setEngineCapacityModel(String engineCapacityModel) {
        this.engineCapacityModel = engineCapacityModel == null ? null : engineCapacityModel.trim();
    }

    public Integer getMaxtask() {
        return maxtask;
    }

    public void setMaxtask(Integer maxtask) {
        this.maxtask = maxtask;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }
}