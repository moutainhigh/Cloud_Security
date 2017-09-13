package com.cn.ctbri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.IPPositionDao;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.IPPosition;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.service.IIPPositionService;
import com.cn.ctbri.service.IOrderAssetService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-20
 * 描        述：  订单资产业务层实现类
 * 版        本：  1.0
 */
@Service
public class IPPositionServiceImpl implements IIPPositionService{
	
	@Autowired
	IPPositionDao ipPositionDao;
	
	public IPPosition findIPPositionByIP(String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, IPPosition> findIPPositions() {
		List<IPPosition> ipPositionList=ipPositionDao.getIPPositions();
		Map<String,IPPosition> map=new LinkedHashMap<String,IPPosition>();
		for(IPPosition ipPosition:ipPositionList){
			map.put(ipPosition.getIp(),ipPosition);
		}
		return map;
	}

	public void saveIPPosition(IPPosition ipPosition) {
		ipPositionDao.saveAsset(ipPosition);
	}

	public int delete(IPPosition ipPosition) {
		
		return ipPositionDao.deleteIP(ipPosition);
	}

	
	
}
