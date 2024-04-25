
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

/**
 * Entity - 会员积分记录
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
public class MemberPointLog extends BaseEntity<Long> {

	/**
	 * 类型
	 * 0: 登录
	 * 1：
	 */
	@Column(nullable = false, updatable = false)
	private Integer type;

	/**
	 * 变动的数量
	 */
	@Column(nullable = false, updatable = false)
	private Long point;

	/**
	 * 备注
	 */
	@Column(updatable = false)
	private String memo;

	/**
	 * 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}