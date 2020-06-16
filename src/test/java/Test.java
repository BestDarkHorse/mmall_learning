import com.alibaba.fastjson.JSON;
import com.mmall.common.ServerResponse;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.DateTimeUtil;

import java.math.BigDecimal;
import java.util.Date;

public class Test {

    @org.junit.Test
    public void Tes(){
        String s = DateTimeUtil.dateToStr(new Date());
        System.out.println("日期转字符串  "+s);

        Date date = DateTimeUtil.strToDate("2018-10-20 9:51:09");
        System.out.println("字符串转日期格式 "+date.toString());
    }

    @org.junit.Test
    public void testBigDecimal() {
        Integer a = 100;
        Integer b = 3;
        System.out.println("Integer 除法"+a/b);
        BigDecimal div = BigDecimalUtil.div(a.doubleValue(), b.doubleValue());
        System.out.println("BigDecimal 除法" + div);
    }

    @org.junit.Test
    public void testServerResponse() {
//        ServerResponse<String> abc = ServerResponse.createByErrorMessage("这就是我的地盘");
//        System.out.println(JSON.toJSON(abc));

        ServerResponse<String> suc = ServerResponse.createBySuccess();
        ServerResponse<String> byError = ServerResponse.createByError();

        System.out.println(JSON.toJSON(byError));
    }

}
