package com.sinosoft.vo;

import java.util.Date;

public class CsOrderAsset {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_order_asset.id
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_order_asset.orderId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    private String orderid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_order_asset.assetId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    private Integer assetid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_order_asset.serviceId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    private Integer serviceid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_order_asset.scan_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    private Integer scanType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_order_asset.scan_date
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    private Date scanDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cs_order_asset.id
     *
     * @return the value of cs_order_asset.id
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cs_order_asset.id
     *
     * @param id the value for cs_order_asset.id
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cs_order_asset.orderId
     *
     * @return the value of cs_order_asset.orderId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cs_order_asset.orderId
     *
     * @param orderid the value for cs_order_asset.orderId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cs_order_asset.assetId
     *
     * @return the value of cs_order_asset.assetId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public Integer getAssetid() {
        return assetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cs_order_asset.assetId
     *
     * @param assetid the value for cs_order_asset.assetId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public void setAssetid(Integer assetid) {
        this.assetid = assetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cs_order_asset.serviceId
     *
     * @return the value of cs_order_asset.serviceId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public Integer getServiceid() {
        return serviceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cs_order_asset.serviceId
     *
     * @param serviceid the value for cs_order_asset.serviceId
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cs_order_asset.scan_type
     *
     * @return the value of cs_order_asset.scan_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public Integer getScanType() {
        return scanType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cs_order_asset.scan_type
     *
     * @param scanType the value for cs_order_asset.scan_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public void setScanType(Integer scanType) {
        this.scanType = scanType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cs_order_asset.scan_date
     *
     * @return the value of cs_order_asset.scan_date
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public Date getScanDate() {
        return scanDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cs_order_asset.scan_date
     *
     * @param scanDate the value for cs_order_asset.scan_date
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }
}