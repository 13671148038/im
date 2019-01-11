package im;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.kaiyun.until.DateUtil;

public class Acds {

	public static void main(String[] args) throws ParseException {
		Calendar instance = Calendar.getInstance();
		int month = instance.get(Calendar.MONTH)+1;
		int year = instance.get(Calendar.YEAR);
		for (int i = 0; i < 24; i++) {
			if(month>12){
				year++;
				month=1;
			}
			String m =month+"";
			m=m.length()<2?('0'+m):m;
			Date date2 = DateUtil.getDate2(year+"-"+m+"-3");
			String lastWeekDate = DateUtil.getLastWeekDate(date2,2);
			System.out.println(lastWeekDate);
			month++;
		}
	}

}
