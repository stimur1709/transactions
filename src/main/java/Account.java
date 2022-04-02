import java.util.concurrent.atomic.AtomicLong;

public class Account {

    private final AtomicLong money = new AtomicLong();
    private final String accNumber;
    private boolean block;

    public Account(long money, String accNumber) {
        this.money.set(money);
        this.accNumber = accNumber;
        block = false;
    }

    public long getMoney() {
        return money.longValue();
    }

    public void setMoney(long money) {
        if (!block) {
            this.money.addAndGet(money);
        }
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isBlock() {
        return !block;
    }
}
