
package com.bootx.service;

import com.bootx.entity.SoftDownloadLog;
import com.bootx.entity.SoftViewLog;

/**
 * @author black
 */
public interface SoftViewLogService extends BaseService<SoftDownloadLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param softViewLog
	 *            审计日志
	 */
	void create(SoftViewLog softViewLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}