package ru.qsystems.route_editor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.qsystems.route_editor.Service.ServiceCustomTables;
import ru.qsystems.route_editor.db.qp_central.custom.forFrontebd.RouteListForFrontend;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.routes_list;
import ru.qsystems.route_editor.db.qp_central.default_tables.Entity.BranchEntity;
import ru.qsystems.route_editor.db.qp_central.default_tables.Entity.ServicesAvailableForBranch;
import ru.qsystems.route_editor.proxyToOrch.ProxyToOrchestra;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteEditorRestController {

    @Autowired
    private ServiceCustomTables serviceCustomTables;

    @Autowired
    public ProxyToOrchestra proxyToOrchestra;

    @GetMapping("/test1")
public String test1() {
    System.out.println("test1 done!");
    return "test1.hello !!!";
}

@GetMapping("/test2/{ttt}")
public String test2(@PathVariable String ttt) {
    return "Input str: " + ttt;
}

    @GetMapping("/test3/{routeId}")
    public RouteListForFrontend test2(@PathVariable Long routeId) {
        return serviceCustomTables.getRouteByRouteId(routeId);
    }


    @GetMapping("/get-routes")
    public List<routes_list> getRoutesList() {
        List<routes_list> routesList= serviceCustomTables.getAllDataRoutesList();
        return routesList;
    }

    @GetMapping("/get-routes-full")
    public List<RouteListForFrontend> getRoutes() {
        List<RouteListForFrontend> routesList= serviceCustomTables.getAllRoutes();
        return routesList;
    }

    @GetMapping("/get-routes/branch/{branchId}")
    public List<RouteListForFrontend> getRoutes(@PathVariable int branchId) {
        List<RouteListForFrontend> routesList= serviceCustomTables.getRoutesForBranch(branchId);
        return routesList;
    }

    @GetMapping("/get-services/branch/{branchId}")
    public List<ServicesAvailableForBranch> getServices(@PathVariable int branchId) {
        List<ServicesAvailableForBranch> servicesList = proxyToOrchestra.getAllServicesForBranch(branchId);
        System.out.println(servicesList);
        return servicesList;
    }

    @GetMapping("/branches")
    public List<BranchEntity> getServices() {
        List<BranchEntity> branchList = proxyToOrchestra.getAllBranches();
        return branchList;
    }

    @PostMapping("/route-add")
    public RouteListForFrontend addNewRoute(@RequestBody RouteListForFrontend route) {
        serviceCustomTables.addRoute(route);
        return route;
    }

    @DeleteMapping("/route-del/{routeId}")
    public String deleteRoute(@PathVariable Long routeId) {
        return serviceCustomTables.routeDelete(routeId);
    }

    @PutMapping("/route-edit/{routeId}")
    public String editRoute(@PathVariable Long routeId, @RequestBody RouteListForFrontend route) {
        route.setRouteId(routeId);
        String resp = serviceCustomTables.routeEdit(route);
        return resp;
    }
}
