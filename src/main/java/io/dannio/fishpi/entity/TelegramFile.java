package io.dannio.fishpi.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(indexes = {@Index(columnList = "fileId")})
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public @Data class TelegramFile extends AbstractPersistable<BigInteger> {

    private BigInteger id;

    private String fileId;

    private String filePath;

    private String fishUrl;

}

