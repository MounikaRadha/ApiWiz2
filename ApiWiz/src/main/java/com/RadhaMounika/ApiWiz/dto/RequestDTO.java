package com.RadhaMounika.ApiWiz.dto;

import com.RadhaMounika.ApiWiz.enums.ApiMethod;
import lombok.Data;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

@Data
public class RequestDTO {
    private ApiMethod apiMethod;
    private String url;
    private Map<String, String> queryParams;
    private Map<String, String> headerVariables;
    private String bodyType;
    private String requestBody;
    private Map<String, String> pathMap;
    private Map<String, String> formParam;
    private Map<String, String> urlEncodedParam;
    private List<NameValuePair> params;
    private int sleepTime;
    private int retryCount;
    private boolean retryEnabled;

}
