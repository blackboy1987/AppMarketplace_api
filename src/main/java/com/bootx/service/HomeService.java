package com.bootx.service;

import java.util.Map;

/**
 * @author black
 */
public interface HomeService {
    Map<String,Object> load(Boolean cache);
}
