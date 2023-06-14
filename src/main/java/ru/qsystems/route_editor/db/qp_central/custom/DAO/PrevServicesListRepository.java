package ru.qsystems.route_editor.db.qp_central.custom.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.Prev_services_PK;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.prev_services_list;


import javax.persistence.PersistenceContext;
import java.util.List;

@PersistenceContext(unitName = "Central")
public interface PrevServicesListRepository extends JpaRepository<prev_services_list, Prev_services_PK> {
    @Query(value = "SELECT * FROM prev_services_list", nativeQuery = true)
    List<prev_services_list> findPrevServices();
}
