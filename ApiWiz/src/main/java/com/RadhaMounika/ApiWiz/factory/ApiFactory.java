package com.RadhaMounika.ApiWiz.factory;


import com.RadhaMounika.ApiWiz.dto.RequestDTO;
import com.RadhaMounika.ApiWiz.enums.ApiMethod;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import java.io.IOException;


public interface ApiFactory {
    public abstract HttpResponse executeTarget(
            ApiMethod apiMethod,
            RequestDTO requestDTO,
            SSLConnectionSocketFactory sslConnectionSocketFactory,
            int timeout
    ) throws IOException;
}
