package com.okdk.board.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt") // jwt.* 설정값을 매핑
public class JwtProperties {
    private String secret;     // 비밀키
    private long expiration;   // 만료 시간
}
