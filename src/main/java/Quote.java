public class Quote {
    private String name;
    private Double price;
    private Shop.Code code;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Shop.Code getCode() {
        return code;
    }

    public void setCode(Shop.Code code) {
        this.code = code;
    }

    public Quote() {
    }

    public Quote(String name, Double price, Shop.Code code) {
        this.name = name;
        this.price = price;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Quote parse(String res) {
        String b[] = res.split(":");
        String name = b[0];
        Double d = Double.parseDouble(b[1]);
        Shop.Code c = Shop.Code.valueOf(b[2]);
        Quote q = new Quote(name, d, c);
        return q;

    }


}
