package spoilerplate.architecture.buckpal.account.adapter.out.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "activity")
@Entity
public class ActivityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private Long ownerAccountId;
    private Long sourceAccountId;
    private Long targetAccountId;
    private long amount;

}
