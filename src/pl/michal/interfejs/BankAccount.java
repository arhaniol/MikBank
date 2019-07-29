package pl.michal.interfejs;

public class BankAccount implements Account {
    private int accountBalance;
    public String accountNO;
    private static int orderNO = 1;

    @Override
    public void deposit(int amount) {
        if (amount > 0)
            accountBalance += amount;
        else
            throw new IllegalArgumentException("The amount must be greater than 0!!!");
    }

    @Override
    public void withdraw(int amount) {
        if (amount > 0) {
            if (amount <= accountBalance)
                accountBalance -= amount;
            else
                throw new IllegalStateException("Lack of account funds");
        } else
            throw new IllegalArgumentException("The amount must be greater than 0!!!");
    }

    public BankAccount() {
        accountBalance = 0;
        GenerateAccountNO();
    }

    public BankAccount(int amount) {
        this.accountBalance = amount;
        GenerateAccountNO();
    }

    private void GenerateAccountNO() {
        accountNO = String.format("0000 %d", orderNO++);
    }

    @Override
    public void depositInfo() {
        System.out.println(String.format("There is %d zł on account %s", accountBalance, accountNO));
    }
}
