package com.sinosoft.dao;

import com.sinosoft.vo.CsServiceType;

public interface CsServiceTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int insert(CsServiceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int insertSelective(CsServiceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    CsServiceType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int updateByPrimaryKeySelective(CsServiceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service_type
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int updateByPrimaryKey(CsServiceType record);
}