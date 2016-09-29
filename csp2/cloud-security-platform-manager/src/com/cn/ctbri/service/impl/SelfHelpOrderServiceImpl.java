package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.SelfHelpOrderDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.service.ISelfHelpOrderService;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：  自助下单层实现类
 * 版        本：  1.0
 */
@Service
public class SelfHelpOrderServiceImpl implements ISelfHelpOrderService{

    @Autowired
    SelfHelpOrderDao selfHelpOrderDao;
    /**
     * 功能描述：查询服务配置类型
     *       @time 2015-1-14
     * 返回值    ：  List<ServiceType>
     */
    public List<ServiceType> findServiceType() {
        return selfHelpOrderDao.getServiceType();
    }
    
    /**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    public List<Factory> findListFactory() {
        return selfHelpOrderDao.getFactory();
    }
    
    /**
     * 功能描述：查询服务资产
     * @param userId 
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    public List<Asset> findServiceAsset(int userId) {
        return selfHelpOrderDao.getServiceAsset(userId);
    }

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    public void insertOrder(Order order) {
        selfHelpOrderDao.save(order);
    }

    /**
     * 功能描述：查询服务
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    public List<Serv> findService() {
        return selfHelpOrderDao.getService();
    }

    /**
     * 功能描述：保存联系人
     *       @time 2015-1-19
     */
    public void insertLinkman(Linkman linkObj) {
        selfHelpOrderDao.saveLinkman(linkObj);
    }

    /**
     * 功能描述：查询漏洞个数
     *       @time 2015-3-9
     */
    public int findLeakNum(int i) {
        return selfHelpOrderDao.findLeakNum(i);
    }

    /**
     * 功能描述：查询网页数
     *       @time 2015-3-9
     */
    public int findWebPageNum() {
        return selfHelpOrderDao.findWebPageNum();
    }

    /**
     * 功能描述：检测网页数
     *       @time 2015-6-29
     */
    public int findWebSite() {
        return selfHelpOrderDao.findWebSite();
    }

    /**
     * 功能描述：断网次数
     *       @time 2015-6-29
     */
    public int findBrokenNetwork() {
        return selfHelpOrderDao.findBrokenNetwork();
    }

   //删除联系人
    public void deleteLinkman(int contactId) {
        selfHelpOrderDao.deleteLinkman(contactId);
    }

	public List findSerList(Map map) {
		return selfHelpOrderDao.findSerList(map);
	}

	public void insertAPI(Map map) {
		selfHelpOrderDao.insertAPI(map);
		
	}

	public void insertServ(Map map) {
		selfHelpOrderDao.insertServ(map);
		
	}

	public void delServ(Map map) {

		selfHelpOrderDao.delServ(map);
		
	}

	public void updateAPI(Map map) {
		selfHelpOrderDao.updateAPI(map);
	}

	public void updateServ(Map map) {
		selfHelpOrderDao.updateServ(map);
		
	}

	public List findShopCarList(String userId, int payFlag,String orderId) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findShopCarList(userId, payFlag,orderId);
	}

	public List findShopCarAPIList(String userId, int payFlag, String orderId) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findShopCarAPIList(userId, payFlag,orderId);
	}

	public void SaveOrderDetail(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.SaveOrderDetail(orderDetail);
	}

	public OrderDetail getOrderDetailById(String id, int userId,
			List assetIdsList) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.getOrderDetailById(id,userId,assetIdsList);
	}

	public OrderDetail findOrderDetailById(String id, int userId) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findOrderDetailById(id, userId);
	}
	/**
	 * 添加服务详情
	 */
	public void insertServiceDetail(ServiceDetail sd) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.insertServiceDetail(sd);
	}

	public void insertScanType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.insertScanType(map);
	}

	public String selectScanType(Map<String, Object> insertMap) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.selectScanType(insertMap);
	}

	public int selectMaxScanType(Map<String, Object> insertMap) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.selectMaxScanType(insertMap);
	}

	public ServiceDetail findServiceDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findServiceDetail(map);
	}

	public int selectParentId(String parent) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.selectParentId(parent);
	}

	public void delScanType(int serviceId) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.delScanType(serviceId);
	}

	public void updateServiceDetail(ServiceDetail sd) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.updateServiceDetail(sd);
	}

	public List<Map<String, Object>> findScanTypeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findScanTypeList(map);
	}

	public void delServDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.delServDetail(map);
	}


}
