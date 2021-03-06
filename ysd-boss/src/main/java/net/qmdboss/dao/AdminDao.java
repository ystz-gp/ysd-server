package net.qmdboss.dao;

import net.qmdboss.entity.Admin;

/**
 * Dao接口 - 管理员
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX6EC45B0FCAF1A33579C2CA7460906C3F
 * ============================================================================
 */

public interface AdminDao extends BaseDao<Admin, Integer> {
	
	/**
	 * 根据用户名判断此管理员是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取管理员,若管理员不存在,则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username);

}