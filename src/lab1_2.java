
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class lab1_2 {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        String code="";
        String fileName =scanner.nextLine();
        int level=scanner.nextInt();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line=bufferedReader.readLine();
        while(line!=null) {
            code+=line;
            line=bufferedReader.readLine();
        }

        String keywords="abstract、assert、boolean、break、byte、case、catch、char、class、continue、default、do、double、else、 enum、extends、final、finally、float、for、if、implements、import、int、interface、instanceof、long、native、new、package、private、protected、public、return、short、static、 strictfp、super、switch、synchronized、this、throw、throws、 transient、try、void、volatile、while";//all keywords
        String []keyArr=keywords.split("、");

        Find_key(keyArr,code);
        Find_SwitchCase(code);
        
    }


    public static void Find_key(String[] keyArr,String code){
        int key_num = 0;
        for(int i = 0; i<keyArr.length; i++) {
            Pattern p=Pattern.compile("[^a-z]"+keyArr[i]+"[^a-z]");
            Matcher matcher=p.matcher(code);
            while(matcher.find()) {
                key_num++;
            }
        }
        System.out.println("key num is "+key_num);
    }

    public static void Find_SwitchCase(String code){
        //check switch
        Pattern p=Pattern.compile("switch");
        Matcher matcher=p.matcher(code);
        int switch_num = 0;
        while(matcher.find()) {
            switch_num++;
        }

        //check case
        p=Pattern.compile("switch.*?}");
        matcher=p.matcher(code);
        List case_List=new ArrayList();
        while(matcher.find()) {
            String code1=matcher.toString();//get one switch section
            Pattern p1=Pattern.compile("case");
            Matcher temp_matcher=p1.matcher(code1);
            int case_numb=0;
            while(temp_matcher.find()) {
                case_numb++;
            }
            case_List.add(case_numb);
        }
        System.out.println("switch num is "+switch_num);
        System.out.print("case num are ");
        for(int i=0;i<case_List.size();i++) {
            System.out.print(case_List.get(i)+" ");
        }
        System.out.println();
    }

    public static void if_Else_Elseif(String code) {
        Pattern p = Pattern.compile("else\\s*if|else|if");
        Matcher matcher=p.matcher(code);
        Stack<String> a = new Stack();
        while(matcher.find()) {
            String code1=code.substring(matcher.start(),matcher.end());
            a.push(code1);
        }
        Stack<String> a1 = new Stack<String>();
        boolean flag = false;
        int ifElseif_Num=0;
        int ifElse_Num=0;
        while (!a.isEmpty()) {
            String temp = a.pop();
            if (temp.equals("else")) {
                a1.push(temp);
            } else if (temp.equals("else if")) {
                a1.push(temp);

            } else {//we get if
                //When the top of the res stack is the else if we need to loop out the else-if all the way to the else
                while (a1.peek().equals("else if")) {
                    a1.pop();
                    // make a little notation that its from else if, not else
                    flag = true;
                }
                if (a1.peek().equals("else")) {
                    a1.pop();
                }
                //if it's elseif ,add to ifElseIfNum
                if (flag) {
                    ifElseif_Num++;
                    flag = false;
                } else {//or add to ifElseNum
                    ifElse_Num++;
                }
            }
        }
        System.out.println("if-else num: " + ifElse_Num);
        System.out.println("if-elseif-else num: " + ifElseif_Num);
    }


}
