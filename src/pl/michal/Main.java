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
        int noAccout;
        while (true) {
            try {
                noAccout = var.nextInt();
                if (noAccout <= 0) {
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

        bankAccounts = new BankAccount[noAccout];

        Bank mikBank = new Bank();

        for (int i = 0; i < bankAccounts.length; i++) {
            bankAccounts[i] = new BankAccount();
            bankAccounts[i].deposit(rand.nextInt(10) * 10 + rand.nextInt(9));
            bankAccounts[i].depositInfo();
        }
        int from, to, amount;

        System.out.println("Account balance before transactions:");
        mikBank.printBankBalance();

        for (int i = 0; i < noAccout; i++) {
            from = rand.nextInt(noAccout);
            to = rand.nextInt(noAccout);
            while (true) {
                try {
                    amount = rand.nextInt(100);
                    System.out.println(String.format("Transfer %d zl from %s account to %s account", amount, bankAccounts[from].accountNO, bankAccounts[to].accountNO));
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
    }
}
