/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.mall;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.GoodsCampaign;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.ResultGenerator;

import java.io.*;   
import java.util.*;


@Controller
public class CsvScannerTest {
	
	@Resource
	NewBeeMallCategoryService newBeeMallCategoryService;
	
	@GetMapping("/csv")
	public void csvScanner(String[] args){  
	     // BufferedReaderはtry処理の最後に必ずclose処理を入れる必要があるため、最初に初期化処理を行っています
	      BufferedReader buffReader = null;
	 
	      try {
	        // sample1.csvファイルを読み込みます
	        FileInputStream fileInput = new FileInputStream("F:\\CSVDemo.csv"); // ※1
	        // バイトストリームをテキスト形式に変換
	        InputStreamReader inputStream = new InputStreamReader(fileInput); // ※2
	        // テキスト形式のファイルを読み込む
	        buffReader = new BufferedReader(inputStream); // ※3
	 
	        String currentContent;
	        int row = 0;
	        String[] arrayColumnName = null;
	        GoodsCampaign goodsCam = new GoodsCampaign();
	        while((currentContent = buffReader.readLine()) != null) { // ※4
	          System.out.println(row + "行目のデータ");
	          if(row == 0) { // ※5
	            arrayColumnName = currentContent.split(",");
	            System.out.println(currentContent);
	          } else {
	            int arrayNumber = 0;
	            String[] arrayColumnData = currentContent.split(","); // ※6
	            
	            Long goodsId = Long.parseLong(arrayColumnData[0]);
	            Long camId = Long.parseLong(arrayColumnData[1]);
	            String sDate = arrayColumnData[2];
	            String eDate = arrayColumnData[3];
	            
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
	            Date startDate = formatter.parse(sDate);
	            Date endDate = formatter.parse(eDate);
	            
	            List<Long> goodsIds = newBeeMallCategoryService.getGoodsId();
	            goodsCam.setGoodsId(goodsId);
	            goodsCam.setCamId(camId);
	            if(endDate.after(startDate)) {
	            	goodsCam.setStartDate(startDate);
		            goodsCam.setEndDate(endDate);
				} /*
					 * else { return
					 * ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult()); }
					 */
	            if(goodsIds.contains(goodsId)) {
	            	int updateRow = newBeeMallCategoryService.setNewGoodsCam(goodsCam);
	            	System.out.println("已更新第"+updateRow+"行");
	            }else {
	            	int insertRow = newBeeMallCategoryService.insertNewGoodsCampaign(goodsCam);
	            	System.out.println("已插入为第"+insertRow+"行");
	            }

	            for(String columnName : arrayColumnName) { // ※7
	              // カラム名と該当行のデータをコンソール出力
	              System.out.println(columnName + " = " + arrayColumnData[arrayNumber]); // ※8
	              arrayNumber++;
	            }
	 
	          }
	          row++;
	        }
	      } catch(Exception ex) {
	        ex.printStackTrace();
	      } finally {
	        try{
	          buffReader.close(); // ※9
	        } catch(Exception ex) {
	          ex.printStackTrace();
	        }
	        
	      } 
	}
}
