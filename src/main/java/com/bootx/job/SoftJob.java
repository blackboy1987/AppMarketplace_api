package com.bootx.job;

import com.bootx.service.CategoryService;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author black
 */
@Configuration
public class SoftJob {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SoftService softService;

    @Resource
    private JdbcTemplate jdbcTemplate;

}
