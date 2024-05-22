import java.util.Scanner;

public class teste {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("Digite um número");
        String value = s.next();
        Long number = Long.valueOf(value);
        System.out.println(number);
    }
}
