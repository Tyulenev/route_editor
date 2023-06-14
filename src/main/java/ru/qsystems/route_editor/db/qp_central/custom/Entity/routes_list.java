package ru.qsystems.route_editor.db.qp_central.custom.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "routes_list")
//        , schema = "qp_central")
public class routes_list {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer branch_id;

}
