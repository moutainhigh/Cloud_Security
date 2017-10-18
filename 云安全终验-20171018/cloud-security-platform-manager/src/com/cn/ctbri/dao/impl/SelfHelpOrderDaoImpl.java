package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.SelfHelpOrderDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.entity.ServiceType;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：   自助下单数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class SelfHelpOrderDaoImpl extends DaoCommon implements SelfHelpOrderDao{

	
	/**
	 * 功        能：SelfHelpOrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.SelfHelpOrderMapper.";
	/**
     * 功        能：ServMapper命名空间
     */
	private String nv = "com.cn.ctbri.entity.ServMapper.";
	
	/**
     * 功能描述：查询服务配置类型
     *       @time 2015-1-14
     * 返回值    ：  List<ServiceType>
     */
	public List<ServiceType> getServiceType() {
		return this.getSqlSession().selectList(ns + "findServiceType");
	}

	/**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    public List<Factory> getFactory() {
        return this.getSqlSession().selectList(ns + "findFactory");
    }

    /**
     * 功能描述：查询服务资产
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    public List<Asset> getServiceAsset(int userId ) {
        return this.getSqlSession().selectList(ns + "findServiceAsset",userId);
    }

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    public void save(Order order) {
        this.getSqlSession().insert(ns + "insert", order);
    }

    /**
     * 功能描述：查询服务
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    public List<Serv> getService() {
        return this.getSqlSession().selectList(nv + "list");
    }

    /**
     * 功能描述：保存联系人
     *       @time 2015-1-19
     */
    public void saveLinkman(Linkman linkObj) {
        this.getSqlSession().insert(ns + "insertLinkman", linkObj);
    }

    /**
     * 功能描述：查询漏洞个数
     *       @time 2015-3-9
     */
    public int findLeakNum(int i) {
        List<Alarm> leakList = this.getSqlSession().selectList(ns + "findLeakNum",i);
        if(leakList!=null&&leakList.size()>0){
        	if(leakList.get(0)!=null){
        	 	 return leakList.get(0).getCount();
        	}
        }
        return 0;
    }

    /**
     * 功能描述：查询网页数
     *       @time 2015-3-9
     */
    public int findWebPageNum() {
        List<HashMap<String, Object>> webPageList = this.getSqlSession().selectList(ns + "findWebPageNum");
        double i=0;
        if(webPageList!=null&&webPageList.size()>0){
           if( webPageList.get(0)!=null){
        	   i = (Double) webPageList.get(0).get("url");
           }
        	
        }
        return (int)i;
    }

    /**
     * 功能描述：检测网页数
     *       @time 2015-6-29
     */
    public int findWebSite() {
        List webSiteList = this.getSqlSession().selectList(ns + "findWebSite");
        return webSiteList.size();
    }

    /**
     * 功能描述：断网次数
     *       @time 2015-6-29
     */
    public int findBrokenNetwork() {
        List brokenNetworkList = this.getSqlSession().selectList(ns + "findBrokenNetwork");
        return brokenNetworkList.size();
    }

    public void deleteLinkman(int contactId) {
        this.getSqlSession().delete(ns + "deleteLinkman",contactId);
    }

	public List findSerList(Map map) {
		return this.getSqlSession().selectList(nv + "findSerList", map);
	}

	public void insertAPI(Map map) {
		this.getSqlSession().insert(nv+"insertAPI",map);		
	}

	public void insertServ(Map map) {
		this.getSqlSession().insert(nv+"insertServ",map);		
	}

	public void delServ(String servId) {
		this.getSqlSession().delete(nv+"delServ",servId);
		
	}
	
	public void delServAPI(String servId) {
		this.getSqlSession().delete(nv+"delServAPI",servId);
		
	}

	public void updateAPI(Map map) {
		this.getSqlSession().update(nv+"updateAPI",map);
	}

	public void updateServ(Map map) {
		this.getSqlSession().update(nv+"updateServ",map);
		
	}

	public List findShopCarList(String userId, int payFlag, String orderId) {
		 Map carMap = new HashMap();
		 carMap.put("userId", userId);
		 carMap.put("payFlag", payFlag);
		 carMap.put("orderId", orderId);
		 return this.getSqlSession().selectList(ns + "findShopCarList",carMap);
	}

	public List findShopCarAPIList(String userId, int payFlag, String orderId) {
		Map carMap = new HashMap();
		 carMap.put("userId", userId);
		 carMap.put("payFlag", payFlag);
		 carMap.put("orderId", orderId);
		 return this.getSqlSession().selectList(ns + "findShopCarAPIList",carMap);
	}

	public void SaveOrderDetail(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(ns+"SaveOrderDetail",orderDetail);
	}

	public OrderDetail getOrderDetailById(String id, int userId,
			List assetIdsList) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		map.put("assetIdsList", assetIdsList);
		return this.getSqlSession().selectOne(ns+"getOrderDetailById", map);
	}

	public OrderDetail findOrderDetailById(String id, int userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return this.getSqlSession().selectOne(ns+"findOrderDetailById", map);
	}

	public void insertServiceDetail(ServiceDetail sd) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(nv+"insertServiceDetail", sd);
	}

	public void insertScanType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(nv+"insertScanType", map);
	}

	public String selectScanType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(nv+"selectScanType",map);
	}

	public int selectMaxScanType(Map<String, Object> insertMap) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(nv+"selectMaxScanType", insertMap);
	}

	public ServiceDetail findServiceDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(nv+"findServiceDetail", map);
	}

	public int selectParentId(String parent) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(nv+"selectParentId",parent);
	}

	public void delScanType(int serviceId) {
		// TODO Auto-generated method stub
		this.getSqlSession().delete(nv+"delScanType", serviceId);
	}

	public void updateServiceDetail(ServiceDetail sd) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(nv+"updateServiceDetail", sd);
	}

	public List<Map<String, Object>> findScanTypeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(nv+"findScanTypeList",map);
	}

	public void delServDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		this.getSqlSession().delete(nv+"delServDetail", map);
	}

	public int findWafPageNum() {
		// TODO Auto-generated method stub
	    List brokenNetworkList = this.getSqlSession().selectList(ns + "findWafPageNum");
		return brokenNetworkList.size();
	}


	
}
