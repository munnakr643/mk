package webqa.ensurity.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webqa.ensurity.base.BaseTest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class abc {

    public static Logger logger = LogManager.getLogger(BaseTest.class);
    public static void main(String[] args) {
//mi(3,"auth",5);
        dta();
//        str();
//  abc fttt=new abc();
//        fttt.time();
//        time();
        time();
        time3();
//        dds();
        System.out.println(getMonthNumber("January"));
        month();
    }

    public static void mi(int i,String text,int intance){
                String strrr="(//div[@aria-haspopup='listbox'])"+ "[" + i + "]";
        String xpathUsingText="(//span[text()='"+text+"'])"+ "[" + intance + "]";
        logger.info("-------------------------------"+strrr);
        System.out.println("-----------+++"+strrr);

        logger.info("---------------xpathUsingText----------------"+xpathUsingText);
        System.out.println("-----xpathUsingText------+++"+xpathUsingText);
    }

    public static void dta(){
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String formatted = df.format(new Date());
        System.out.println(formatted);
        int st=Integer.parseInt(formatted);
        if(st<=02){
            System.out.println("Hi");

        }else {
            System.out.println("Hello");
        }
    }

    public static void str(){
        String st="₹ 3,445";
        String s2str = st.replaceAll("₹", "").replaceAll(",","").trim();
        System.out.println(s2str);
        float f=Float.parseFloat(s2str);
        float td=(f*18)/100;
        System.out.println(td);
        System.out.println(td+f);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        System.out.println(df.format(td+f));

    }




        public static String time(){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -45);
            cal.add(Calendar.MONTH, -0);
            cal.add(Calendar.YEAR, -0);
            Date date = cal.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat yr = new SimpleDateFormat("yyyy");
            SimpleDateFormat month = new SimpleDateFormat("MM");
            SimpleDateFormat day = new SimpleDateFormat("dd");
            String yrs = yr.format(date);
            String mon = month.format(date);
            String dd = day.format(date);
            if (dd.charAt(0)=='0'){
                dd = dd.substring(1);
            }
            String date1 = format1.format(date);
            System.out.println(date1);
            System.out.println(yrs);
            System.out.println(mon);
            System.out.println(dd);
            return mon;
        }


    public static void time2(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -100);
        cal.add(Calendar.YEAR, 0);
//        cal.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//        cal.setTimeZone(TimeZone.getTimeZone("America/Montreal"));
        cal.getInstance(TimeZone.getTimeZone("GMT"));

        Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("HH-mm-ss");
        String date1 = format1.format(date);
        System.out.println(date1);
    }


    public static void dds(){
        String srrt="123456";
        for(int i=0;i<srrt.length();i++){
            System.out.println(srrt.charAt(i));
        }
    }


    public static void time3(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH_mm-ss");
        myDateObj.atZone( ZoneId.of( "Africa/Tunis" ) );
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(formattedDate);
//        return formattedDate;
    }

    private static int getMonthNumber(String monthName) {
        return Month.valueOf(monthName.toUpperCase()).getValue();
    }

    public static void month(){
        System.out.println("-------------"+Integer.parseInt(time()));
        int selectMonth=Integer.parseInt(time());

        int currentMonth=getMonthNumber("August");
            if(selectMonth<currentMonth){
                for (int i=1;i<=currentMonth-selectMonth;i++){
                    System.out.println("ifclick"+i);
                }
            }else {
                for (int i=1;i<=selectMonth-currentMonth;i++){
                    System.out.println("elseclick"+i);
                }
            }
    }
}
