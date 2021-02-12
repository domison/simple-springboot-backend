package world.doms.simplecrudbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SimpleCrudBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCrudBackendApplication.class, args);
    }
//    Undertow server = Undertow.builder()
//            .addHttpListener(8000, "localhost")
//            .setHandler(new HttpHandler() {
//                @Override
//                public void handleRequest(final HttpServerExchange exchange) throws Exception {
//                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
//                    exchange.getResponseSender().send("Frontend not required");
//                }
//            }).build();
//        server.start();
//    }
}

@RestController
class MyRestController {

    @GetMapping("/")
    public String welcome(){
        return "Hello World";
    }
}
