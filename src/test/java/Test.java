import com.mmall.util.DateTimeUtil;

import java.util.Date;

public class Test {

    @org.junit.Test
    public void Tes(){
        String s = DateTimeUtil.dateToStr(new Date());
        System.out.println("日期转字符串  "+s);

        Date date = DateTimeUtil.strToDate("2018-10-20 9:51:09");
        System.out.println("字符串转日期格式 "+date.toString());
    }

}
