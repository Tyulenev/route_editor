package ru.qsystems.route_editor.db.qp_central.custom.forFrontebd;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ServiceStructure {
    Long id;
    ArrayList<Long> prev;
    boolean defaultChecked;
}
