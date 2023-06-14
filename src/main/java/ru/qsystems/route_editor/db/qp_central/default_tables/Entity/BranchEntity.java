package ru.qsystems.route_editor.db.qp_central.default_tables.Entity;

import lombok.Data;

@Data
public class BranchEntity {
    private Long id;
    private String name;
    private String branchPrefix;
    private Long operationProfileId;
    private Long equipmentProfileId;
    private Boolean enabled;
}
