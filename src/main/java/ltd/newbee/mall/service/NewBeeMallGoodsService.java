/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.GoodsImageEntity;
import ltd.newbee.mall.entity.GoodsInfo;
import ltd.newbee.mall.entity.GoodsInfoBySku;
import ltd.newbee.mall.entity.GoodsPageEntity;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.entity.InsertGoodsQaLike;
import ltd.newbee.mall.entity.InsertGoodsReview;
import ltd.newbee.mall.entity.InsertGoodsReviewLike;
import ltd.newbee.mall.entity.InsertSearchHistoryEntity;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.RecentChkHistory;
import ltd.newbee.mall.entity.SearchHistoryEntity;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.SearchPageParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface NewBeeMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(NewBeeMallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param newBeeMallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<NewBeeMallGoods> newBeeMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(NewBeeMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    NewBeeMallGoods getNewBeeMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);
    
    GoodsInfo getGoodsInfoByPK(Long id);
    
    ArrayList<GoodsImageEntity> getGoodsImageByPk(Long id);
    
    ArrayList<NewBeeMallGoods>getGoodsPage(Map<String,Object>params2);
    

    ArrayList<GoodsQa> getGoodsQa(Map<String,Object>params);

//    ArrayList<GoodsQa> getGoodsQa(String orderBy);
    int insertGoodsQa(GoodsQa goodsQa);
    
    GoodsQa getGoodsQaPage(Long count2);
    
    ArrayList<GoodsReview> getGoodsReview(Map<String,Object>params);
    
    Double getRateAvg(Long goodsId);
    
    Long getReviewCount(Long goodsId);
    
    Long [] getRateCount(Long goodsId);

    
    int insertGoodsReview(GoodsReview review);
    
    ArrayList<String> getSearchHistory();
    
    int insertSearchHistory(InsertSearchHistoryEntity history);
    
    ArrayList<String> getGoodsName(String keyword);
    
    ArrayList<RecentChkHistory> getRecentChkHistory();
    
    Long getGoodsQaCount(Long goodsId);
    
    Long getMaxQaId(Long goodsId);
    
    int getQaLikeUserId(Map<String,Object>params);
    
    int insertGoodsQaLike(InsertGoodsQaLike qa);
    
    int insertGoodsReviewLike(InsertGoodsReviewLike reviewLike);
    
    int getReviewLikeUserId(Map<String,Object>params);
    
    Long getMaxReviewId(Long goodsId);
    
    List<GoodsInfoBySku> getGoodsInfo(long goodsId);
    
    List<String> getColor (long goodsId);
    
    List<String> getMemory (long goodsId);
    
    List<String> getSize (long goodsId);

}
