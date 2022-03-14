/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Campaign;
import ltd.newbee.mall.entity.GoodsCampaign;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.OrderCampaign;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCategoryMapper {
    int deleteByPrimaryKey(Long categoryId);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);

    GoodsCategory selectByPrimaryKey(Long categoryId);

    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    int updateByPrimaryKeySelective(GoodsCategory record);

    int updateByPrimaryKey(GoodsCategory record);

    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

    List<Campaign> findGoodsCampaignList(PageQueryUtil pageUtil);
    
    PageResult getCampaignPage(PageQueryUtil pageUtil);
    
    int getTotalCampaign(PageQueryUtil pageUtil);
    
    int insertNewCampaign(Campaign campaign);
    
    Long getMaxCampaignId();
    
    Campaign getCampaignInfo(String camName);
    
    Campaign getCampaignById(Long camId);
    
    List<Long>getCampaignId();
    
    int updateByCamId(Campaign campaign);
    
    int deleteCampaign(Integer[] ids);
    
    List<GoodsCampaign> findCampaignList(PageQueryUtil pageUtil);
    
    int getTotalGoodsCampaign(PageQueryUtil pageUtil);
    
    List<GoodsCampaign>getGoodsCampaignContent();
    
    int setNewGoodsCam(GoodsCampaign goodsCampaign);
    
    GoodsCampaign getGoodsCampaignByGoodsId(Long goodsId);
    
    int insertNewGoodsCampaign(GoodsCampaign goodsCampaign);
    
    int deleteGoodsCam(GoodsCampaign goodsCampaign);
    
    GoodsCampaign getGoodsCamById(String skuId);
    
    List<Long> getGoodsId();
    
    List<OrderCampaign> getOrderCam(Long userId);
    
    GoodsCampaign getGoodsCamByCamId (Long camId);
    

}