
package com.bootx.service;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;

import java.util.List;

/**
 * @author black
 */
public interface SoftImageService extends BaseService<SoftImage, Long> {

    void remove(Soft soft);

    void create(Soft soft, List<String> images);
}