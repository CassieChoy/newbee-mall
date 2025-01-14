/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.GoodsLikeVO;
import ltd.newbee.mall.controller.vo.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.dao.GoodsCategoryMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.dao.NewBeeMallOrderItemMapper;
import ltd.newbee.mall.dao.NewBeeMallShoppingCartItemMapper;
import ltd.newbee.mall.entity.CartBySku;
import ltd.newbee.mall.entity.GoodsCampaign;
import ltd.newbee.mall.entity.GoodsLike;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.NewBeeMallShoppingCartItem;
import ltd.newbee.mall.entity.OrderCampaign;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NewBeeMallShoppingCartServiceImpl implements NewBeeMallShoppingCartService {

    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;

    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;
    
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    
    @Autowired
    private NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;

    @Override
    public String saveNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
        NewBeeMallShoppingCartItem temp = newBeeMallShoppingCartItemMapper.selectByUserIdAndGoodsId(newBeeMallShoppingCartItem.getUserId(), newBeeMallShoppingCartItem.getSkuId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setGoodsCount(newBeeMallShoppingCartItem.getGoodsCount());
            return updateNewBeeMallCartItem(temp);
        }
        NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKey(newBeeMallShoppingCartItem.getGoodsId());
        //商品为空
        if (newBeeMallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = newBeeMallShoppingCartItemMapper.selectCountByUserId(newBeeMallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (newBeeMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (newBeeMallShoppingCartItemMapper.insertSelective(newBeeMallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
        NewBeeMallShoppingCartItem newBeeMallShoppingCartItemUpdate = newBeeMallShoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItem.getCartItemId());
        if (newBeeMallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (newBeeMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!newBeeMallShoppingCartItemUpdate.getUserId().equals(newBeeMallShoppingCartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (newBeeMallShoppingCartItem.getGoodsCount().equals(newBeeMallShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        newBeeMallShoppingCartItemUpdate.setGoodsCount(newBeeMallShoppingCartItem.getGoodsCount());
        newBeeMallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (newBeeMallShoppingCartItemMapper.updateByPrimaryKeySelective(newBeeMallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public NewBeeMallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId) {
        return newBeeMallShoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        NewBeeMallShoppingCartItem newBeeMallShoppingCartItem = newBeeMallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (newBeeMallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(newBeeMallShoppingCartItem.getUserId())) {
            return false;
        }
        return newBeeMallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<NewBeeMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId) {
        List<NewBeeMallShoppingCartItemVO> newBeeMallShoppingCartItemVOS = new ArrayList<>();
        List<NewBeeMallShoppingCartItem> newBeeMallShoppingCartItems = newBeeMallShoppingCartItemMapper.selectByUserId(newBeeMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(newBeeMallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> newBeeMallGoodsIds = newBeeMallShoppingCartItems.stream().map(NewBeeMallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<NewBeeMallGoods> newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
            Map<Long, NewBeeMallGoods> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(newBeeMallGoods)) {
                newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(NewBeeMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            
            
            for (NewBeeMallShoppingCartItem newBeeMallShoppingCartItem : newBeeMallShoppingCartItems) {
                NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO = new NewBeeMallShoppingCartItemVO();
                BeanUtil.copyProperties(newBeeMallShoppingCartItem, newBeeMallShoppingCartItemVO);
                
                
                
                if (newBeeMallGoodsMap.containsKey(newBeeMallShoppingCartItem.getGoodsId())) {
                	String skuId = newBeeMallShoppingCartItem.getSkuId();
                    CartBySku newBeeMallGoodsTemp = newBeeMallShoppingCartItemMapper.getCartBySku(skuId);
                    newBeeMallShoppingCartItemVO.setGoodsCoverImg(newBeeMallGoodsTemp.getImage());
                    newBeeMallShoppingCartItemVO.setColor(newBeeMallGoodsTemp.getColor());
                    newBeeMallShoppingCartItemVO.setMemory(newBeeMallGoodsTemp.getMemory());
                    newBeeMallShoppingCartItemVO.setSize(newBeeMallGoodsTemp.getSize());
                    String goodsName = newBeeMallGoodsTemp.getGoodsName();
					
					  int price = newBeeMallGoodsTemp.getPrice();
					  GoodsCampaign goodsCam = goodsCategoryMapper.getGoodsCamById(newBeeMallShoppingCartItem.getSkuId());
					  if(goodsCam != null) { 
						  newBeeMallGoodsTemp.setCamId(goodsCam.getCamId());
						  newBeeMallGoodsTemp.setCamKind(goodsCam.getCamKind());
						  newBeeMallGoodsTemp.setCal1(goodsCam.getCal1());
						  int camType = goodsCam.getCamKind(); 
						  String cam = goodsCam.getCal1();
						  Double camCount; 
						  int camPrice; 
						  if(camType == 3) {
							  String[] pieces = cam.split("%"); 
							  camCount = 1.0-Double.parseDouble(pieces[0]) / 100; 
							  camPrice = (int)Math.ceil(price * camCount); 
							  newBeeMallGoodsTemp.setPrice(camPrice);; 
						  	  } 
						  else if(camType == 2) {
							  camCount = Double.parseDouble(cam); 
							  camPrice = (int)(price - camCount);
							  newBeeMallGoodsTemp.setPrice(camPrice);
							  }
						  else {
							  newBeeMallGoodsTemp.setPrice(price);
						  }
					  
					  }
					 
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    newBeeMallShoppingCartItemVO.setGoodsName(goodsName);
                    
                    newBeeMallShoppingCartItemVO.setCamId(newBeeMallGoodsTemp.getCamId());
                    
                    newBeeMallShoppingCartItemVO.setCal1(newBeeMallGoodsTemp.getCal1());
                    
                    newBeeMallShoppingCartItemVO.setCamKind(newBeeMallGoodsTemp.getCamKind());
					
					newBeeMallShoppingCartItemVO.setSellingPrice(newBeeMallGoodsTemp.getPrice());
					
                    newBeeMallShoppingCartItemVOS.add(newBeeMallShoppingCartItemVO);
                }
            }
        }
        return newBeeMallShoppingCartItemVOS;
    }

	@Override
	public List<GoodsLikeVO> getMyLikeItems(Long newBeeMallUserId) {
		List<GoodsLikeVO> GoodsLikeVOS = new ArrayList<>();
        List<GoodsLike> GoodsLikeItems = newBeeMallShoppingCartItemMapper.selectLikeByUserId(newBeeMallUserId, Constants.GOODS_LIKE_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(GoodsLikeItems)) {
            //查询商品信息并做数据转换
            List<Long> newBeeMallGoodsIds = GoodsLikeItems.stream().map(GoodsLike::getGoodsId).collect(Collectors.toList());
            List<NewBeeMallGoods> newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
            Map<Long, NewBeeMallGoods> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(newBeeMallGoods)) {
                newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(NewBeeMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (GoodsLike goodsLikeItem : GoodsLikeItems) {
            	GoodsLikeVO GoodsLikeVO = new GoodsLikeVO();
                BeanUtil.copyProperties(goodsLikeItem, GoodsLikeVO);
                
                
                if (newBeeMallGoodsMap.containsKey(goodsLikeItem.getGoodsId())) {
                	GoodsLike newBeeMallGoodsTemp = newBeeMallShoppingCartItemMapper.getLikeBySku(goodsLikeItem.getSkuId());
                	GoodsLikeVO.setImage(newBeeMallGoodsTemp.getImage());
                	GoodsLikeVO.setColor(newBeeMallGoodsTemp.getColor());
                	GoodsLikeVO.setMemory(newBeeMallGoodsTemp.getMemory());
                	GoodsLikeVO.setSize(newBeeMallGoodsTemp.getSize());
                    String goodsName = newBeeMallGoodsTemp.getGoodsName();
 					
 					  int price = newBeeMallGoodsTemp.getPrice();
 					  GoodsCampaign goodsCam = goodsCategoryMapper.getGoodsCamById(goodsLikeItem.getSkuId());
 					  if(goodsCam != null) { 
 						  int camType = goodsCam.getCamKind(); 
 						  String cam = goodsCam.getCal1();
 						  Double camCount; 
 						  int camPrice; 
 						  if(camType == 3) {
 							  String[] pieces = cam.split("%"); 
 							  camCount = 1.0-Double.parseDouble(pieces[0]) / 100; 
 							  camPrice = (int)Math.ceil(price * camCount); 
 							  newBeeMallGoodsTemp.setPrice(camPrice);; 
 						  	  } 
 						  if(camType == 2) {
 							  camCount = Double.parseDouble(cam); 
 							  camPrice = (int)(price - camCount);
 							  newBeeMallGoodsTemp.setPrice(camPrice);
 							  }
 					  
 					  }
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    GoodsLikeVO.setGoodsName(goodsName);
					
                    GoodsLikeVO.setPrice(newBeeMallGoodsTemp.getPrice());
					 
                    GoodsLikeVOS.add(GoodsLikeVO);
                }
            }
        }
        return GoodsLikeVOS;
	}

	@Override
	public int getLikeCount(Long userId) {
		
		return newBeeMallShoppingCartItemMapper.getLikeCount(userId);
	}

	@Override
	public int insertGoodsLike(GoodsLike goodsLike) {
		
		return newBeeMallShoppingCartItemMapper.insertGoodsLike(goodsLike);
	}

	@Override
	public Long getMaxLikeId() {
		
		return newBeeMallShoppingCartItemMapper.getMaxLikeId();
	}

	@Override
	public int deleteGoodsLike(Long likeId) {
		
		return newBeeMallShoppingCartItemMapper.deleteGoodsLike(likeId);
	}
}
