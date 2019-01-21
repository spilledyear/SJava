package com.zto.sxy.qf.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author spilledyear
 * @date 2018/2/8 22:24
 */
public class UpdateEmployee {
    private static DBUtil dbUtil;


    public static void main(String[] args) {
        dbUtil = DBUtil.getInstance();

        dbUtil.getConnection();
        String sql = "SELECT ITERFACE_ID, VALUE1 EMPLOYEE_NUMBER, VALUE7 BANK, VALUE8 BRANCH,  VALUE10 PROVINCE, VALUE11 CITY, VALUE20 DEPARTMENT, VALUE21 COMPANY FROM test_hscs_itf_imp_interfaces where INTERFACE_NAME = 'QF_SOA_EMPLOYEE' limit 1000";

        try {
            List<Map<String, Object>> results = dbUtil.executeQuery(sql, new ArrayList<>());
            for (Map<String, Object> recordMap : results) {
                for (String key : recordMap.keySet()) {
                    if (key.equals("")) {

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
