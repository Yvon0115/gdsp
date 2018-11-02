
package com.gdsp.integration.smartbi.utils;

import java.util.List;

import smartbi.catalogtree.ICatalogElement;
import smartbi.sdk.ClientConnector;
import smartbi.sdk.InvokeResult;
import smartbi.sdk.service.businessview.BusinessViewService;
import smartbi.sdk.service.catalog.CatalogService;

public class SmartbiConnection {
	
	public static void main(String[] args) {
		new SmartbiConnection().run();
	}

    /**

     * 测试BusinessViewService SDK

     */
    private void run() {

        String url = "http://localhost:18080/smartbi";

        String user = "admin";

        String password = "manager";

        ClientConnector conn = createClientConnector(url, user, password);

        if (conn == null) {

            System.out.println("创建应用连接器失败，请检查“连接url、用户名、密码”是否正确！");

            return;

        }

        try {
        	
            CatalogService catalogService = createCatalogService(conn);
            List<ICatalogElement> elements = catalogService.getRootElements();
            for (int i = 0; i < elements.size(); i++) {
            	System.out.println("----" + i);
            	System.out.println(elements.get(i).getId());
            	System.out.println(elements.get(i).getName());
            	System.out.println(elements.get(i).isHasChild());
            	System.out.println(elements.get(i).getType() + "**");
            	System.out.println(elements.get(i).getExtended() + "$$");
            	InvokeResult ret = conn.remoteInvoke("CatalogService", "getCatalogElementFullPath",
        				new Object[] { elements.get(i).getId() });
            	
            	System.out.println(ret.getResult());
			}
            
            String resId = "I4028812115561f6c0144956d0aa20117";
            ICatalogElement element = catalogService.getCatalogElementById(resId);
            System.out.println("#################");
            System.out.println(element.getId());
            System.out.println(element.getName());
            System.out.println(element.isHasChild());
            System.out.println(element.getType());
        } finally {

            closeClientConnector(conn);

        }

    }

    /**

     * 创建应用连接器。<br>

     * 在跟Smartbi服务器进行任何通信之前必须先创建一个ClientConnector对象，即应用链接器。<br>

     * 客户代码仅需要创建一个ClientConnector实例。

     * 

     * @return 应用连接器

     */

    public ClientConnector createClientConnector(String url, String user, String password) {

        ClientConnector conn = new ClientConnector(url);

        boolean loginOK = conn.open(user, password);

        if (loginOK) {

            return conn;

        } else {

            conn.close();

            return null;

        }

    }

    /**
     * 创建服务对象。<br>
     * 以ClientConnector为构造函数参数，创建时将之前的ClientConnector实例传入。
     * 
     * @param conn
     *            应用连接器
     * @return 服务对象
     */

    public BusinessViewService createBusinessViewService(ClientConnector conn) {
        if (conn == null) {

            return null;

        } else {

            BusinessViewService bizViewService = new BusinessViewService(conn);

            return bizViewService;

        }

    }
    /**
     * 
     * @Title: createCatalogService 
     * @Description: 创建目录服务Service
     * @param conn	连接器
     * @return CatalogService    目录服务
     */
    public CatalogService createCatalogService(ClientConnector conn){
    	if (conn == null) {

            return null;

        } else {

        	CatalogService catalogService = new CatalogService(conn);

            return catalogService;

        }
    }
    /**
     * 关闭应用连接器
     * 
     * @param conn
     *            应用连接器
     */
    public void closeClientConnector(ClientConnector conn) {
        if (conn != null) {

            conn.close();

            conn = null;

        }
    }
}