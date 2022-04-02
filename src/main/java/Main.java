import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Client(bank)));
        }
        System.out.println("Средства в банке до перевода " + bank.getSumAllAccounts());
        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Средства в банке после перевода " + bank.getSumAllAccounts());
    }
}
