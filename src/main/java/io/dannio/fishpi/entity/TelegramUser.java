package io.dannio.fishpi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(indexes = {@Index(columnList = "telegramId")})
@EqualsAndHashCode(callSuper = true)
public @Data class TelegramUser extends AbstractPersistable<BigInteger> {

    private BigInteger id;

    private Long telegramId;

    private String fishId;

    private String apiKey;

}
