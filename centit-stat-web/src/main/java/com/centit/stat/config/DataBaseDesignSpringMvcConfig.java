package com.centit.stat.config;

import com.centit.framework.config.BaseSpringMvcConfig;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.centit.product.dbdesign.controller"},
        includeFilters = {@ComponentScan.Filter(value= org.springframework.stereotype.Controller.class)},
        useDefaultFilters = false)
public class DataBaseDesignSpringMvcConfig extends BaseSpringMvcConfig {

}
