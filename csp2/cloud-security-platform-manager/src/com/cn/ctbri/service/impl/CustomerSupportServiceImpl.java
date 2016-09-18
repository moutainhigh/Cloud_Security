package com.cn.ctbri.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.dao.CustomerSupportDao;
import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.ICustomerSupportSevice;
import com.cn.ctbri.util.DateUtils;
/**
 * 客服支持业务层实现类
 * @author 柳青
 *
 */
@Service
public class CustomerSupportServiceImpl implements ICustomerSupportSevice {
	@Autowired
	CustomerSupportDao customerSupportDao;

	/**
	 * 获取用户信息查询tab数据
	 */
	public List<Map<String, Object>> queryUserInfo(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String username = (String)paramMap.get("username");
		String email = (String)paramMap.get("email");
		String mobile = (String)paramMap.get("mobile");
		String orderno = (String)paramMap.get("orderno");
		String assetname = (String)paramMap.get("assetname");
		String assetaddr = (String)paramMap.get("assetaddr");
		
		boolean paramIsNullFlag = checkParam(username, email, mobile, orderno, assetname, assetaddr);

		if((!StringUtils.isEmpty(username) || !StringUtils.isEmpty(email) || !StringUtils.isEmpty(mobile))
				|| paramIsNullFlag){
			List<User> userList = getUserList(username, email, mobile, 0);
			for(int i=0; i<userList.size(); i++){
				User user = userList.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				int userId = user.getId();
				if(!StringUtils.isEmpty(orderno) && (!StringUtils.isEmpty(assetname) || !StringUtils.isEmpty(assetaddr))){
					List<Order> orderList = getOrderList(assetname, assetaddr, userId, orderno);
					List<OrderAsset> assetList = getAssetList(orderno, userId, assetname, assetaddr);
					if(orderList != null && orderList.size() != 0 && assetList != null && assetList.size() != 0){
						map.put("username", StringUtils.isEmpty(user.getName())?"":user.getName());
						map.put("usercompony", StringUtils.isEmpty(user.getCompany())?"":user.getCompany());
						map.put("userindustry", StringUtils.isEmpty(user.getIndustry())?"":user.getIndustry());
						map.put("usermobile", StringUtils.isEmpty(user.getMobile())?"":user.getMobile());
						map.put("usercreatetime", user.getCreateTime()==null?"":DateUtils.dateToString(user.getCreateTime()));
						map.put("orderlist",orderList);
						map.put("assetlist", assetList);
					}
				} else if(!StringUtils.isEmpty(orderno)){
					List<Order> orderList = getOrderList(assetname, assetaddr, userId, orderno);
					if(orderList != null && orderList.size() != 0){
						List<OrderAsset> assetList = getAssetList(orderno, userId, assetname, assetaddr);
						map.put("username", StringUtils.isEmpty(user.getName())?"":user.getName());
						map.put("usercompony", StringUtils.isEmpty(user.getCompany())?"":user.getCompany());
						map.put("userindustry", StringUtils.isEmpty(user.getIndustry())?"":user.getIndustry());
						map.put("usermobile", StringUtils.isEmpty(user.getMobile())?"":user.getMobile());
						map.put("usercreatetime", user.getCreateTime()==null?"":DateUtils.dateToString(user.getCreateTime()));
						map.put("orderlist",orderList);
						map.put("assetlist", assetList);
					}
				} else if(!StringUtils.isEmpty(assetname) || !StringUtils.isEmpty(assetaddr)){
					List<OrderAsset> assetList = getAssetList(orderno, userId, assetname, assetaddr);
					if(assetList != null && assetList.size() != 0){
						List<Order> orderList = getOrderList(assetname, assetaddr, userId, orderno);
						map.put("username", StringUtils.isEmpty(user.getName())?"":user.getName());
						map.put("usercompony", StringUtils.isEmpty(user.getCompany())?"":user.getCompany());
						map.put("userindustry", StringUtils.isEmpty(user.getIndustry())?"":user.getIndustry());
						map.put("usermobile", StringUtils.isEmpty(user.getMobile())?"":user.getMobile());
						map.put("usercreatetime", user.getCreateTime()==null?"":DateUtils.dateToString(user.getCreateTime()));
						map.put("orderlist",orderList);
						map.put("assetlist", assetList);
					}
				} else {
					List<Order> orderList = getOrderList(assetname, assetaddr, userId, orderno);
					List<OrderAsset> assetList = getAssetList(orderno, userId, assetname, assetaddr);
					map.put("username", StringUtils.isEmpty(user.getName())?"":user.getName());
					map.put("usercompony", StringUtils.isEmpty(user.getCompany())?"":user.getCompany());
					map.put("userindustry", StringUtils.isEmpty(user.getIndustry())?"":user.getIndustry());
					map.put("usermobile", StringUtils.isEmpty(user.getMobile())?"":user.getMobile());
					map.put("usercreatetime", user.getCreateTime()==null?"":DateUtils.dateToString(user.getCreateTime()));
					map.put("orderlist",orderList);
					map.put("assetlist", assetList);
				}
				if(!map.isEmpty()){
					list.add(map);
				}
			}
			return list;
		} else if(!StringUtils.isEmpty(orderno)){
			List<Order> orderList = getOrderList(assetname, assetaddr, 0, orderno);
			List<Integer> userIdList = new ArrayList<Integer>();
			for(int i=0; i<orderList.size(); i++){
				Map<String, Object> map = new HashMap<String, Object>();
				if(i == 0 || orderList.get(i).getUserId() != orderList.get(i-1).getUserId()){
					List<User> userList = getUserList(username, email, mobile, orderList.get(i).getUserId());
					if(!userList.isEmpty() && !userIdList.contains((Integer) userList.get(0).getId())){
						List<OrderAsset> assets = getAssetList(orderno,userList.get(0).getId(), assetname, assetaddr);
						if(!StringUtils.isEmpty(assetname) || !StringUtils.isEmpty(assetaddr)){
							if(assets != null && assets.size() != 0){
								List<Order> orders = getOrderList(assetname, assetaddr, userList.get(0).getId(), orderno);
								map.put("username", StringUtils.isEmpty(userList.get(0).getName())?"":userList.get(0).getName());
								map.put("usercompony", StringUtils.isEmpty(userList.get(0).getCompany())?"":userList.get(0).getCompany());
								map.put("userindustry", StringUtils.isEmpty(userList.get(0).getIndustry())?"":userList.get(0).getIndustry());
								map.put("usermobile", StringUtils.isEmpty(userList.get(0).getMobile())?"":userList.get(0).getMobile());
								map.put("usercreatetime", userList.get(0).getCreateTime()==null?"":DateUtils.dateToString(userList.get(0).getCreateTime()));
								map.put("orderlist", orders);
								map.put("assetlist", assets);
								
								userIdList.add(userList.get(0).getId());
							}				
						} else {
							List<Order> orders = getOrderList(assetname, assetaddr, userList.get(0).getId(), orderno);
							map.put("username", StringUtils.isEmpty(userList.get(0).getName())?"":userList.get(0).getName());
							map.put("usercompony", StringUtils.isEmpty(userList.get(0).getCompany())?"":userList.get(0).getCompany());
							map.put("userindustry", StringUtils.isEmpty(userList.get(0).getIndustry())?"":userList.get(0).getIndustry());
							map.put("usermobile", StringUtils.isEmpty(userList.get(0).getMobile())?"":userList.get(0).getMobile());
							map.put("usercreatetime", userList.get(0).getCreateTime()==null?"":DateUtils.dateToString(userList.get(0).getCreateTime()));
							map.put("orderlist", orders);
							map.put("assetlist", assets);
							
							userIdList.add(userList.get(0).getId());
						}
					}

				}
				if(!map.isEmpty()){
					list.add(map);
				}	
			}
			return list;
		} else if(!StringUtils.isEmpty(assetname) || !StringUtils.isEmpty(assetaddr)){
			List<OrderAsset> assetList = getAssetList(orderno, 0, assetname, assetaddr);
			List<Integer> userIdList = new ArrayList<Integer>();
			for(int i=0; i<assetList.size(); i++){
				Map<String, Object> map = new HashMap<String, Object>();
				List<User> userList = getUserListByAssetId(assetList.get(i).getAssetId());

				if(!userList.isEmpty() && !userIdList.contains((Integer) userList.get(0).getId())){
					List<Order> orders = getOrderList(assetname, assetaddr, userList.get(0).getId(), null);
					List<OrderAsset> assets = getAssetList(null, userList.get(0).getId(), assetname, assetaddr);
					map.put("username", StringUtils.isEmpty(userList.get(0).getName())?"":userList.get(0).getName());
					map.put("usercompony", StringUtils.isEmpty(userList.get(0).getCompany())?"":userList.get(0).getCompany());
					map.put("userindustry", StringUtils.isEmpty(userList.get(0).getIndustry())?"":userList.get(0).getIndustry());
					map.put("usermobile", StringUtils.isEmpty(userList.get(0).getMobile())?"":userList.get(0).getMobile());
					map.put("usercreatetime", userList.get(0).getCreateTime()==null?"":DateUtils.dateToString(userList.get(0).getCreateTime()));
					map.put("assetlist", assets);
					map.put("orderlist", orders);

					userIdList.add(userList.get(0).getId());
				}

				if(!map.isEmpty()){
					list.add(map);
				}
			}
			return list;
		}
		return list;
	}
	/**
	 * 参数是否为空check 
	 * @param username
	 * @param email
	 * @param mobile
	 * @param orderno
	 * @param assetname
	 * @param assetaddr
	 * @return 参数全部为空 ：true 否则：false
	 */
	private boolean checkParam(String username, String email, String mobile,
			String orderno, String assetname, String assetaddr) {
		if(StringUtils.isEmpty(username) && StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)
				&& StringUtils.isEmpty(orderno) && StringUtils.isEmpty(assetname) && StringUtils.isEmpty(assetaddr)){
			return true;
		}
		return false;
	}

	/**
	 * 查询符合条件的订单列表
	 * @param assetname
	 * @param assetaddr
	 * @param id
	 * @param orderno
	 * @return
	 */
	private List<Order> getOrderList(String assetname, String assetaddr, int id,String orderno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assetname", assetname);
		map.put("assetaddr", assetaddr);
		map.put("userId", id);
		map.put("orderno", orderno);
		List<Order> orders = customerSupportDao.querOrderInfo(map);
		return orders;
	}

	/**
	 * 查询符合条件的资产信息列表
	 * @param orderno
	 * @param id
	 * @param assetname
	 * @param assetaddr
	 * @return
	 */
	private List<OrderAsset> getAssetList(String orderno, int id, String assetname, String assetaddr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderno", orderno);
		map.put("userId", id);
		map.put("assetname", assetname);
		map.put("assetaddr", assetaddr);
		List<OrderAsset> assets = customerSupportDao.queryAssetInfo(map);
		return assets;
	}
	/**
	 * 获取用户信息列表
	 * @param name
	 * @param email
	 * @param mobile
	 * @param id
	 * @return
	 */
	private List<User> getUserList(String name, String email, String mobile, int id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("email", email);
		map.put("mobile", mobile);
		map.put("id", id);
		List<User> users = customerSupportDao.queryUserInfo(map);
		return users;
	}
	/**
	 * 根据资产id，获取用户列表
	 * @param assetId
	 * @return
	 */
	private List<User> getUserListByAssetId(int assetId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assetId", assetId);
		List<User> users = customerSupportDao.getUserInfoByAssetId(map);
		return users;
	}

	/**
	 * 获取订单查询tab信息
	 */
	public List<Order> queryOrderInfo(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<Order> orders = customerSupportDao.getOrderInfo(paramMap);
		return orders;
	}

}
