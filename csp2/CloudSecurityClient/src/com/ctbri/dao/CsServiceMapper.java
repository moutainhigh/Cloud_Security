package com.ctbri.dao;

import com.ctbri.vo.CsService;

public interface CsServiceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int insert(CsService record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int insertSelective(CsService record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    CsService selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int updateByPrimaryKeySelective(CsService record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_service
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int updateByPrimaryKey(CsService record);
}