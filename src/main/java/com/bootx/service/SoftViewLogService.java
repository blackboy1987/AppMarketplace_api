
package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftViewLog;

/**
 * @author black
 */
public interface SoftViewLogService extends BaseService<SoftViewLog, Long> {

	/**
	 * 清空审计日志
	 */
	void clear();

	void add(Member currentUser, Soft soft);
}