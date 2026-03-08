//package com.polarbookshop.order_service.order.messaging;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.polarbookshop.order_service.book.Book;
//import com.polarbookshop.order_service.book.BookClient;
//import com.polarbookshop.order_service.order.domain.Order;
//import com.polarbookshop.order_service.order.domain.OrderRepository;
//import com.polarbookshop.order_service.order.domain.OrderService;
//import com.polarbookshop.order_service.order.domain.OrderStatus;
//import com.polarbookshop.order_service.order.event.OrderAcceptedMessage;
//import com.polarbookshop.order_service.order.web.OrderRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.cloud.stream.binder.test.OutputDestination;
//import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
//import org.springframework.context.annotation.Import;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import java.io.IOException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
//        properties = {
//                "spring.cloud.config.enabled=false",
//                "spring.flyway.enabled=false",
//                "spring.r2dbc.url=r2dbc:h2:mem:///testdb", // Подменяем Postgres на H2 в памяти
//                "spring.r2dbc.username=sa",
//                "spring.r2dbc.password=",
//                "spring.cloud.stream.test.binder.auto-create-topics=true"
//        })
//@Import(TestChannelBinderConfiguration.class)
//public class FunctionsStreamIntegrationTest {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private OutputDestination output;
//
//    @MockBean
//    private BookClient bookClient; // Мокаем внешний сервис книг
//
//    @MockBean
//    private OrderRepository orderRepository;
//
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    void whenOrderAcceptedThroughRestEndpoint() throws IOException {
//        String isbn = "1234567890";
//        int quantity = 3;
//
//        Book expectedBook = new Book(isbn, "Title", "Author", 10.0);
//        Order expectedOrder = new Order(123l,
//                isbn,
//                expectedBook.title(),
//                expectedBook.price(),
//                quantity,
//                OrderStatus.ACCEPTED,
//                null,
//                null,
//                0);
//        Message expectedMessage = MessageBuilder.withPayload(new OrderAcceptedMessage(expectedOrder.id())).build();
//
//        given(bookClient.getBookByIsbn(isbn)).willReturn(Mono.just(expectedBook));
//        given(orderRepository.save(any(Order.class))).willReturn(Mono.just(expectedOrder));
//
//        StepVerifier.create(orderService.submitOrder(isbn, quantity))
//                .expectNextMatches(order -> order.equals(expectedOrder))
//                .verifyComplete();
//        assertThat(objectMapper.readValue(output.receive(5000, "acceptOrder-out-0").getPayload(), OrderAcceptedMessage.class))
//                .isEqualTo(expectedMessage.getPayload());
//
//    }
//
//
//}
