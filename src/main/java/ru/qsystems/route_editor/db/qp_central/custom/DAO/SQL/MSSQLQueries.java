package ru.qsystems.route_editor.db.qp_central.custom.DAO.SQL;

public class MSSQLQueries {
    public final static String SQLgetRoutes = "SELECT rl.id AS routeId, rl.name, ssps.service_id, ssps.defaultChecked, ssps.prev_service_id\n" +
            "FROM dbo.routes_list as rl \n" +
            "LEFT JOIN (SELECT rs.route_id as routeId, rs.id, rs.service_id, rs.defaultChecked, psl.prev_service_id   FROM route_services as rs \n" +
            "LEFT JOIN prev_services_list as psl ON psl.route_services_id = rs.id) ssps\n" +
            "ON ssps.routeId = rl.id";
}
