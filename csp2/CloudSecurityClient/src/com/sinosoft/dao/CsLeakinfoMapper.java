package com.sinosoft.dao;

import com.sinosoft.vo.CsLeakinfo;

public interface CsLeakinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_leakinfo
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_leakinfo
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int insert(CsLeakinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_leakinfo
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int insertSelective(CsLeakinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_leakinfo
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    CsLeakinfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_leakinfo
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int updateByPrimaryKeySelective(CsLeakinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_leakinfo
     *
     * @mbggenerated Thu Oct 22 09:40:42 CST 2015
     */
    int updateByPrimaryKey(CsLeakinfo record);
}