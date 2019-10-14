package pl.michal;


import pl.michal.interfejs.BankAccount;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        BankAccount[] bankAccounts;
        Random rand = new Random();
        Scanner var = new Scanner(System.in);
        System.out.print("Enter how many account you want to generate: ");
        int noAccount;
        while (true) {
            try {
                noAccount = var.nextInt();
                if (noAccount <= 0) {
                    throw new IllegalArgumentException("The number of account must be greater then 0");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number");
                var.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString() + " \nEnter correct number");
            }
        }

        bankAccounts = new BankAccount[noAccount];

        Bank mikBank = new Bank();

        for (int i = 0; i < bankAccounts.length; i++) {
            bankAccounts[i] = new BankAccount();
//            bankAccounts[i].deposit(rand.nextInt(10) * 10 + rand.nextInt(9));
            bankAccounts[i].depositInfo();
        }
        int from, to;
        double amount;

        System.out.println("Account balance before transactions:");
        mikBank.printBankBalance();

        for (int i = 0; i < noAccount; i++) {
            do {
                from = rand.nextInt(noAccount);
                to = rand.nextInt(noAccount);
            } while (from == to);

            while (true) {
                try {
                    amount = rand.nextDouble() * 100;
                    mikBank.transfer(bankAccounts[from], bankAccounts[to], amount);
                    break;
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.print(e.toString());
                }
                bankAccounts[from].depositInfo();
                bankAccounts[to].depositInfo();
            }
        }
        System.out.println("Account balance after all transactions");
        mikBank.printBankBalance();
        try {
            mikBank.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bankAccounts.length; i++) {
            try {
                bankAccounts[i].Finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
