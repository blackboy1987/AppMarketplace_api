
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftDownloadLog;

/**
 * @author black
 */
public interface SoftDownloadLogService extends BaseService<SoftDownloadLog, Long> {

	/**
	 * 清空审计日志
	 */
	void clear();

	void add(Member member, Soft soft);

	Page<SoftDownloadLog> findPage(Pageable pageable, Long softId);
}