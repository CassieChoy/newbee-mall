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

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.*;   
import java.util.*;

@RunWith(SpringRunner.class)

@SpringBootTest
public class CsvWriterTest {
	
	 public static void main(String args[]) {
	        // CSVに書き込む内容を記述
	        String[] goodsId = {"10705", "10706"};
	        String[] camId = {"1024", "1025"};
	        String[] startDate = {"2022/02/27", "2022/02/28"};
	        String[] endDate = {"9999/02/20", "9999/02/01"};
	        // exportCsvクラスに渡す
	        exportCsv( goodsId, camId, startDate, endDate);
	    }
	    public static void exportCsv(String[] goodsId, String[] camId, String[] startDate, String[] endDate){
	        try {
	            // 出力ファイルの作成
	            FileWriter fw = new FileWriter("F:\\\\CSVDemo.csv", true);
	            // PrintWriterクラスのオブジェクトを生成
	            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
	 
	            // データを書き込む
	            for(int i = 0; i < goodsId.length; i++){
	                pw.print(goodsId[i]);
	                pw.print(",");
	                pw.print(camId[i]);
	                pw.print(",");
	                pw.print(startDate[i]);
	                pw.print(",");
	                pw.print(endDate[i]);
	                pw.println();
	            }
	 
	            // ファイルを閉じる
	            pw.close();
	 
	            // 出力確認用のメッセージ
	            System.out.println("csvファイルを出力しました");
	 
	        // 出力に失敗したときの処理
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	
}
