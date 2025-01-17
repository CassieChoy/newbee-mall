/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.GoodsLikeVO;
import ltd.newbee.mall.controller.vo.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.GoodsCampaign;
import ltd.newbee.mall.entity.GoodsLike;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.NewBeeMallShoppingCartItem;
import ltd.newbee.mall.entity.OrderCampaign;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Resource
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;
    
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;

    @Resource
	private NewBeeMallOrderService newBeeMallOrderService;

	
    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<NewBeeMallShoppingCartItemVO> myShoppingCartItems = newBeeMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(NewBeeMallShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                return "error/error_5xx";
            }
            //总价
            for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
            	Long goodsId = newBeeMallShoppingCartItemVO.getGoodsId();
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveNewBeeMallShoppingCartItem(@RequestBody NewBeeMallShoppingCartItem newBeeMallShoppingCartItem,
                                                 HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        newBeeMallShoppingCartItem.setUserId(user.getUserId());
        String saveResult = newBeeMallShoppingCartService.saveNewBeeMallCartItem(newBeeMallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@RequestBody NewBeeMallShoppingCartItem newBeeMallShoppingCartItem,
                                                   HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        newBeeMallShoppingCartItem.setUserId(user.getUserId());
        String updateResult = newBeeMallShoppingCartService.updateNewBeeMallCartItem(newBeeMallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{newBeeMallShoppingCartItemId}")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = newBeeMallShoppingCartService.deleteById(newBeeMallShoppingCartItemId,user.getUserId());
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        int totalPoint = 0;
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<NewBeeMallShoppingCartItemVO> myShoppingCartItems = newBeeMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
            	String skuId = newBeeMallShoppingCartItemVO.getSkuId();
            	GoodsCampaign cam = newBeeMallCategoryService.getGoodsCampaignByGoodsId(skuId);
            	if(cam!= null) {
            		newBeeMallShoppingCartItemVO.setCampaign(cam.getCamName());
            	}
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
            List<OrderCampaign> userPointItems = newBeeMallOrderService.selectUserPoint(user.getUserId());
            for(OrderCampaign userPointItem : userPointItems) {
            	int point = userPointItem.getPoint();
            	if(userPointItem.getPointType()==0) {
            		totalPoint = totalPoint + point;
            	}else {
            		totalPoint = totalPoint - point;
            	}
            }
        }
        request.setAttribute("totalPoint", totalPoint);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }
    
    @GetMapping("/shop-like")
    public String likeListPage(HttpServletRequest request,HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        List<GoodsLikeVO> myLikeItems = newBeeMallShoppingCartService.getMyLikeItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myLikeItems)) {
            //购物项总数
            itemsTotal = newBeeMallShoppingCartService.getLikeCount(user.getUserId());
            if (itemsTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("myLikeItems", myLikeItems);
        return "mall/like";
    }
    
    @PostMapping("/shop-like")
    @ResponseBody
    public Result saveGoodsLike(@RequestBody GoodsLike goodsLike,HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Long likeId = newBeeMallShoppingCartService.getMaxLikeId();
        Long newLikeId = likeId + 1;
        Date createDate = new Date();
        goodsLike.setUserId(user.getUserId());
        goodsLike.setIsDeleted(0);
        goodsLike.setLikeId(newLikeId);
        goodsLike.setCreateTime(createDate);
        
        int row = newBeeMallShoppingCartService.insertGoodsLike(goodsLike);
        //添加成功
        if (row>0) {
            return ResultGenerator.genSuccessResult("添加成功");
        }
        //添加失败
        return ResultGenerator.genFailResult("添加失败");
    }
    
    @PostMapping("/shop-like/delete")
    @ResponseBody
    public Result deleteGoodsLike(@RequestBody GoodsLike goodsLike,HttpSession httpSession) {
        Long likeId = goodsLike.getLikeId();
        
        
        int row = newBeeMallShoppingCartService.deleteGoodsLike(likeId);
        //添加成功
        if (row>0) {
            return ResultGenerator.genSuccessResult("删除成功");
        }
        //添加失败
        return ResultGenerator.genFailResult("删除失败");
    }
}
