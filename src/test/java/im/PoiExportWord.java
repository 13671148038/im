package im;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class PoiExportWord {

	public static void main(String[] args) throws Exception {
		XWPFDocument document = getImportResultData();
		FileOutputStream out = new FileOutputStream(new File("C:/Users/MyPC/Desktop/taskResult.doc"));
		document.write(out);
//      下载
//		document.write(response.getOutputStream());
	}

	/**
	 * 查询导出的数据
	 */
	public static XWPFDocument getImportResultData() {
		XWPFDocument document = new XWPFDocument();
			// 标题段落
			String titleParagraphStr =  "周   维  护  检  查  登  记  表";
			XWPFParagraph titleParagraph = document.createParagraph();
			// 设置居中
			titleParagraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun titleParagraphStrRun = titleParagraph.createRun();
			titleParagraphStrRun.setText(titleParagraphStr);
			titleParagraphStrRun.setFontSize(20);

			// 创建表格 创建的table会自带一行一列忽视不要理它就行
			XWPFTable table = document.createTable();
			CTTcPr newTcPr = null;
			XWPFTableRow row = null;
			XWPFTableCell cell = null;
			CTP p = null;
			CTPPr newPPr = null;

			// 第一行
			row = table.createRow();
			// 第一列 创建行的时候会自带一列,多以调用get方法获取第一列 后面的列使用create方法创建列
			cell = row.getCell(0);
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVMerge().setVal(STMerge.RESTART); // 向下合并
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			newTcPr.addNewTcW().setW(new BigInteger("621")); // 宽度
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("序号");

			// 第二列
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewGridSpan().setVal(new BigInteger("2")); // 左右合并
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("检查内容");

			// 第三列
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			newTcPr.addNewTcW().setW(new BigInteger("1044")); // 宽度
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("技术要求");
			// 第四列
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			// TODO 这里是查询的日期集合中的个数就是检查结果中合并的个数
			newTcPr.addNewGridSpan().setVal(new BigInteger("7")); // 左右合并
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			newTcPr.addNewTcW().setW(new BigInteger("4844")); // 宽度
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("检查结果");

			// 第二行
			row = table.createRow();
			// 第一列
			cell = row.getCell(0);
			newTcPr = cell.getCTTc().addNewTcPr();
			// 这个方法的意思:如果有行合并的话就加这个
			newTcPr.addNewVMerge();
			// 第二列
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewGridSpan().setVal(new BigInteger("3")); // 列合并
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("日期");

			// TODO 这里是查询的日期需要动态生成
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");

			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");

			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");
			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("2016-09-09");

			// 第三列
			row = table.createRow();

			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			newTcPr.addNewTcW().setW(new BigInteger("1089")); // 宽度
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("sdsdsd");

			cell = row.createCell();
			newTcPr = cell.getCTTc().addNewTcPr();
			newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
			newTcPr.addNewTcW().setW(new BigInteger("1089")); // 宽度
			p = cell.getCTTc().getPArray(0);
			newPPr = p.addNewPPr();
			newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
			cell.setText("sdsdsd收到");

			cell = row.createCell();
			cell = row.createCell();
			cell = row.createCell();
			cell = row.createCell();
			cell = row.createCell();
			cell = row.createCell();
			cell = row.createCell();
			cell = row.createCell();

		return document;
	}

}
