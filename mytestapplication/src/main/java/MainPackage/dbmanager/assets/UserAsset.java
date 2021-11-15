package MainPackage.dbmanager.assets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class UserAsset {
    private String id;
    private String username;
    private String password;
}
