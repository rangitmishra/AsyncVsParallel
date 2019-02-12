import java.util.concurrent.ThreadLocalRandom;

public class Shop {
    private String name;
    public enum Code {
        NONE(0), SILVER(5), PLATINUM(10), GOLD(15);
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }
    }

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice(String product) {
        Delay.delay();
        Double price =   Math.random() * product.charAt(0);

        int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
        Code code = Code.values()[randomNum];

        return name + ":" + price + ":" + code;
    }

    public Double getPriceInRupee(String product) {
        Delay.delay();

        return Math.random() * product.charAt(0);
    }

}
