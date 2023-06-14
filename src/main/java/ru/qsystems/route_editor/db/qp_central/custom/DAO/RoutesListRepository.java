package ru.qsystems.route_editor.db.qp_central.custom.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.routes_list;

import javax.persistence.PersistenceContext;
import java.util.List;

@PersistenceContext(unitName = "Central")
//@Repository
public interface RoutesListRepository
        extends CrudRepository<routes_list, Long> {
//    extends JpaRepository<routes_list, Long> {
    @Query(value = "SELECT * FROM routes_list", nativeQuery = true)
    List<routes_list> findAllRoutesShort();

}
