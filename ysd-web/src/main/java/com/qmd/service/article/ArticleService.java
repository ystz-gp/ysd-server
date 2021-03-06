package com.qmd.service.article;

import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface ArticleService  extends BaseService<Article,Integer>{
	

	/**
	 * 根据文章标识查询子级
	 * @param id
	 * @return
	 */
	public List<ArticleCategory> getChildArticleCategoryByIdList(Integer id);
	
	
	/**
	 * 根据 sign 标识,id 查询分类
	 * @param sign,id
	 * @return
	 */
	public ArticleCategory getArticleCategory(Map<String,Object> map);
	
	/**
	 * 查询文章列表
	 * @param map
	 * @return
	 */
	public List<Article> getArticleByArticleCategoryId(Map<String,Object> map);
	
	/**
	 * 查询文章内容
	 */
	public Article getArticleById(Map<String,Object> map);
	
	
	/**
	 * 根据Sign 查询文章列表
	 * @param map
	 * @return
	 */
	public List<Article> getArticleBySign(Map<String,Object> map);
	
	/**
	 * 根据Sign 查询文章列表【分页】
	 * @param map
	 * @return
	 */
	public Pager getArticlePagerBySign(Pager pager,Map<String,Object> map);
	
	/**
	 * 根据Sign 查询文章列表
	 * @param map
	 * @return
	 */
	public List<Article> getArticleBySignMany(Map<String,Object> map);
	
	/**
	 * 根据Sign 查询文章列表【分页】
	 * @param map
	 * @return
	 */
	public Pager getArticlePagerBySignMany(Pager pager,Map<String,Object> map);
	
	/**qxw
	 * 新版查询帮助中的数据
	 * @param map
	 * @return
	 */
	public List<Article> selectListAByMap(Map<String,Object> map);
	
	
	
	
	
}
