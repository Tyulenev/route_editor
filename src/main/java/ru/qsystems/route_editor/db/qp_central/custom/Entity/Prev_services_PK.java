package ru.qsystems.route_editor.db.qp_central.custom.Entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class Prev_services_PK implements Serializable {
    private long route_services_id;
    private long prev_service_id;
}
