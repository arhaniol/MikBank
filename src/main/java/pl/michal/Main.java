package pl.michal;


import pl.michal.interfejs.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Random rand = new Random();
        Scanner var = new Scanner(System.in);
        final Logger logger = LoggerFactory.getLogger(Main.class);

        logger.info("Enter how many account you want to generate: ");
//        System.out.print("Enter how many account you want to generate: ");
        int noAccount;
        while (true) {
            try {
                noAccount = var.nextInt();
                if (noAccount <= 0) {
                    throw new IllegalArgumentException("The number of account must be greater then 0");
                }
                break;
            } catch (InputMismatchException e) {
                logger.info("You must enter a number");
//                System.out.println("You must enter a number");
                var.next();
            } catch (IllegalArgumentException e) {
                logger.info(e.toString() + " \nEnter correct number");
//                System.out.println(e.toString() + " \nEnter correct number");
            }
        }

        BankAccount[] bankAccounts;
        bankAccounts = new BankAccount[noAccount];

        Bank mikBank = new Bank();
        if (!mikBank.isConnectionEstablished()) {
            logger.info("Connection not established!\nProgram terminated!");
            return;
        }

        for (int i = 0; i < bankAccounts.length; i++) {
            bankAccounts[i] = new BankAccount();
            bankAccounts[i].depositInfo();
        }
        int from, to;
        double amount;

        logger.info("Account balance before transactions:");
//        System.out.println("Account balance before transactions:");
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
        logger.info("Account balance after all transactions");
//        System.out.println("Account balance after all transactions");
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
