package MainPackage.dbmanager.repository;

import MainPackage.dbmanager.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,String> {
}
