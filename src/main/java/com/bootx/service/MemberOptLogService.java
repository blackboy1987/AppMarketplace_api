
package com.bootx.service;

import com.bootx.entity.MemberOptLog;

public interface MemberOptLogService extends BaseService<MemberOptLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param memberOptLog
	 *            审计日志
	 */
	void create(MemberOptLog memberOptLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}