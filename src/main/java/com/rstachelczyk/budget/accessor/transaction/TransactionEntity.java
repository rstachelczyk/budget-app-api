package com.rstachelczyk.budget.accessor.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private long amount;
//    private Long userId;
//    private Long categoryId;
//    private String type;
//    private String status;
//    private Boolean isRecurring;
    @CreatedDate
    private OffsetDateTime createdAt;

}
