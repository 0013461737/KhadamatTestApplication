package MainPackage.dbmanager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Setter
@Getter
@RedisHash("ORDER")
public class Order {

    private String id;
    private String name;
    private String family;
    private String phone;
    private String address;
    private int channelId;
    private long amount;
    private int quantity;
}
