
package com.bootx.service;

import com.bootx.entity.SoftDownloadLog;

/**
 * @author black
 */
public interface SoftDownloadLogService extends BaseService<SoftDownloadLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param softDownloadLog
	 *            审计日志
	 */
	void create(SoftDownloadLog softDownloadLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}