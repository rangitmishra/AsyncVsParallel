import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class AsynchVsParallel {
    /**
     * Question1. For a list of shops print {price of product P in shop X is R}
     * where R is the price calculated after applying discount
     *
     * shop.getPrice(product)
     *
     */

    public static void main(String[] args) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        List<Shop> shops = Arrays.asList(new Shop("shop1"), new Shop("shop2"),
                new Shop("shop3"), new Shop("shop4"), new Shop("shop5"),
                new Shop("shop6"), new Shop("shop7"), new Shop("shop8"));

       // List<String> prices = shops.stream().map(s -> s.getPrice("tea")).collect(Collectors.toList());
       // List<String> prices = shops.parallelStream().map(s -> s.getPrice("tea")).collect(Collectors.toList());
     /*   List<CompletableFuture<String>> fs = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice("tea")))
                .collect(Collectors.toList());*/
        Executor executor = Executors.newFixedThreadPool(Math.min(100, shops.size()), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        List<CompletableFuture<String>> fs = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice("tea"), executor))
                .collect(Collectors.toList());

        List<String> prices = fs.stream().map(CompletableFuture::join).collect(Collectors.toList());

        prices.stream().forEach(System.out::println);

        stopwatch.stop();
        long timeTaken = stopwatch.getTime();
        System.out.println("Time consumed in processing is: " + timeTaken + " milliseconds");
        System.out.println("change in file 2");
    }
}
