package net.qmdboss.dao;

import net.qmdboss.entity.ArticleCategory;

import java.util.List;

/**
 * Dao接口 - 文章分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD693C9587F7A89A2474E4066ECEB93DE
 * ============================================================================
 */

public interface ArticleCategoryDao extends BaseDao<ArticleCategory, Integer> {
	
	/**
	 * 判断标识是否存在（不区分大小写）
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public boolean isExistBySign(String sign);
	
	/**
	 * 根据标识获取文章分类
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public ArticleCategory getArticleCategoryBySign(String sign);
	
	/**
	 * 获取文章分类树集合
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryTree();
	
	/**
	 * 获取顶级文章分类集合
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 顶级文章分类集合
	 * 
	 */
	public List<ArticleCategory> getRootArticleCategoryList(Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param isContainParent
	 *            是否包含所有父分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory, boolean isContainParent, Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取所有子类集合,若无子类则返回null
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param isContainChildren
	 *            是否包含所有子分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory, boolean isContainChildren, Integer maxResults);

	
	/**
	 * 根据父级sign 和子级keyValue查询子级name
	 * @param sign
	 * @param keyValue
	 * @return
	 */
	public String findChildListingByKeyValue(String sign,String keyValue);
}