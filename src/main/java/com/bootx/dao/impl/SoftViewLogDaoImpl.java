
package com.bootx.dao.impl;

import com.bootx.dao.SoftViewLogDao;
import com.bootx.entity.SoftViewLog;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class SoftViewLogDaoImpl extends BaseDaoImpl<SoftViewLog, Long> implements SoftViewLogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from SoftViewLog softViewLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}