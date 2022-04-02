import java.util.stream.IntStream;

public class Client extends Thread {
    private final Account account;
    private final Bank bank;
    private final String accountNumber;

    public Client(Bank bank) {
        this.bank = bank;
        this.account = bank.createAccount((long) (Math.random() * (300000 - 250000) + 250000));
        this.accountNumber = account.getAccNumber();
    }

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> transfer(getAmountOfMoney()));
    }

    public synchronized void transfer(long money) {
        if (account.getMoney() > money) {
            String number = bank.getRandomNumberAcc(accountNumber);
            if (number == null) account.setBlock(true);
            else bank.transfer(accountNumber, number, money);
        }
    }

    public long getAmountOfMoney() {
        return (long) (Math.random() * (65000 - 25000) + 25000);
    }
}
