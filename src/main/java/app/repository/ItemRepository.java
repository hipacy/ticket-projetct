package app.repository;

import app.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query(value = "select * from Item", nativeQuery = true)
    List<Item> findAllItems();

    @Query("select t from Item t where t.name = ?1")
    List<Item> findItemByName(String name);

    @Query("select t from Item t where t.type = ?1")
    List<Item> findItemByType(String type);
}
