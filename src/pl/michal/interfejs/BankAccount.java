package pl.michal.interfejs;

public class BankAccount implements Account {
    private int stanKonta;
    public String nrKonta;
    private static int nrPorzadkowy = 1;

    @Override
    public void deposit(int amount) {
        if (amount > 0)
            stanKonta += amount;
        else
            throw new IllegalArgumentException("Kwota musi byc wieksza od 0!!!");
    }

    @Override
    public void withdraw(int amount) {
        if (amount > 0) {
            if (amount <= stanKonta)
                stanKonta -= amount;
            else
                throw new IllegalStateException("Brak srodkow na koncie");
        } else
            throw new IllegalArgumentException("Kwota musi byc wieksza od 0!!!");
    }

    public BankAccount() {
        stanKonta = 0;
        GenerujNumerKonta();
    }

    public BankAccount(int stanKonta) {
        this.stanKonta = stanKonta;
        GenerujNumerKonta();
    }

    private void GenerujNumerKonta() {
        nrKonta = String.format("0000 %d", nrPorzadkowy++);
    }

    @Override
    public void depositInfo(){
        System.out.println(String.format("Na koncie: %s jest %d zl",nrKonta,stanKonta));
    }
}
