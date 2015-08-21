package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.DistrictDataDao;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.ITaskService;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-08-20
 * 描        述：  数据展示业务层实现类
 * 版        本：  1.0
 */
@Service
public class DistrictDataServiceImpl implements IDistrictDataService{

	@Autowired
	DistrictDataDao districtDataDao;

    public List<District> getDistrictByAll() {
        return districtDataDao.getDistrictByAll();
    }



}
