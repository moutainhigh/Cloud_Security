package com.ctbri.service;

import javax.ws.rs.FormParam;

import com.ctbri.exception.CloudException;

public interface AssetService {
	/**
	 * 
	* @Title: selectByPrimaryKey 
	* @Description: 根据ID查询资产
	* @param id
	* @return CsAsset    返回类型 
	* @throws
	 */
    String selectByPrimaryKey(Integer id);
    /**
     * 
    * @Title: selectByUserId 
    * @Description: 根据用户id查询资产
    * @param userid
    * @param pageNow
    * @param pageSize
    * @param @throws CloudException    设定文件 
    * @return String    返回类型 
    * @throws
     */
    String selectByUserId(@FormParam("userId")
    		Integer userId,@FormParam("pageNow")Integer pageNow,@FormParam("pageSize")Integer pageSize) throws CloudException;
    /**
     * 
    * @Title: selectByUserIdAndStatus 
    * @Description: 查询已验证和未验证资产
    * @param userid
    * @param status
    * @param pageNow
    * @param pageSize
    * @param @throws CloudException    设定文件 
    * @return String    返回类型 
    * @throws
     */
    String selectByUserIdAndStatus(@FormParam("userId")
			Integer userId,@FormParam("pageNow")Integer pageNow,
			@FormParam("pageSize")Integer pageSize,
			@FormParam("status")Integer status) throws CloudException;
    
}
