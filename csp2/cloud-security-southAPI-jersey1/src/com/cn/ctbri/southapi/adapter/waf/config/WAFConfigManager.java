package com.cn.ctbri.southapi.adapter.waf.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.ctbri.southapi.adapter.waf.syslog.WAFSyslogConfig;


public class WAFConfigManager {
	public String DEFAULT_FILE_WAF_CONFIG = "../conf/WafConfig.xml";
	
	public static HashMap<Integer,WAFConfigDeviceGroup> mapWAFConfigDeviceManager = new HashMap<Integer,WAFConfigDeviceGroup>();  //Integer : ResourceID
	public static HashMap<String,WAFConfigSyslogGroup> mapWAFConfigSyslogManager = new HashMap<String,WAFConfigSyslogGroup>();    //String : type
	
	public static HashMap<String,WAFConfigDevice> mapWAFConfigDeviceList = new HashMap<String,WAFConfigDevice>();  //String : ip addr
	
	
	/*
	 * Load WAF Config Information
	 */
	public boolean loadWAFConfig() {
		
		return loadWAFConfig(DEFAULT_FILE_WAF_CONFIG);
	}
	
	public boolean loadWAFConfig(String fileName) {
		
		try {
			//System.out.println(System.getProperty("user.dir"));

			SAXReader reader = new SAXReader();
			Document document = reader.read(fileName);
			
			//For device manager group
			List<?> listWAFDeviceGroup = document.selectNodes("/WAF_CONFIG/WAFDeviceManage/WAFDeviceGroup");
			loadWAFConfigDeviceGroup(listWAFDeviceGroup);
			
			//For syslog dispose group 
			List<?> listSyslogGroup = document.selectNodes("/WAF_CONFIG/SyslogGroup");
			loadWAFConfigSyslogGroup(listSyslogGroup);
		
			//For syslog server config
			List<?> listSyslogServer = document.selectNodes("/WAF_CONFIG/SyslogServer");
			loadWAFConfigSyslogServer(listSyslogServer);

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return true;
	}

	/*
	 * For Device Group
	 */
	private boolean loadWAFConfigDeviceGroup(List<?> list) {
		if ( list == null || list.size() <=0 ) return false;
		
		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element elementWAFDeviceGroup = (Element) iterator.next();
				if (elementWAFDeviceGroup == null ) continue;
				
				WAFConfigDeviceGroup wafConfigDeviceGroup = new WAFConfigDeviceGroup();	
				String resourceId = elementWAFDeviceGroup.elementText("ResourceID");
				if ( resourceId == null || "".equals(resourceId)) return false;
				
				wafConfigDeviceGroup.resourceID = Integer.parseInt(resourceId);		
				wafConfigDeviceGroup.resourceURI = (elementWAFDeviceGroup.elementText("ResourceURI") == null ? "" : elementWAFDeviceGroup.elementText("ResourceURI"));
				wafConfigDeviceGroup.deployMode = (elementWAFDeviceGroup.elementText("DeployMode") == null ? "" : elementWAFDeviceGroup.elementText("DeployMode"));

				List<?> listWAFDevice  = elementWAFDeviceGroup.selectNodes("WAFDeviceList/WAFDevice");
				if ( !loadWAFConfigDevice(listWAFDevice,wafConfigDeviceGroup) ) continue;
				
				mapWAFConfigDeviceManager.put(wafConfigDeviceGroup.resourceID, wafConfigDeviceGroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * For Device
	 */
	private boolean loadWAFConfigDevice(List<?> listWAFDevice,WAFConfigDeviceGroup wafConfigDeviceGroup) {
		if ( listWAFDevice == null || listWAFDevice.size() <=0 ) return false;
		
		try {
			Iterator<?> iterator = listWAFDevice.iterator();
			while (iterator.hasNext()) {
				Element elementWAFDevice = (Element) iterator.next();
				if (elementWAFDevice == null ) continue;
				
				WAFConfigDevice wafConfigDevice = new WAFConfigDevice();
				
				//For Config Device Group
				wafConfigDevice.setResourceID(wafConfigDeviceGroup.getResourceID());
				wafConfigDevice.setResourceURI(wafConfigDeviceGroup.getResourceURI());
				wafConfigDevice.setDeployMode(wafConfigDeviceGroup.getDeployMode());
				
				
				//For Config Device 
				String devID = elementWAFDevice.elementText("WAFDevID");
				if ( devID == null || "".equals(devID)) return false;
				
				wafConfigDevice.setDeviceID(Integer.parseInt(devID));		
				wafConfigDevice.setFactory(elementWAFDevice.elementText("WAFFactory"));
				wafConfigDevice.setFactoryName(elementWAFDevice.elementText("WAFFactoryName"));
				wafConfigDevice.setDevicePhyIP(elementWAFDevice.elementText("WAFDevPhyIP"));
				wafConfigDevice.setDevicePhyIP(elementWAFDevice.elementText("WAFDevPhyIP"));
				
				String strPublicIPList = elementWAFDevice.elementText("WAFPublicIPList");
				String arrayPublicIPList[] = null;
				
				if ( strPublicIPList != null && !"".equals(strPublicIPList) ) {
					arrayPublicIPList = strPublicIPList.split(";");
				}		
				
				if ( arrayPublicIPList == null  ) {  //default : device phy ip
					arrayPublicIPList = new String[1]; 
					arrayPublicIPList[0] = wafConfigDevice.getDevicePhyIP();
				} 
				wafConfigDevice.setDevicePublicIPList(arrayPublicIPList);

				//For WAFDevAPI
				Element elementWAFDeviceAPI = elementWAFDevice.element("WAFDevAPI");
				wafConfigDevice.setApiAddr(elementWAFDeviceAPI.elementText("APIAddr"));
				wafConfigDevice.setApiKey(elementWAFDeviceAPI.elementText("APIKey"));
				wafConfigDevice.setApiValue(elementWAFDeviceAPI.elementText("APIValue"));
				wafConfigDevice.setApiUserName(elementWAFDeviceAPI.elementText("APIUserName"));
				wafConfigDevice.setApiPwd(elementWAFDeviceAPI.elementText("APIPwd"));
			
				
				//For WAFDevSyslog
				Element elementWAFDevSyslog = elementWAFDevice.element("WAFDevSyslog");
				wafConfigDevice.setSyslogIdentifyType(elementWAFDevSyslog.attributeValue("identifyType"));
				wafConfigDevice.setSyslogVer(elementWAFDevSyslog.attributeValue("syslogVer"));
				wafConfigDevice.setSyslogCode(elementWAFDevSyslog.attributeValue("syslogCode"));
				wafConfigDevice.setSyslogDeviceTag(elementWAFDevSyslog.elementText("WAFDevTag"));
		
				wafConfigDeviceGroup.mapWAFConfigDevice.put(Integer.parseInt(devID), wafConfigDevice);
				
				//Store into Device List
				if ( wafConfigDevice.getDevicePhyIP() != null )
					mapWAFConfigDeviceList.put( (wafConfigDevice.getDevicePhyIP()).toLowerCase(), wafConfigDevice);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;		
	}

	/*
	 * For Syslog Group
	 */
	private boolean loadWAFConfigSyslogGroup(List<?> list) {
		if (list == null || list.size() <= 0)
			return false;

		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element syslogGroupElement = (Element) iterator.next();
				if (syslogGroupElement == null)
					continue;

				String type = syslogGroupElement.attributeValue("type");
				if (null == type || "".equals(type))
					return false;

				WAFConfigSyslogGroup wafConfigSyslogGroup = new WAFConfigSyslogGroup();
				wafConfigSyslogGroup.setType(type);
				List<?> listSyslog = syslogGroupElement.selectNodes("Syslog");

				if (!loadWAFConfigSyslog(listSyslog, wafConfigSyslogGroup))
					continue;
				mapWAFConfigSyslogManager.put(type.toLowerCase(), wafConfigSyslogGroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * For Syslog
	 */
	private boolean loadWAFConfigSyslog(List<?> list,
			WAFConfigSyslogGroup wafConfigSyslogGroup) {
		if (list == null || list.size() <= 0)
			return false;

		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element syslogElement = (Element) iterator.next();
				if (syslogElement == null)
					continue;
				String syslogFactory = syslogElement.attributeValue("factory");
				if (syslogFactory == null || "".equals(syslogFactory))
					return false;

				WAFConfigSyslog wafConfigSyslog = new WAFConfigSyslog();
				wafConfigSyslog.factory = syslogFactory;
				wafConfigSyslog.syslogVer =  syslogElement.attributeValue("ver");
				wafConfigSyslog.syslogRegTag = syslogElement.attributeValue("regexTag");

				List<?> listSyslogItem = syslogElement.selectNodes("WafLog");
				if (!loadSyslogItem(listSyslogItem, wafConfigSyslog))
					continue;

				wafConfigSyslogGroup.listWAFConfigSyslog.add(wafConfigSyslog);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * For Syslog Item
	 */
	private boolean loadSyslogItem(List<?> list, WAFConfigSyslog wafConfigSyslog) {
		if (list == null || list.size() <= 0)
			return false;

		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element syslogItemElement = (Element) iterator.next();
				if (syslogItemElement == null)
					continue;

				String item = syslogItemElement.attributeValue("item");
				if (item == null || "".equals(item))
					return false;

				WAFConfigSyslogItem wafConfigSyslogItem = new WAFConfigSyslogItem();
				wafConfigSyslogItem.item = item;
				wafConfigSyslogItem.tag = syslogItemElement.attributeValue("tag");
				wafConfigSyslogItem.sqlForDB = syslogItemElement.element("dbopt").attributeValue("sql");

				Element syslogItemMatchElement = syslogItemElement.element("match");
				wafConfigSyslogItem.matchRegx = syslogItemMatchElement.attributeValue("reg");

				//For Precondition
				List<?> listSyslogItemPre = syslogItemElement.selectNodes("precondition/pre");
				loadSyslogItemPre(listSyslogItemPre,wafConfigSyslogItem);				
				
				//For trans
				List<?> listSyslogItemTrans = syslogItemElement.selectNodes("match/trans");
				loadSyslogItemTrans(listSyslogItemTrans,wafConfigSyslogItem);

				wafConfigSyslogItem.buildSyslogItemTrans();
				wafConfigSyslog.mapWAFConfigSyslogItem.put(item.toLowerCase(),wafConfigSyslogItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * For Syslog Item Trans
	 */
	private boolean loadSyslogItemPre(List<?> list,	WAFConfigSyslogItem wafConfigSyslogItem) {
		if (list == null || list.size() <= 0)
			return false;

		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element preElement = (Element) iterator.next();
				if (preElement == null)
					continue;

				String idString = preElement.attributeValue("id");
				if (idString == null || "".equals(idString))
					continue;

				WAFConfigSyslogItemPre wafConfigSyslogItemPre = new WAFConfigSyslogItemPre();
				wafConfigSyslogItemPre.setId(idString);
				wafConfigSyslogItemPre.setObjmethod(preElement.attributeValue("method"));
				
				String argCount = preElement.attributeValue("argcount");
				if ( argCount != null && !"".equals(argCount) ) {
					wafConfigSyslogItemPre.setArgCount(Integer.parseInt(argCount));
					for ( int i=0; i<Integer.parseInt(argCount); i++ ) {
						if ( i>= 10 ) break; //Only max 10 arg
						
						String arg = preElement.attributeValue("arg"+i);
						wafConfigSyslogItemPre.getArrayArg()[i] = arg;						
					}
				}

				wafConfigSyslogItem.listWAFConfigSyslogItemPre.add(wafConfigSyslogItemPre);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/*
	 * For Syslog Item Trans
	 */
	private boolean loadSyslogItemTrans(List<?> list,
			WAFConfigSyslogItem wafConfigSyslogItem) {
		if (list == null || list.size() <= 0)
			return false;

		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element transElement = (Element) iterator.next();
				if (transElement == null)
					continue;

				String idString = transElement.attributeValue("id");
				if (idString == null || "".equals(idString))
					continue;

				WAFConfigSyslogItemTrans wafConfigSyslogItemTrans = new WAFConfigSyslogItemTrans();
				wafConfigSyslogItemTrans.setId(idString);
				wafConfigSyslogItemTrans.setName(transElement.attributeValue("name"));
				wafConfigSyslogItemTrans.setExpress(transElement.attributeValue("express"));

				wafConfigSyslogItem.listWAFConfigSyslogItemTrans.add(wafConfigSyslogItemTrans);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	/*
	 * For Syslog Server
	 */
	private boolean loadWAFConfigSyslogServer(List<?> list) {
		if ( list == null || list.size() <=0 ) return false;
		
		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element elementSyslogServer = (Element) iterator.next();
				if (elementSyslogServer == null ) continue;
				
				String serverIP = elementSyslogServer.attributeValue("serverIP");
				if ((serverIP != null)  && (!"".equals(serverIP)) ) WAFSyslogConfig.setSyslogServerIP(serverIP);
					
				
				String serverPort = elementSyslogServer.attributeValue("port");
				if ((serverPort != null)  && (!"".equals(serverPort)) ) WAFSyslogConfig.setSyslogServerPort(Integer.parseInt(serverPort));
								
				String threadPoolSize = elementSyslogServer.attributeValue("threadPoolSize");
				if ((threadPoolSize != null)  && (!"".equals(threadPoolSize)) ) WAFSyslogConfig.setThreadPoolSize(Integer.parseInt(threadPoolSize));
				
				String disposeBlockSize = elementSyslogServer.attributeValue("disposeBlockSize");
				if ((disposeBlockSize != null)  && (!"".equals(disposeBlockSize)) ) WAFSyslogConfig.setDisposeBlockSize(Integer.parseInt(disposeBlockSize));

				String debug = elementSyslogServer.attributeValue("debug");
				if ((debug != null)  && ("true".equalsIgnoreCase(debug)) ) WAFSyslogConfig.setDebugMode(true);
			
				String packetDisposeMode = elementSyslogServer.attributeValue("packetDisposeMode");
				if ( packetDisposeMode == null || "".equals(packetDisposeMode) ) 
					packetDisposeMode =  WAFSyslogConfig.DEFAULT_PACKET_DISPOSEMODE_STORE;	
				
				WAFSyslogConfig.setPacketDisposeMode(packetDisposeMode);
				
				List<?> listSyslogServerStore  = elementSyslogServer.selectNodes("Store");
				if ( listSyslogServerStore != null )
					loadWAFConfigSyslogServerStore(listSyslogServerStore);				

				
				List<?> listSyslogServerRelay = elementSyslogServer.selectNodes("Relay");
				if ( listSyslogServerRelay != null )
					loadWAFConfigSyslogServerRelay(listSyslogServerRelay);
				
				//Only one
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	

	/*
	 * For Syslog Server Relay
	 */
	private boolean loadWAFConfigSyslogServerRelay(List<?> list) {
		if ( list == null || list.size() <=0 ) return false;
		
		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element elementSyslogServerRelay = (Element) iterator.next();
				if (elementSyslogServerRelay == null ) continue;
				
				String relayServerIP = elementSyslogServerRelay.attributeValue("relayServerIP");
				if ((relayServerIP != null)  && (!"".equals(relayServerIP)) ) WAFSyslogConfig.setRelayServerIP(relayServerIP);
					
				
				String relayPort = elementSyslogServerRelay.attributeValue("relayPort");
				if ((relayPort != null)  && (!"".equals(relayPort)) ) WAFSyslogConfig.setRelayServerPort(Integer.parseInt(relayPort));
				
				String relayMode = elementSyslogServerRelay.attributeValue("relayMode");
				if ((relayMode != null)  && (!"".equals(relayMode)) ) WAFSyslogConfig.setRelayMode(relayMode);

				String relayProtocol = elementSyslogServerRelay.attributeValue("relayProtocol");
				if ((relayProtocol != null)  && (!"".equals(relayProtocol)) ) WAFSyslogConfig.setRelayProtocol(relayProtocol);

				//Only one
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	

	/*
	 * For Syslog Server Store
	 */
	private boolean loadWAFConfigSyslogServerStore(List<?> list) {
		if ( list == null || list.size() <=0 ) return false;
		
		try {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Element elementSyslogServerStore = (Element) iterator.next();
				if (elementSyslogServerStore == null ) continue;
		
				String jdbDriver = elementSyslogServerStore.elementText("JdbDriver");
				if ((jdbDriver != null)  && (!"".equals(jdbDriver)) ) WAFSyslogConfig.setJdbDriver(jdbDriver);
								
				String jdbcURL = elementSyslogServerStore.elementText("JdbcURL");
				if ((jdbcURL != null)  && (!"".equals(jdbcURL)) ) WAFSyslogConfig.setJdbcURL(jdbcURL);

				String jdbcUser = elementSyslogServerStore.elementText("JdbcUser");
				if ((jdbcUser != null)  && (!"".equals(jdbcUser)) ) WAFSyslogConfig.setJdbcUser(jdbcUser);

				String jdbcPwd = elementSyslogServerStore.elementText("JdbcPwd");
				if ((jdbcPwd != null)  && (!"".equals(jdbcPwd)) ) WAFSyslogConfig.setJdbcPwd(jdbcPwd);

				
				//Only one
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/*
	 * 
	 */
	public static WAFConfigDevice getWAFConfigDeviceByIP(String ipAddr) {
		WAFConfigDevice wafConfigDevice = mapWAFConfigDeviceList.get(ipAddr.toLowerCase());
		if ( wafConfigDevice == null ) return null;
		
		if ( (ipAddr!= null) && (!"".equals(ipAddr)) && (ipAddr.equalsIgnoreCase(wafConfigDevice.getDevicePhyIP())) ) 
			return wafConfigDevice;
		
		return null;
	}

	/*
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static WAFConfigSyslog getWAFConfigSyslogByFactoryAndVer(String factory,String syslogVer) {
		if ( factory == null || syslogVer == null ) return null;
		
		
		WAFConfigSyslogGroup wafConfigSyslogGroup = mapWAFConfigSyslogManager.get("waf");
		if ( wafConfigSyslogGroup == null ) return null;
		
		Iterator it = wafConfigSyslogGroup.listWAFConfigSyslog.iterator();
	      while ( it.hasNext() ){
	    	  WAFConfigSyslog wafConfigSyslog = (WAFConfigSyslog)it.next();
	    	  
	    	  if ( factory.equalsIgnoreCase(wafConfigSyslog.getFactory()) && syslogVer.equalsIgnoreCase(wafConfigSyslog.getSyslogVer()))
	    		  return wafConfigSyslog;
	      }
	      
	   return null;
	
	}

	/*
	public static void main(String[] args) {
		WAFConfigManager wafConfigManager = new WAFConfigManager();
		wafConfigManager.loadWAFConfig();
		
		System.out.println(wafConfigManager.mapWAFConfigDeviceManager
				.get(10001).mapWAFConfigDevice.get(30001).getApiAddr());
		System.out.println(wafConfigManager.mapWAFConfigSyslogManager
				.get("waf").getType());
				
	}*/
}
