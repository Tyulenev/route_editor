package ru.qsystems.route_editor.db.qp_central.default_tables.Entity;

import lombok.Data;

@Data
public class ServicesAvailableForBranch {
    private Long id;
    private String internalName;
    private String externalName;
//    private String internalDescription;
//    private String externalDescription;
    private Long targetTransactionTime;

}

