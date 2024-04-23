
package com.bootx.dao;


import com.bootx.entity.SoftViewLog;

/**
 * @author black
 */
public interface SoftViewLogDao extends BaseDao<SoftViewLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}