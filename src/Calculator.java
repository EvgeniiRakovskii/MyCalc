import java.io.DataInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.lang.Enum;
import java.util.regex.Pattern;

public class Calculator {

    public static class Numeral{

        private String number;
        public String type;

        public Numeral(String number){
            this.number=number;
        }
        public void setNumber(String number) {
            this.number = number;
        }
        public String getNumber(){
            return number;
        }

        public String getType() {
            return type;
        }

        public String defineMetric()
        {
            int len = number.length();
            char[] n = new char[len];
            int a=0,r=0;
            for ( int i =0; i < len; i++){
            n[i] = number.charAt(i);
            if( (byte)n[i] >= 48 && (byte)n[i]<=57) {
                a++;
            }
                else if((byte)n[i] == 73 || (byte)n[i]==86 || (byte)n[i]==88){
                r++;
            }
        }
            if (a==len)
                return type ="Arabic";
            else if (r==len)
                return type ="Roman";
            else throw new IllegalArgumentException(number + " не является арабской или римской цифрой");
        }
    }

    public static class Arabic{

        int ArabNum;

        public Arabic(Numeral Number){
            this.ArabNum= Integer.parseInt(Number.number);
            if (ArabNum == 0 || ArabNum >10)
                throw new IllegalArgumentException(ArabNum + " Введите число в диапазоне от 1 до 10");
        }

        public int OperationWith(Arabic Number2, String operator) {
            if (operator.equals("+")) {
                return ArabNum + Number2.ArabNum;
            } else if (operator.equals("-")) {
                return ArabNum - Number2.ArabNum;
            } else if (operator.equals("/")) {
                return ArabNum / Number2.ArabNum;
            } else if (operator.equals("*")) {
                return ArabNum * Number2.ArabNum;
            }
            else throw new IllegalArgumentException( operator + " не является одной из допустимых операций: \"+,-,*,/\" ");

        }


    }

    public static class Roman{
        String Number;
        public Roman(Numeral Number){
            this.Number= Number.number;
        }

        public int OperationWith(Roman Number2, String operator){
            RomanNumeral R = RomanNumeral.valueOf(Number);
            int a1 = R.getValue();
            RomanNumeral R2 = RomanNumeral.valueOf(Number2.Number);
            int a2 = R2.getValue();
            if ( operator.equals("+") ) {
                return a1 + a2;
            } else if ( operator.equals("-") ) {
                return a1 - a2;
            } else if ( operator.equals("/") ) {
                return a1 / a2;
            } else if ( operator.equals("*") ) {
                return a1 * a2;

            }
            return 2;

        }}
    enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5),
        VI(6), VII(7), VIII(8), IX(9), X(10),
        XX(20) , XXX(30), XL(40), L(50), LX(60),
        LXX(70), LXXX(80), XC(90), C(100);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }



    public static void main(String[] args) throws java.io.IOException{
        Scanner console = new Scanner(System.in);
        String N1 = console.next();
        Boolean b1 = console.hasNext(console.delimiter());
        String symbol = console.next();
        Boolean b2 = console.hasNext(console.delimiter());
        String N2 = console.next();
        String check = console.nextLine();
        console.close();

       System.out.println();
        System.out.println(b1 + " " + b2 + " ");
        if (!check.isEmpty()) throw new IllegalArgumentException(" Калькулятор работает только с двумя числами ");
        Numeral Number1 = new Numeral(N1);
        Numeral Number2 = new Numeral(N2);

          if (Number1.defineMetric() == "Arabic" && Number2.defineMetric() == "Arabic" ) {
                Arabic a1 = new Arabic(Number1);
                Arabic a2 = new Arabic(Number2);
                System.out.println("Результат для арабских - " + a1.OperationWith(a2,symbol));
            }
          else if (Number1.defineMetric() == "Roman" && Number2.defineMetric() == "Roman" ){
              Roman a1 = new Roman(Number1);
              Roman a2 = new Roman(Number2);
              System.out.print("Результат для римских- " + a1.OperationWith(a2,symbol) + " equal ");
              int i = 18;
              int number = a1.OperationWith(a2,symbol);
              StringBuilder sb = new StringBuilder();
              while (number>0 && i>=0){
                  RomanNumeral currentSymbol = RomanNumeral.values()[i];
                  if(currentSymbol.getValue()<=number){
                      sb.append(currentSymbol.name());
                      number = number - currentSymbol.getValue();}
                      else i--;
              }
              System.out.println(sb);
          }
          else throw new IllegalArgumentException( " Введите числа одного типа");

        System.out.println("Первое число - " + Number1.defineMetric());
        System.out.println("Оператор - " + symbol);
        System.out.println("Второе число - " + Number2.defineMetric());

        //console.close();

    }}


