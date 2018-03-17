package org.actech.smart.trader.web.service.impl;
import org.actech.smart.trader.registry.service.ServiceRegistryCenter;
import org.actech.smart.trader.web.service.DistributeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by paul on 2018/3/14.
 */
@Service
public class DistributeServiceImpl implements DistributeService {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ServiceRegistryCenter registryCenter;

    @Override
    public String distribute(String serviceCode, String parameter) {
        logger.debug("开始分发服务， serviceCode[" + serviceCode + "], parameter[" + parameter + "]");
        Assert.notNull(serviceCode != null, "服务编码不能为null");

        if (parameter == null) parameter = "";
        String[] services = serviceCode.split(":");

        StringBuilder sb = new StringBuilder();

        for (String servicePair : services) {
            String[] pair = servicePair.trim().split("=");
            Assert.hasText(pair[0], "必须制定服务编码");

            String serviceName = pair[0].trim();

            if (pair.length >= 2) parameter = pair[1].trim();
            Object object = registryCenter.call(serviceName, parameter);

            sb.append("*******service[" + serviceName + "]**********\r\n");
            sb.append(object);
            sb.append("\r\n");
        }

        return sb.toString();
    }
}
