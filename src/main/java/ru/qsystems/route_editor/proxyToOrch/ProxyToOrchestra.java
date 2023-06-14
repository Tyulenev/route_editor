package ru.qsystems.route_editor.proxyToOrch;



import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.qsystems.route_editor.db.qp_central.default_tables.Entity.BranchEntity;
import ru.qsystems.route_editor.db.qp_central.default_tables.Entity.ServicesAvailableForBranch;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;


@Component
public class ProxyToOrchestra {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${ORCHESTRA_BASE_URL}")
    private String URL_BASE_ORCHESTRA;

    @Value("${ORCHESTRA_LOGIN}")
    private String ORCHESTRA_LOGIN;
    @Value("${ORCHESTRA_PASSWORD}")
    private String ORCHESTRA_PASSWORD;


//    /rest/servicepoint/branches/{branchId}/services/ - get all services for branch
    private final String URL_GET_SERVICES_FOR_BRANCH_BEFORE_BRANCHID = "/rest/servicepoint/branches/";
    private final String URL_GET_SERVICES_FOR_BRANCH_AFTER_BRANCHID = "/services/";

//    http://test-dev05:8080/qsystem/rest/config/branches
    private final String URL_GET_BRANCHES = "/qsystem/rest/config/branches";

    private String getUrlForServiceListForBranch (int branchId) {
        return URL_BASE_ORCHESTRA + URL_GET_SERVICES_FOR_BRANCH_BEFORE_BRANCHID + branchId +
                URL_GET_SERVICES_FOR_BRANCH_AFTER_BRANCHID;
    }

    private String getUrlForBranchList() {
        return URL_BASE_ORCHESTRA + URL_GET_BRANCHES;
    }



    private HttpHeaders createHeaders(final String username, final String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    public List<ServicesAvailableForBranch> getAllServicesForBranch(int branchId) {
        HttpEntity httpEnt = new HttpEntity(createHeaders(ORCHESTRA_LOGIN, ORCHESTRA_PASSWORD));
        ResponseEntity<List<ServicesAvailableForBranch>> responseEntity =
                restTemplate.exchange(
                        getUrlForServiceListForBranch (branchId),
                        HttpMethod.GET,
                        httpEnt,
                        new ParameterizedTypeReference <List<ServicesAvailableForBranch>>() {
                        });

        List<ServicesAvailableForBranch> services = responseEntity.getBody();
        return services;
    }

    public List<BranchEntity> getAllBranches() {
        HttpEntity httpEnt = new HttpEntity(createHeaders(ORCHESTRA_LOGIN, ORCHESTRA_PASSWORD));
        ResponseEntity<List<BranchEntity>> responseEntity =
                restTemplate.exchange(
                        getUrlForBranchList (),
                        HttpMethod.GET,
                        httpEnt,
                        new ParameterizedTypeReference <List<BranchEntity>>() {
                        });
        List<BranchEntity> branchList = responseEntity.getBody();
        return branchList;
    }


}
