package pl.springready.feignanwser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import pl.springready.feignanwser.qotesServer.QuoteProxy;
import pl.springready.feignanwser.qotesServer.Quote;
import pl.springready.feignanwser.qotesServer.QuotePost;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class FeignAnwserApplication {

    private final QuoteProxy quoteProxy;


    // Wstrzykiwanie QoutesMechanism (Feign Client)
    public FeignAnwserApplication(QuoteProxy quoteProxy) {
        this.quoteProxy = quoteProxy;
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignAnwserApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
        // Wywołanie metody Feign Client po uruchomieniu aplikacji
        List<Quote> quotes = quoteProxy.getAllQuotes();
        System.out.println("GET ALL!");
        quotes.forEach(quote -> {
            System.out.println("ID: " + quote.value().id());
            System.out.println("Quote: " + quote.value().quote());
        });
        System.out.println("GET WITH ID = 5");
        System.out.println(quoteProxy.getOne(5L));
        System.out.println();
        System.out.println("RANDOM");
        System.out.println(quoteProxy.getRandomOne());
        System.out.println("Get with request param");
        System.out.println(quoteProxy.getOneByRequestParam(5L));
        System.out.println("Get with header");
        System.out.println(quoteProxy.getAllWithHeader("HEADER HEADER"));
        System.out.println("DELETE");
        System.out.println(quoteProxy.getOne(5L));
        quoteProxy.deleteById(5L);
        System.out.println("AFTER DELETE");
        System.out.println(quoteProxy.getOne(5L));

        System.out.println("POST");
        System.out.println(quoteProxy.addQuote(new QuotePost("QUOTE")));



//        System.out.println(qoutesMechanism.deleteById(1L));

    }
}
