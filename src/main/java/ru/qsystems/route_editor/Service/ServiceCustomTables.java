package ru.qsystems.route_editor.Service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.qsystems.route_editor.Exceptions.RouteNotFoundException;
import ru.qsystems.route_editor.db.qp_central.custom.DAO.PrevServicesListRepository;
import ru.qsystems.route_editor.db.qp_central.custom.DAO.RoutesListRepository;
import ru.qsystems.route_editor.db.qp_central.custom.DAO.RoutesServicesRepository;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.Prev_services_PK;
import ru.qsystems.route_editor.db.qp_central.custom.forFrontebd.RouteListForFrontend;
import ru.qsystems.route_editor.db.qp_central.custom.forFrontebd.ServiceStructure;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.prev_services_list;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.route_services;
import ru.qsystems.route_editor.db.qp_central.custom.Entity.routes_list;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class ServiceCustomTables {

    @Autowired
    private RoutesListRepository routesListRepository;
    @Autowired
    private RoutesServicesRepository routesServicesRepository;
    @Autowired
    private PrevServicesListRepository prevServicesListRepository;

    public List<routes_list> getAllDataRoutesList() {
        return routesListRepository.findAllRoutesShort();
    }

    public List<RouteListForFrontend> getAllRoutes() {
        List<RouteListForFrontend> routeListForFrontends= new ArrayList<>();
        List<routes_list> routeList = routesListRepository.findAllRoutesShort();
        List<route_services> routeServices = routesServicesRepository.getRouteServices();
        List<prev_services_list> prevServicesList = prevServicesListRepository.findPrevServices();
        for (routes_list rl: routeList) {
            RouteListForFrontend rlForFront = new RouteListForFrontend();
            rlForFront.setRouteId(rl.getId());
            rlForFront.setName(rl.getName());
            rlForFront.setBranchId(rl.getBranch_id());
            List<ServiceStructure> serviceStructureList = new ArrayList<>();
            for (route_services rs:routeServices) {
                if (rs.getRoute_id()==rl.getId()) {
                    ServiceStructure serviceStr = new ServiceStructure();
                    serviceStr.setId(rs.getService_id());
                    serviceStr.setDefaultChecked(rs.isDefaultChecked());
//                    adding prev services. Need to Add
                    ArrayList<Long> listPrev = new ArrayList<>();
                    for (prev_services_list prevService:prevServicesList) {
                        if (prevService.getPk().getRoute_services_id() == rs.getId()) {
                            listPrev.add(prevService.getPk().getPrev_service_id());
                        }
                    }
                    serviceStr.setPrev(listPrev);

                    serviceStructureList.add(serviceStr);
                }
                rlForFront.setServices(serviceStructureList);
            }
            routeListForFrontends.add(rlForFront);
        }
        return routeListForFrontends;
    }

    public RouteListForFrontend getRouteByRouteId(Long routeId) {
        RouteListForFrontend routeForFrontend = new RouteListForFrontend();
        routes_list routeList = routesListRepository.findOne(routeId);
        List<route_services> routeServices = routesServicesRepository.getRouteServices();
        List<prev_services_list> prevServicesList = prevServicesListRepository.findPrevServices();

        if (routeList!=null) {
            routeForFrontend.setRouteId(routeId);
            routeForFrontend.setName(routeList.getName());
            routeForFrontend.setBranchId(routeList.getBranch_id());
        } else {
            throw new RouteNotFoundException("Route with id " + routeId + " not found");
        }
        List<ServiceStructure> listOfServices = new ArrayList<>();
        for (route_services rs:routeServices) {
            if (rs.getRoute_id()==routeId) {
                ServiceStructure ss = new ServiceStructure();
                ss.setId(rs.getService_id());
                ss.setDefaultChecked(rs.isDefaultChecked());
                ArrayList<Long> prevList = new ArrayList<>();
                for (prev_services_list psl:prevServicesList) {
                    if (psl.getPk().getRoute_services_id() == rs.getId())
                        prevList.add(psl.getPk().getPrev_service_id());
                }
                ss.setPrev(prevList);
                listOfServices.add(ss);
            }
        }
        routeForFrontend.setServices(listOfServices);
        return routeForFrontend;
    }


    public List<RouteListForFrontend> getRoutesForBranch(int branchId) {
        List<RouteListForFrontend> routeListForFrontends= new ArrayList<>();
        List<routes_list> routeList = routesListRepository.findAllRoutesShort();
        List<route_services> routeServices = routesServicesRepository.getRouteServices();
        List<prev_services_list> prevServicesList = prevServicesListRepository.findPrevServices();
        for (routes_list rl: routeList) {
            if (rl.getBranch_id() == branchId) {
                RouteListForFrontend rlForFront = new RouteListForFrontend();
                rlForFront.setRouteId(rl.getId());
                rlForFront.setName(rl.getName());
                rlForFront.setBranchId(rl.getBranch_id());

                List<ServiceStructure> serviceStructureList = new ArrayList<>();
                for (route_services rs:routeServices) {
                    if (rs.getRoute_id()==rl.getId()) {
                        ServiceStructure serviceStr = new ServiceStructure();
                        serviceStr.setId(rs.getService_id());
                        serviceStr.setDefaultChecked(rs.isDefaultChecked());
//                    adding prev services. Need to Add
                        ArrayList<Long> listPrev = new ArrayList<>();
                        for (prev_services_list prevService:prevServicesList) {
                            if (prevService.getPk().getRoute_services_id() == rs.getId()) {
                                listPrev.add(prevService.getPk().getPrev_service_id());
                            }
                        }
                        serviceStr.setPrev(listPrev);

                        serviceStructureList.add(serviceStr);
                    }
                    rlForFront.setServices(serviceStructureList);
                }
                routeListForFrontends.add(rlForFront);
            }

        }
        return routeListForFrontends;
    }

    public RouteListForFrontend addRoute(RouteListForFrontend addedRoute) {
        routes_list rl = new routes_list();
        rl.setName(addedRoute.getName());
        rl.setBranch_id(addedRoute.getBranchId());

        addedRoute.setRouteId(routesListRepository.save(rl).getId());

        for (ServiceStructure service : addedRoute.getServices()) {
            route_services rs = new route_services();
            rs.setRoute_id(addedRoute.getRouteId());
            rs.setService_id(service.getId());
            rs.setDefaultChecked(service.isDefaultChecked());

            Long serviceId = routesServicesRepository.save(rs).getId();

            for (Long prev:service.getPrev()) {
                prev_services_list psl = new prev_services_list();
                Prev_services_PK psPK = new Prev_services_PK();
                psPK.setRoute_services_id(serviceId);
                psPK.setPrev_service_id(prev);
                psl.setPk(psPK);
                prevServicesListRepository.save(psl);
            }
        }
    return addedRoute;
    }

    public String routeDelete(Long routeId) {
        try {
            routes_list routeForDel = routesListRepository.findOne(routeId);
            System.out.println("routeForDel: " + routeForDel);

            if (routeForDel!= null) {
                routesListRepository.delete(routeId);
            }
            return "Route with id=" + routeForDel.getId() + " was successfully deleted";
        } catch (NullPointerException e) {
            log.severe("Route with id = " + routeId + " not found");
            throw new RouteNotFoundException("Route with id = " + routeId + " not found");
        }
    }

//    addRouteListForFrontend - with id!
    public String routeEdit (RouteListForFrontend addRouteListForFrontend) {
        routes_list routeForEdit = routesListRepository.findOne(addRouteListForFrontend.getRouteId());
        routeForEdit.setName(addRouteListForFrontend.getName());
        routeForEdit.setBranch_id(addRouteListForFrontend.getBranchId());
        routesListRepository.save(routeForEdit);
        Long routeId = routeForEdit.getId();

//        Delete services for this route
        List<route_services> currentRouteServicesList = routesServicesRepository.getRouteServices();
        for (route_services rsForDel:currentRouteServicesList) {
            if (rsForDel.getRoute_id() == routeId) {
                routesServicesRepository.delete(rsForDel.getId());
            }
        }

//        Add new services and prev
        for (ServiceStructure ss:addRouteListForFrontend.getServices()) {
            route_services rsForAdd = new route_services();
            rsForAdd.setRoute_id(routeId);
            rsForAdd.setService_id(ss.getId());
            rsForAdd.setDefaultChecked(ss.isDefaultChecked());

            Long serviceId = routesServicesRepository.save(rsForAdd).getId();

            for (Long prev:ss.getPrev()) {
                prev_services_list psl = new prev_services_list();
                Prev_services_PK psPK = new Prev_services_PK();
                psPK.setRoute_services_id(serviceId);
                psPK.setPrev_service_id(prev);
                psl.setPk(psPK);
                prevServicesListRepository.save(psl);
            }

        }

        return "OK";
    }

}

