package MainPackage.dbmanager.repository;

import MainPackage.dbmanager.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order,String> {
    Optional<Order> findFirstByOrderBySortingDesc();
    Optional<Order> findTopByOrderBySortingDesc();
    Optional<Order> findAllByOrderBySortingDesc();
    Optional<Order> findBySortingVal(String sortingVal);
}
