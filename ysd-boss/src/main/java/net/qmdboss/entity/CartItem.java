package net.qmdboss.entity;

import net.qmdboss.util.SettingUtil;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 实体类 - 购物车项
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX899B1AA9E96F0B3D0C2028AC461DBA08
 * ============================================================================
 */

@Entity
public class CartItem extends BaseEntity {

	private static final long serialVersionUID = -4241469437632553865L;
	
	private Integer quantity;// 数量
	
	private Product product;// 货品
	private Member member;// 会员

	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_cart_item_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_cart_item_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	// 获取优惠价格,若member对象为null则返回原价格
	@Transient
	public BigDecimal getPreferentialPrice() {
		if (member != null) {
			BigDecimal preferentialPrice = product.getPrice().multiply(new BigDecimal(member.getMemberRank().getPreferentialScale().toString()).divide(new BigDecimal(100)));
			return SettingUtil.setPriceScale(preferentialPrice);
		} else {
			return product.getPrice();
		}
	}
	
	// 获取小计价格
	@Transient
	public BigDecimal getSubtotalPrice() {
		BigDecimal subtotalPrice = getPreferentialPrice().multiply(new BigDecimal(quantity));
		return SettingUtil.setPriceScale(subtotalPrice);
	}

}