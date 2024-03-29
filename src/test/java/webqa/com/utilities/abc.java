package webqa.com.utilities;

import java.util.Arrays;

public class abc {
    public static void main(String[] args) {
        productArray();
       // reverseStringInSameOrder();
        //rvso1();
        reverseStringInSameOrder1();
    }

    public static void productArray() {
        int[] a= {1,2,3,4};
        int total=1;

        for(int im=0;im<a.length;im++)
            total=total*a[im];

        int[] bm=new int[a.length];
        for(int j=0;j<a.length;j++) {
            bm[j]=total/a[j];
        }
        for(int im=0;im<a.length;im++) {
            System.out.print(bm[im]+" ");
        }
    }


    public static void productArray1() {
        //		Input -1,2,3,4
//		Output - 24 ,12 ,8, 6

        int[] numArr = {1,2,3,4};
        int[] res = new int[numArr.length];
        int temp = 1;
        for(int i=0;i<numArr.length;i++) {
            temp = temp*numArr[i];
        }

        for(int i=0;i<numArr.length;i++) {
            res[i] = temp/numArr[i];
        }

        System.out.println(Arrays.toString(res));

    }
    

    public static void reverseStringInSameOrder() {
        String st = "Munna Kumar Yadav";
        String[] strToArray = st.split(" ");
        for (int i=0;i<strToArray.length;i++){
            char [] stToChar=strToArray[i].toCharArray();
            for (int k=stToChar.length-1;k>=0;k--){
                System.out.print(stToChar[k]);
            }
            System.out.print(" ");
        }
    }

    public static void reverseStringInSameOrder1() {
        String st = "Hello World";
        String[] strToArray = st.split(" ");
        for (int i=0;i<strToArray.length;i++){
            //char [] stToChar=strToArray[i].toCharArray();
            for (int k=strToArray[i].length()-1;k>=0;k--){
                System.out.print(strToArray[i].charAt(k));
            }
            System.out.print(" ");
        }
    }

    public static void rvso1(){
        String s = "Hello World";
        // Output :- olleH dlroW
        String[] s1= s.split(" ");
        for(int i=0;i<s1.length;i++){
            String s2 = s1[i];
           // System.out.println("s2-----"+s2);
            String s3 = "";
            for(int j=s2.length()-1;j>=0;j--) {
                s3= s3+s2.charAt(j);

            }
            System.out.print(s3);
            System.out.print(" ");
        }
    }

   
}

