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
	
	private long orderItemId;
	
	private long camId;
	
	private Date endDate;
	
	private int camKind;
	
	private String camName;
	
	private String cal1;
	
	private int sellingPrice;
	
	private Date createTime;
	
	private String orderNo;
	
	private int point;
	
	private int totalPoint;
	
	private Date createDate;
	
	private int orderStatus;
	
	private int pointType;
	
	private long pointId;
	
	private Date payTime;
	
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public long getPointId() {
		return pointId;
	}

	public void setPointId(long pointId) {
		this.pointId = pointId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getPointType() {
		return pointType;
	}

	public void setPointType(int pointType) {
		this.pointType = pointType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
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

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	

	

	

    

	

    
}