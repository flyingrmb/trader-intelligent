package org.actech.smart.trader.registry.service.impl;

import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.registry.entity.ServiceDescriptor;
import org.actech.smart.trader.registry.service.ServiceEncoder;
import org.actech.smart.trader.registry.service.ServiceRegistryCenter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by paul on 2018/3/14.
 */
@Service
public class ServiceRegistryCenterImpl implements ServiceRegistryCenter {
    private final Log logger = LogFactory.getLog(this.getClass());

    private Map<String, ServiceDescriptor> services = null;
    private ExecutorService executor = Executors.newFixedThreadPool(20);

    @Override
    public Object call(String serviceName, String parameter) {
        if (services == null) register();

        ServiceDescriptor descriptor = services.get(serviceName);
        Assert.notNull(descriptor, "服务未注册，请使用service/help查阅服务注册情况");

        if (!descriptor.getServicePoint().async()) {
            return ReflectionUtils.invokeMethod(descriptor.getMethod(), descriptor.getBean(), parameter);
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                ReflectionUtils.invokeMethod(descriptor.getMethod(), descriptor.getBean(), parameter);
            }
        });

        return "Async Executing...";
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServiceEncoder encodeService;

    private synchronized void register() {
        if (services != null) return ;

        logger.info("服务注册程序启动，开始注册服务>>>>>");

        services = new TreeMap<String, ServiceDescriptor>();
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(Registry.class);
        if (beanNames == null) return ;

        for (String beanName : beanNames) {
            Object object = applicationContext.getBean(beanName);
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(object.getClass());
            if (methods == null) continue;

            for (Method method : methods) {
                ServicePoint servicePoint = AnnotationUtils.findAnnotation(method, ServicePoint.class);
                if (servicePoint == null) continue;

                ServiceDescriptor descriptor = new ServiceDescriptor();
                descriptor.setServicePoint(servicePoint);

                descriptor.setBean(object);
                descriptor.setMethod(method);

                String serviceCode = encodeService.register(servicePoint.code());
                descriptor.setServiceCode(serviceCode);
                services.put(serviceCode, descriptor);
            }
        }

        logger.info("完成服务注册，共发现" + services.size() +"个服务");
    }

    @Override
    public String dashboard() {
        if (services == null) register();

        StringBuilder sb = new StringBuilder();
        sb.append("【服务清单明细】********* 服务总数（" + services.size() + "）\r\n");
        for (String key : services.keySet()) {
            ServiceDescriptor descriptor = services.get(key);
            sb.append("\t[服务编码]：" + descriptor.getServiceCode() + "\r\n");
            sb.append("\t[服务名称]：" + descriptor.getServicePoint().name() + "\r\n");
            sb.append("\t[使用样例]: \r\n");
            String[] examples = descriptor.getServicePoint().example();
            if (examples == null) continue;

            for (String example : examples) {
                sb.append("\t\t" + example + "\r\n");
            }

            sb.append("\r\n");
        }

        return sb.toString();
    }
}
