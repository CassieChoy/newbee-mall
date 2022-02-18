/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.mall;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;

@RunWith(SpringRunner.class)

@SpringBootTest
public class GoodsQaTest {
	//在controller里导入service
	@Resource
	NewBeeMallGoodsService newBeeMallGoodsService;
	
	NewBeeMallCategoryService newBeeMallCategoryService;
	@Test
	public void GoodsQaTest() {
	
//		List<GoodsQa> list = newBeeMallGoodsService.getGoodsQa("like");
//			assertEquals(3,list.size());
//		System.out.println(list.get(0));
		/*
		 * GoodsQa qa = new GoodsQa(); qa.setGoodsId(10702); int row =
		 * newBeeMallGoodsService.insertGoodsQa(qa); System.out.println(row);
		 */
		
		Map<String, Object> params = new HashMap();
		params.put("page", 5);
		params.put("limit", 5);
		PageQueryUtil pageUtil = new PageQueryUtil(params);
		int count =newBeeMallCategoryService.getTotalCampaign(pageUtil);
		System.out.println(count);
		
		
	}
	
	
	
	}

   




