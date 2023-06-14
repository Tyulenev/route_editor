package ru.qsystems.route_editor.db.qp_central.custom.Entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "prev_services_list")
public class prev_services_list {

    @EmbeddedId
    private  Prev_services_PK pk;


}
