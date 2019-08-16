package pl.michal.interfejs;

import java.util.Random;

public class BankAccount implements Account {
    private double accountBalance;

    public int getAccountNO() {
        return accountNO;
    }

    private int accountNO;
    private static int orderNO = 1;
    private static MySQLaccess mySQLaccess = new MySQLaccess();
    private static Random rand = new Random();

    @Override
    public void deposit(int amount) {
        if (amount > 0)
            accountBalance += amount;
        else
            throw new IllegalArgumentException("The amount must be greater than 0!!!\n");
    }

    @Override
    public void withdraw(int amount) {
        if (amount > 0) {
            if (amount <= accountBalance)
                accountBalance -= amount;
            else
                throw new IllegalStateException("Lack of account funds\n");
        } else
            throw new IllegalArgumentException("The amount must be greater than 0!!!\n");
    }

    public void withdraw(double amount) throws Exception {
        if (amount > 0) {
            try {
                double accB = mySQLaccess.GetAccountInfo(accountNO);
                if ((accB - amount) < 0) {
                    throw new IllegalStateException("Lack of account founds!\n");
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    public BankAccount() {
        GenerateAccountNO();

        accountBalance = rand.nextDouble() * 100;
        String name = String.format("%c", rand.nextInt(24) + 65);
        String lastName = String.format("%c", rand.nextInt(24) + 65);
        String eMail = String.format("%s.%s@com.pl", name, lastName);
        String city = String.format("%S%s%S%s%s%s", name, name, name, lastName, lastName, lastName);
        try {
            mySQLaccess.InsertClientData(accountNO, name, lastName, city, eMail);
            mySQLaccess.InsertAccountData(accountNO, accountBalance, 10);
            mySQLaccess.InsertBankTransaction(accountNO, accountNO, accountBalance, 0);
            mySQLaccess.FinishTransaction();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public BankAccount(int amount) {
        this.accountBalance = amount;
        GenerateAccountNO();
    }

    private void GenerateAccountNO() {
//        accountNO = String.format("0000 %d", orderNO++);
        accountNO = orderNO++;
    }

    @Override
    public void depositInfo() {
        try {
            System.out.println(String.format("There is %.2f zł on account %s", mySQLaccess.GetAccountInfo(accountNO), accountNO));
        } catch (Exception e) {
            System.out.println("Problem to get Deposit Info\n");
        }
    }

    public void Finish() throws Exception {
        mySQLaccess.CloseDB();
    }
}
