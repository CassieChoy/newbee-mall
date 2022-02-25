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

public class OrderCampaign {
	private long userId;
	
	private long orderId;
	
	private long camId;
	
	private Date endDate;
	
	private int camKind;
	
	private String camName;
	
	private String cal1;
	
	private int totalPrice;
	
	private Date payTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCamId() {
		return camId;
	}

	public void setCamId(long camId) {
		this.camId = camId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCamKind() {
		return camKind;
	}

	public void setCamKind(int camKind) {
		this.camKind = camKind;
	}

	public String getCamName() {
		return camName;
	}

	public void setCamName(String camName) {
		this.camName = camName;
	}

	public String getCal1() {
		return cal1;
	}

	public void setCal1(String cal1) {
		this.cal1 = cal1;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "OrderCampaign [userId=" + userId + ", orderId=" + orderId + ", camId=" + camId + ", endDate=" + endDate
				+ ", camKind=" + camKind + ", camName=" + camName + ", cal1=" + cal1 + ", totalPrice=" + totalPrice
				+ ", payTime=" + payTime + "]";
	}
	

    

	

    
}