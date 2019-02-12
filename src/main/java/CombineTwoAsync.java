import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class CombineTwoAsync {
    public static void main(String[] args) {

        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        List<Shop> shops = Arrays.asList(new Shop("shop1"), new Shop("shop2"),
                new Shop("shop3"), new Shop("shop4"), new Shop("shop5"),
                new Shop("shop6"), new Shop("shop7"), new Shop("shop8"));

        Executor executor = Executors.newFixedThreadPool(Math.min(100, shops.size()), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        List<CompletableFuture<Double>> cs = shops
                .stream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> shop.getPriceInRupee("tea"), executor))
                .map(future -> future
                        .thenCombine(CompletableFuture
                                .supplyAsync(() -> ExchangeService.rupeeToDollar(), executor), (price, rate) -> price*rate))
                .collect(Collectors.toList());


        List<Double> prices = cs.stream().map(CompletableFuture::join).collect(Collectors.toList());

        prices.stream().forEach(System.out::println);

        stopwatch.stop();
        long timeTaken = stopwatch.getTime();
        System.out.println("Time consumed in processing is: " + timeTaken + " milliseconds");
    }

}
