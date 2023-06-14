package ru.qsystems.route_editor.db.qp_central.custom.forFrontebd;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "routes_list", schema = "qp_central")
public class RoutesListFull {
    @Id
    private Long routeId;

    private String name;

    //    private Integer branch_id;

    private Integer serviceId;

    private boolean defaultChecked;

    private Integer prevServiceId;

}
