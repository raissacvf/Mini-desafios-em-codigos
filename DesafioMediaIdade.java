// Faça um programa que peça para 3 pessoas a sua idade, ao final o programa deverá verificar se a média de idade da turma varia entre 0 e 25, 26 e 60 e maior que 60; e então, dizer se a turma é jovem, adulta ou idosa, conforme a média calculada.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        
        System.out.printn ("Digite a idade da 1° pessoa");
        int idade1 = leitor.nextInt();
        System.out.printn ("Digite a idade da 2° pessoa");
        int idade2 = leitor.nextInt();
        System.out.printn ("Digite a idade da 3° pessoa");
        int idade3 = leitor.nextInt();

      double mediaIdade = (idade1 + idade2 + idade3) / 3.0;

        if (mediaIdade >= 0 && mediaIdade <= 25) {
            System.out.println("Jovem!");
        } else if (mediaIdade >= 26 && mediaIdade <= 60) {
            System.out.println("Adulta!");
        } else {
            System.out.println("Idosa!");
        }
        
        leitor.close();
    }
}
