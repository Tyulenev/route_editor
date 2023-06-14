package ru.qsystems.route_editor.db.qp_central.custom.forFrontebd;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RouteListForFrontend {
    Long routeId;

    String name;

    Integer branchId;

    List<ServiceStructure> services;


}
