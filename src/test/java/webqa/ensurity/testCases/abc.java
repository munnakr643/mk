package webqa.ensurity.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webqa.ensurity.base.BaseTest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class abc {

    public static Logger logger = LogManager.getLogger(BaseTest.class);
    public static void main(String[] args) {
//mi(3,"auth",5);
        dta();
//        str();
//  abc fttt=new abc();
//        fttt.time();
        time();
//        dds();
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




        public static void time(){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -100);
            cal.add(Calendar.YEAR, 0);
            Date date = cal.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String date1 = format1.format(date);
            System.out.println(date1);
        }


    public static void dds(){
        String srrt="123456";
        for(int i=0;i<srrt.length();i++){
            System.out.println(srrt.charAt(i));
        }
    }

    public static int solution(String S) {
        int[] arr = new int[26];
        for(int i=0;i<S.length();i++)
        {
            arr[(int)S.charAt(i)-65]++;
        }
        int a=arr[0];
        int b=arr[1];
        int l=arr[11]/2;
        int n=arr[13];
        int o=arr[14]/2;
        return Math.min(Math.min(Math.min(a, b),Math.min(l, n)),o);
    }

}
