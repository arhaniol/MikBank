package pl.michal;


import pl.michal.interfejs.BankAccount;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        BankAccount[] konto;
        Random rand = new Random();
        Scanner var = new Scanner(System.in);
        System.out.print("Podaj ile kont chcesz wygenerowac: ");
        int ileKont;
        while (true) {
            try {
                ileKont = var.nextInt();
                if (ileKont <= 0) {
                    throw new IllegalArgumentException("Liczba kont musi być większa od 0");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Musisz wprowadzić liczbe");
                var.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString() + " \nPodaj poprawną liczbę");
                //System.out.print("Podaj poprawną liczbę");
            }
        }

        konto = new BankAccount[ileKont];

        Bank mikBank = new Bank();

        for (int i = 0; i < konto.length; i++) {
            konto[i] = new BankAccount();
            konto[i].deposit(rand.nextInt(10) * 10 + rand.nextInt(9));
            konto[i].depositInfo();
        }
        int from, to, ile;

        System.out.println("Stan przed przelewem:");
        mikBank.showKasa();

        for (int i = 0; i < ileKont; i++) {
            from = rand.nextInt(ileKont);
            to = rand.nextInt(ileKont);
            while (true) {
                try {
                    ile = rand.nextInt(100);
                    System.out.println(String.format("Przelew %d zl z konta %s na konto %s", ile, konto[from].nrKonta, konto[to].nrKonta));
                    mikBank.transfer(konto[from], konto[to], ile);
                    break;
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.print(e.toString());
                }
                konto[from].depositInfo();
                konto[to].depositInfo();
            }
        }
        System.out.println("Stan po przelewie");
        mikBank.showKasa();
    }
}
