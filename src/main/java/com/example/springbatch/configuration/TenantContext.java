/**
 * 
 */
package com.example.springbatch.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 86211
 *
 */
public class TenantContext {

    private static List<String> tenantList = new ArrayList<String>();

    private static Map<Object, Object> allDataSources = new HashMap<>();

    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<String>();

//    private static ThreadLocal<String> uuid = new InheritableThreadLocal<String>();
//
//    public static ThreadLocal<String> getUuid() {
//        return uuid;
//    }
//
//    public static void setUuid(String uuid) {
//        TenantContext.uuid.set(uuid);
//    }

    public static List<String> getTenantList() {
        return tenantList;
    }

    public static Map<Object, Object> getAllDataSources() {
        return allDataSources;
    }

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void updateTenantList(Collection<String> tenants) {
        tenantList.clear();
        tenantList.addAll(tenants);
    }
}
