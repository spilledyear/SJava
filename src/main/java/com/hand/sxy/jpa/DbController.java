package com.hand.sxy.jpa;

/**
 * @author spilledyear
 * @date 2018/2/9 14:07
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mydb")
public class DbController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/getUsers")
    public List<Map<String, Object>> getDbType() {
        String sql = "SELECT BRANCH_BANK_ID, BRANCH_BANK_NUM FROM qf_ce_branch_bank";
        Map<String, Long> numberMap = new HashMap<>();

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            String key = map.get("BRANCH_BANK_NUM").toString();
            Long value = Long.parseLong(map.get("BRANCH_BANK_ID").toString());
            if (key != null && value != null && numberMap.get(key) == null) {
                numberMap.put(key, value);
            }
        }

        if (list.size() != numberMap.size()) {
            return null;
        }

        Map<String, String> employeeMap = new HashMap<>();
        sql = "SELECT BRANCH_BANK_ID FROM qf_pub_employee GROUP By BRANCH_BANK_ID";
        List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : employeeList) {
            employeeMap.put(map.get("BRANCH_BANK_ID").toString(), map.get("BRANCH_BANK_ID").toString());
        }
        if (employeeMap.size() != employeeList.size()) {
            return null;
        }

        System.out.println("需要更新的员工数 为" + employeeList.size() + " 条");

        String updateSql = "UPDATE qf_pub_employee SET BRANCH_BANK_ID = ? WHERE BRANCH_BANK_ID = ?";

        int count = 0;
        for (String key : numberMap.keySet()) {
            if (employeeMap.get(key) != null) {
                Long value = numberMap.get(key);
                int i = jdbcTemplate.update(updateSql, new Object[]{value, key});
                System.out.println((count++) + "     BRANCH_BANK_ID 为   " + key + "  的员工更新为  " + value + "   , 共   " + i + "   条");
            }
        }
        System.out.println("执行成功");
        return new ArrayList<>();
    }
}
