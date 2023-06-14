package ru.qsystems.route_editor.db.qp_central.custom.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.route_services;

import javax.persistence.PersistenceContext;
import java.util.List;

@PersistenceContext(unitName = "Central")
public interface RoutesServicesRepository extends JpaRepository<route_services, Long> {
    @Query(value = "SELECT * FROM route_services", nativeQuery = true)
    List<route_services> getRouteServices();
}
