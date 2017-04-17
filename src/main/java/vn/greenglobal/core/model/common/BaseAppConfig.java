package vn.greenglobal.core.model.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import io.katharsis.resource.registry.ServiceUrlProvider;

public class BaseAppConfig {
	public ServiceUrlProvider getServiceUrlProvider() {
		return new ServiceUrlProvider() {
			@Value("${katharsis.pathPrefix}")
			private String pathPrefix;

			@Override
			public String getUrl() {
				RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
			    if (attribs instanceof NativeWebRequest) {
			        HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
					return request.getScheme() + "://" + request.getHeader("host") + request.getContextPath() + pathPrefix;
			    }
			    return pathPrefix;
			}
		};
	}

	public FilterRegistrationBean requestLoggingFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		CommonsRequestLoggingFilter requestDumperFilter = new CommonsRequestLoggingFilter();
		requestDumperFilter.setIncludePayload(true);
		requestDumperFilter.setMaxPayloadLength(1000);
		requestDumperFilter.setIncludeClientInfo(true);
		requestDumperFilter.setIncludeHeaders(true);
		requestDumperFilter.setIncludeQueryString(true);
		registration.setFilter(requestDumperFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}
}
