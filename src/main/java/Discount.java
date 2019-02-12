public class Discount {

    public static String calculatePrice(Quote quote) {

        Delay.delay();
        Double p = quote.getPrice();
        Shop.Code c = quote.getCode();
        int per = c.getPercentage();
        Double price = p - (per/10);
        return quote.getName() + ":" + price;

    }
}
