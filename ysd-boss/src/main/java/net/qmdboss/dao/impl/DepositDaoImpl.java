package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.DepositDao;
import net.qmdboss.entity.Deposit;
import net.qmdboss.entity.Member;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 预存款记录
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXC18C08E26DB83F5F881422AB66E5DC75
 * ============================================================================
 */

@Repository("depositDaoImpl")
public class DepositDaoImpl extends BaseDaoImpl<Deposit, Integer> implements DepositDao {

	public Pager getDepositPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("member", member));
	}
	
}