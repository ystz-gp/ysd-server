package net.qmdboss.dao.impl;

import net.qmdboss.dao.ReshipDao;
import net.qmdboss.entity.Reship;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao实现类 - 退货
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX6624D83301C073E40676B792A192D5EE
 * ============================================================================
 */

@Repository("reshipDaoImpl")
public class ReshipDaoImpl extends BaseDaoImpl<Reship, Integer> implements ReshipDao {
	
	@SuppressWarnings("unchecked")
	public String getLastReshipSn() {
		String hql = "from Reship as reship order by reship.createDate desc";
		List<Reship> reshipList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (reshipList != null && reshipList.size() > 0) {
			return reshipList.get(0).getReshipSn();
		} else {
			return null;
		}
	}

}