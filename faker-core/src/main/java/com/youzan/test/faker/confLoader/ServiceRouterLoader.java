package com.youzan.test.faker.confLoader;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libaixian on 16/7/31.
 */
@Component
public class ServiceRouterLoader {
    public final Logger logger = LoggerFactory.getLogger(ServiceRouterLoader.class);
    public final String ServiceRouterFile = "conf/serviceRouter.xml";

    private Map<String, String> services = null;

    public Map<String, String> load() {
        if (services != null) {
            return services;
        }

        synchronized (this) {
            if (services != null) {
                return services;
            }

            try {
                SAXReader reader = new SAXReader();
                InputStream input = getClass().getClassLoader().getResourceAsStream(ServiceRouterFile);
                if (input == null) {
                    throw new RuntimeException(String.format("Service config file [%s] not found", this.ServiceRouterFile));
                }

                Document doc = reader.read(input);
                Element root = doc.getRootElement();
                List<Element> serviceNodes = root.elements();
                if (serviceNodes == null || serviceNodes.size() == 0) {
                    return null;
                }

                services = new HashMap<String, String>();
                for (Element each : serviceNodes) {
                    String url = each.attributeValue("url");
                    String classHandler = each.attributeValue("class");
                    if (StringUtils.isEmpty(url) || StringUtils.isEmpty(classHandler)) {
                        logger.warn("Skip service router config: url={} and class={}", url, classHandler);
                        continue;
                    }

                    services.put(url, classHandler);
                }

                return services;
            } catch (DocumentException e) {
                logger.error("Run into an error when parsing service config file {} \n Exception: {}", this.ServiceRouterFile, e.getMessage());
                throw new RuntimeException(e.getMessage());
            } catch (Exception e) {
                logger.error("Run into an error when parsing service config file {} \n Exception: {}", this.ServiceRouterFile, e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
