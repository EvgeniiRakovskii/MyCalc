import java.io.DataInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.lang.Enum;
import java.util.regex.Pattern;

public class Calculator {

    public static  void checkCondition(int number){
        if (number == 0 || number > 10)
         throw new IllegalArgumentException();
    }
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
                // проверяем по таблице ASCII является ли символ цифрой
                // или буквой, соответствующей римской цифре;
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
            else throw new IllegalArgumentException();
        }
    }

   public static class Arabic{

        int ArabNum;

        public Arabic(Numeral Number){
            this.ArabNum= Integer.parseInt(Number.number);
            checkCondition(ArabNum);
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
            else throw new IllegalArgumentException();
        }
    }

    public static class Roman{
        String Number;
        public Roman(Numeral Number){
            this.Number= Number.number;
        }

        public StringBuilder ArabicToRoman(int number){
            int i = 18;
            StringBuilder sb = new StringBuilder();
            while (number>0 && i>=0) {
                RomanNumeral currentSymbol = RomanNumeral.values()[i];
                if (currentSymbol.getValue() <= number) {
                    sb.append(currentSymbol.name());
                    number = number - currentSymbol.getValue();
                } else i--;
            }
            return sb;
        }

        public StringBuilder OperationWith(Roman Number2, String operator){
            RomanNumeral R = RomanNumeral.valueOf(Number);
            int r1 = R.getValue();
            checkCondition(r1);
            RomanNumeral R2 = RomanNumeral.valueOf(Number2.Number);
            int r2 = R2.getValue();
            checkCondition(r2);
            if ( operator.equals("+") ) {
                return ArabicToRoman(r1 + r2);
            } else if ( operator.equals("-") ) {
                return ArabicToRoman(r1 - r2);
            } else if ( operator.equals("/") ) {
                return ArabicToRoman(r1 / r2);
            } else if ( operator.equals("*") ) {
                return ArabicToRoman(r1 * r2);

            }
            return null;
        }
    }
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

    public static void main(String[] args) throws java.io.IOException {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String exercise = reader.readLine();
                String symbols[] = exercise.split(" ");

                Numeral Number1 = new Numeral(symbols[0]);
                Numeral Number2 = new Numeral(symbols[2]);

                if (Number1.defineMetric() == "Arabic" && Number2.defineMetric() == "Arabic") {
                    Arabic a1 = new Arabic(Number1);
                    Arabic a2 = new Arabic(Number2);
                    System.out.println(a1.OperationWith(a2, symbols[1]));
                } else if (Number1.defineMetric() == "Roman" && Number2.defineMetric() == "Roman") {
                    Roman r1 = new Roman(Number1);
                    Roman r2 = new Roman(Number2);
                    System.out.println(r1.OperationWith(r2, symbols[1]));
                } else throw new IllegalArgumentException();

            }  catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
                System.out.println("Введите уравнение в форме a + b, " +
                        "числа должны быть в пределе от 1(I) до 10(X)");
            }
    }
}


