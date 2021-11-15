package MainPackage.dbmanager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Setter
@Getter
@RedisHash("USER")
public class User {
    private String id;
    @Indexed
    private String username;
    @Indexed
    private String password;
}
