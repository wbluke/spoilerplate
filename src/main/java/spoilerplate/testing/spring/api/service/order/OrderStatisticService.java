package spoilerplate.testing.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoilerplate.testing.spring.client.mail.MailSendClient;
import spoilerplate.testing.spring.domain.history.mail.MailSendHistory;
import spoilerplate.testing.spring.domain.history.mail.MailSendHistoryRepository;
import spoilerplate.testing.spring.domain.order.Order;
import spoilerplate.testing.spring.domain.order.OrderRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderStatisticService {

    private static final String FROM_EMAIL = "cafe@cafe.com";
    private static final String SUBJECT = "[주문통계] 총 매출";

    private final OrderRepository orderRepository;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    private final MailSendClient mailSendClient;

    public void sendEmailAboutTotalSalesAmount(LocalDate orderDate, String email) {
        // TODO: 2022/09/23 범위 기반으로 다시
        List<Order> orders = orderRepository.findAllByRegisteredDateTimeBetween(
                orderDate.atStartOfDay(), orderDate.atTime(23, 59, 59)
        );
        int totalSalesAmount = orders.stream()
            .mapToInt(Order::getTotalPrice)
            .sum();

        String content = String.format("%s의 총 매출은 %s원입니다.", orderDate, totalSalesAmount);
        boolean sendingResult = mailSendClient.send(FROM_EMAIL, email, SUBJECT, content);
        if (!sendingResult) {
            throw new IllegalArgumentException("메일 전송 실패");
        }

        mailSendHistoryRepository.save(
            MailSendHistory.builder()
                .fromEmail(FROM_EMAIL)
                .toEmail(email)
                .subject(SUBJECT)
                .content(content)
                .build()
        );
    }

}
