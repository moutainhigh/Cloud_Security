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
import com.cn.ctbri.entity.Serv;
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


}
