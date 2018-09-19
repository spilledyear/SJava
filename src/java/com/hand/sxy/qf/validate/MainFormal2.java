package com.hand.sxy.qf.validate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author spilledyear
 * @date 2017/12/29 10:10
 */
public class MainFormal2 {
    private static final Logger LOG = Logger.getLogger("RedisUtils");

    private static final String REDIS_IP = "localhost";
    private static final String REDIS_PORT = "6379";

    /**
     * 校验字段
     */
    private static final String BANK = "BANK";
    private static final String BRANCH = "BRANCH";
    private static final String PROVINCE = "PROVINCE";
    private static final String CITY = "CITY";
    private static final String COMPANY = "COMPANY";
    private static final String DEPARTMENT = "DEPARTMENT";


    private static DBUtil dbUtil;

    public static void main(String[] args) {
        RedisUtils.init(REDIS_IP, REDIS_PORT);
        dbUtil = DBUtil.getInstance();
//        initRedis();
        printRedis();

        MainFormal2 instance = new MainFormal2();
        instance.vertify();
    }

    private static void printRedis() {
        LOG.info("银行个数：  " + RedisUtils.getList("qf.bank").size());
        LOG.info("银行个数：  " + RedisUtils.getList("qf.bank").toString());
        LOG.info("分行个数：  " + RedisUtils.getList("qf.branch").size());
        LOG.info("省份个数：  " + RedisUtils.getList("qf.province").size());
        LOG.info("城市个数：  " + RedisUtils.getList("qf.city").size());
        LOG.info("部门个数：  " + RedisUtils.getList("qf.department").size());
        LOG.info("公司个数：  " + RedisUtils.getList("qf.company").size());
    }


    /**
     * 校验
     */
    private void vertify() {
        dbUtil = DBUtil.getInstance();
        this.vertifyEmployee();
    }

    /**
     * <p>校验员工数据, 不做是否为空校验， 是否为空校验可以直接用SQL查询</p>
     */
    private void vertifyEmployee() {
        Long startTime = System.currentTimeMillis();
        LOG.info("开始从数据库获取数据");
        dbUtil.getConnection();
        String sql = "SELECT ITERFACE_ID, VALUE1 EMPLOYEE_NUMBER, VALUE7 BANK, VALUE8 BRANCH,  VALUE10 PROVINCE, VALUE11 CITY, VALUE20 DEPARTMENT, VALUE21 COMPANY FROM test_hscs_itf_imp_interfaces where INTERFACE_NAME = 'QF_SOA_EMPLOYEE' limit 1000";
        try {
            StringBuilder errorMsg = new StringBuilder("");
            StringBuilder interfaceIds = new StringBuilder("(");
            StringBuilder employeeNumers = new StringBuilder("(");

            Boolean firstFlag = true, lineFlag = false;

            List<String> branchs = RedisUtils.getList("qf.branch");
            List<String> banks = RedisUtils.getList("qf.bank");
            List<String> provinces = RedisUtils.getList("qf.province");
            List<String> citys = RedisUtils.getList("qf.city");
            List<String> companys = RedisUtils.getList("qf.company");
            List<String> departments = RedisUtils.getList("qf.department");

            List<Map<String, Object>> results = dbUtil.executeQuery(sql, new ArrayList<>());
            LOG.info("完成从数据库获取数据， " + results.size() + "  条数据用时  " + (System.currentTimeMillis() - startTime) + "  毫秒");
            startTime = System.currentTimeMillis();
            LOG.info("开始校验数据");

            for (Map<String, Object> recordMap : results) {
                firstFlag = true;
                lineFlag = false;

                for (String key : recordMap.keySet()) {
                    String iterface = "ITERFACE_ID：" + recordMap.get("ITERFACE_ID").toString();
                    String employee = "     员工编号：" + recordMap.get("EMPLOYEE_NUMBER").toString() + "\t";
                    String fieldValue = recordMap.get(key).toString();

                    if (key.equals(BANK)) {
                        if (banks.indexOf(fieldValue) == -1) {
                            lineFlag = true;
                            if (firstFlag) {
                                interfaceIds = interfaceIds.append(recordMap.get("ITERFACE_ID").toString()).append(",");
                                employeeNumers = employeeNumers.append(recordMap.get("EMPLOYEE_NUMBER").toString()).append(",");
                                errorMsg.append(iterface).append(employee).append("\t银行校验失败\t");
                                firstFlag = false;
                            } else {
                                errorMsg.append("\t银行校验失败\t");
                            }
                        }
                    }

                    if (key.equals(BRANCH)) {
                        if (branchs.indexOf(fieldValue) == -1) {
                            lineFlag = true;
                            if (firstFlag) {
                                interfaceIds = interfaceIds.append(recordMap.get("ITERFACE_ID").toString()).append(",");
                                employeeNumers = employeeNumers.append(recordMap.get("EMPLOYEE_NUMBER").toString()).append(",");
                                errorMsg.append(iterface).append(employee).append("\t分行校验失败\t");
                                firstFlag = false;
                            } else {
                                errorMsg.append("\t分行校验失败\t");
                            }
                        }
                    }

                    if (key.equals(PROVINCE)) {
                        if (provinces.indexOf(fieldValue) == -1) {
                            lineFlag = true;
                            if (firstFlag) {
                                interfaceIds = interfaceIds.append(recordMap.get("ITERFACE_ID").toString()).append(",");
                                employeeNumers = employeeNumers.append(recordMap.get("EMPLOYEE_NUMBER").toString()).append(",");
                                errorMsg.append(iterface).append(employee).append("\t省份校验失败\t");
                                firstFlag = false;
                            } else {
                                errorMsg.append("\t省份校验失败\t");
                            }
                        }
                    }

                    if (key.equals(CITY)) {
                        if (citys.indexOf(fieldValue) == -1) {
                            lineFlag = true;
                            if (firstFlag) {
                                interfaceIds = interfaceIds.append(recordMap.get("ITERFACE_ID").toString()).append(",");
                                employeeNumers = employeeNumers.append(recordMap.get("EMPLOYEE_NUMBER").toString()).append(",");
                                errorMsg.append(iterface).append(employee).append("\t城市校验失败\t");
                                firstFlag = false;
                            } else {
                                errorMsg.append("\t城市校验失败\t");
                            }
                        }
                    }

                    if (key.equals(COMPANY)) {
                        if (companys.indexOf(fieldValue) == -1) {
                            lineFlag = true;
                            if (firstFlag) {
                                interfaceIds = interfaceIds.append(recordMap.get("ITERFACE_ID").toString()).append(",");
                                employeeNumers = employeeNumers.append(recordMap.get("EMPLOYEE_NUMBER").toString()).append(",");
                                errorMsg.append(iterface).append(employee).append("\t公司校验失败\t");
                                firstFlag = false;
                            } else {
                                errorMsg.append("\t公司校验失败\t");
                            }
                        }
                    }

                    if (key.equals(DEPARTMENT)) {
                        if (departments.indexOf(fieldValue) == -1) {
                            lineFlag = true;
                            if (firstFlag) {
                                interfaceIds = interfaceIds.append(recordMap.get("ITERFACE_ID").toString()).append(",");
                                employeeNumers = employeeNumers.append(recordMap.get("EMPLOYEE_NUMBER").toString()).append(",");
                                errorMsg.append(iterface).append(employee).append("\t部门校验失败\t");
                                firstFlag = false;
                            } else {
                                errorMsg.append("\t部门校验失败\t");
                            }
                        }
                    }
                }

                if (lineFlag) {
                    errorMsg.append("\n");
                }
            }
            LOG.info("校验数据完成， " + results.size() + "  条数据用时  " + (System.currentTimeMillis() - startTime) + "  毫秒");

            errorMsg.append("\n").append("接口ID：").append(interfaceIds).append(")\n");
            errorMsg.append("\n").append("员工编号：").append(employeeNumers).append(")\n");
            this.writeFile(errorMsg.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }


    /**
     * <p> 将字符串写入 文件</p>
     *
     * @param errorMsg
     */
    private void writeFile(String errorMsg) {
        try {
            File file = new File("errorMsg.txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化 redis 数据
     */
    private static void initRedis() {
        RedisUtils.delete("qf.");
        initBank();
        initBranch();
        initProvince();
        initCity();
        initDepatment();
        initCompany();
    }


    /**
     * 初始化 分行 到redis
     */
    private static void initBank() {
        dbUtil.getConnection();
        String sql = "SELECT BANK_NAME FROM hscs_pub_bank";
        try {
            List<String> list = dbUtil.queryList(sql, "BANK_NAME");
            RedisUtils.setList("qf.bank", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }

    /**
     * 初始化 分行 到redis
     */
    private static void initBranch() {
        dbUtil.getConnection();
        String sql = "SELECT DOT_SHORT_NAME FROM qf_ce_branch_bank";
        try {
            List<String> list = dbUtil.queryList(sql, "DOT_SHORT_NAME");
            RedisUtils.setList("qf.branch", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }

    /**
     * 初始化 省份 到redis
     */
    private static void initProvince() {
        dbUtil.getConnection();
        String sql = "SELECT REGION_NAME FROM hscs_pub_regions WHERE REGION_TYPE_CODE = 'PROV'";
        try {
            List<String> list = dbUtil.queryList(sql, "REGION_NAME");
            RedisUtils.setList("qf.province", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }

    /**
     * 初始化 城市 到redis
     */
    private static void initCity() {
        dbUtil.getConnection();
        String sql = "SELECT REGION_NAME FROM hscs_pub_regions WHERE REGION_TYPE_CODE = 'CITY'";
        try {
            List<String> list = dbUtil.queryList(sql, "REGION_NAME");
            RedisUtils.setList("qf.city", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }

    /**
     * 初始化 部门 到redis
     */
    private static void initDepatment() {
        dbUtil.getConnection();
        String sql = "SELECT LEVEL_CODE FROM qf_pub_organization_level";
        try {
            List<String> list = dbUtil.queryList(sql, "LEVEL_CODE");
            RedisUtils.setList("qf.department", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }

    /**
     * 初始化 公司 到redis
     */
    private static void initCompany() {
        dbUtil.getConnection();
        String sql = "SELECT LEVEL_CODE FROM qf_pub_organization_level WHERE (ORG_FLAG = 'THR_MARKET_COM' OR ORG_FLAG = 'OPERATION_COM' OR ORG_FLAG = 'SEC_MARKET_COM')";
        try {
            List<String> list = dbUtil.queryList(sql, "LEVEL_CODE");
            RedisUtils.setList("qf.company", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }
}
