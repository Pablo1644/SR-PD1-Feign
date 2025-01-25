package pl.springready.feignanwser;


import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
        log.info("GET ALL!");
        quotes.forEach(quote -> {
            log.info("ID: " + quote.value().id());
            log.info("Quote: " + quote.value().quote());
        });
        log.info("GET WITH ID = 5");
        log.info(quoteProxy.getOne(5L));
        log.info("RANDOM");
        log.info(quoteProxy.getRandomOne());
        log.info("Get with request param");
        log.info(quoteProxy.getOneByRequestParam(5L));
        log.info("Get with header");
        log.info(quoteProxy.getAllWithHeader("HEADER HEADER"));
        log.info("DELETE");
        log.info(quoteProxy.getOne(5L));
        quoteProxy.deleteById(5L);
        log.info("AFTER DELETE");
        log.info(quoteProxy.getOne(5L));

        log.info("POST");
        log.info(quoteProxy.addQuote(new QuotePost("QUOTE")));

        log.info(quoteProxy.deleteById(1L));
    }
}
