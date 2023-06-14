package ru.qsystems.route_editor.db.qp_central.custom.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "route_services")
public class route_services {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private long route_id;

    private long service_id;

    private boolean defaultChecked;
    
}
