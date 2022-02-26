/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class userCoupon {
	
	private long cam_id;
	
	private long coupon_id;
	
	private String coupon_code;
	
	private int cam_kind;
	
	private String cal1;

	public long getCam_id() {
		return cam_id;
	}

	public void setCam_id(long cam_id) {
		this.cam_id = cam_id;
	}

	public long getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(long coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}

	public int getCam_kind() {
		return cam_kind;
	}

	public void setCam_kind(int cam_kind) {
		this.cam_kind = cam_kind;
	}

	public String getCal1() {
		return cal1;
	}

	public void setCal1(String cal1) {
		this.cal1 = cal1;
	}

	@Override
	public String toString() {
		return "userCoupon [cam_id=" + cam_id + ", coupon_id=" + coupon_id + ", coupon_code=" + coupon_code
				+ ", cam_kind=" + cam_kind + ", cal1=" + cal1 + "]";
	}
	
	
}