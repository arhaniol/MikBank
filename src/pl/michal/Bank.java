package pl.michal;

import pl.michal.interfejs.BankAccount;
import pl.michal.interfejs.BankTransfer;

public class Bank implements BankTransfer {
    private int kasa;
    private final int oplata;

    public Bank() {
        kasa = 0;
        oplata = 1;
    }

    @Override
    public void transfer(BankAccount from, BankAccount to, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Przelew musi byę wiekszy od 0 zl!!!");
        }
        from.withdraw(amount + oplata);
        to.deposit(amount);
        kasa += oplata;
    }

    public void showKasa() {
        System.out.println(String.format("W banku jest %d zl", kasa));
    }
}
