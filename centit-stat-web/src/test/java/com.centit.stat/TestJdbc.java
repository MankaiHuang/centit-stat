package com.centit.stat;

import com.alibaba.fastjson.JSONObject;
import com.centit.stat.config.ServiceConfig;
import com.centit.stat.report.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@WebAppConfiguration
public class TestJdbc {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ReportService reportService;

    @Test
    public void testSpringJdbc(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(
            "select model_name, col_name, col_type from q_query_column where model_name = ?", new Object[]{"test"});
//        List<QueryColumn> columns = jdbcTemplate.queryForList("select model_name, col_name, col_type from q_query_column", QueryColumn.class);
        System.out.println(result);
    }

    @Test
    public void testReport(){
        JSONObject result = reportService.queryData("report1");
        System.out.println(result);
    }
}
