package io.dannio.fishpi.entity;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(indexes = {@Index(columnList = "fileId")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public @Data class TelegramFile extends AbstractPersistable<BigInteger> {

    private BigInteger id;

    private String fileId;

    private String filePath;

    private String fishUrl;

}

