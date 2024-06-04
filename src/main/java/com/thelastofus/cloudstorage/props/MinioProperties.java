package com.thelastofus.cloudstorage.props;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "minio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioProperties {

    String bucket;
    String url;
    String accessKey;
    String secretKey;
    Long memoryLimit;

}
