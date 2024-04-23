
package com.bootx.dao;


import com.bootx.entity.SoftDownloadLog;

public interface SoftDownloadLogDao extends BaseDao<SoftDownloadLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}