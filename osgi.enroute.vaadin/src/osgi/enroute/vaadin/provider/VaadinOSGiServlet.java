package osgi.enroute.vaadin.provider;

import java.util.Properties;

import org.osgi.framework.ServiceReference;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Constants;
import com.vaadin.server.DefaultDeploymentConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.UI;

import osgi.enroute.vaadin.api.Application;
import osgi.enroute.vaadin.api.PushMode;

@SuppressWarnings("serial")
@VaadinServletConfiguration(productionMode = false, ui = UI.class)
public class VaadinOSGiServlet extends VaadinServlet {

	Application<?> application;
	ServiceReference<?> ref;

	public VaadinOSGiServlet(Application<?> application, ServiceReference<?> ref) {
		this.application = application;
		this.ref = ref;
	}

	@Override
	protected DeploymentConfiguration createDeploymentConfiguration(Properties initParameters) {

		fillParameters(initParameters);

		return new DefaultDeploymentConfiguration(getClass(), initParameters);
	}

	private void fillParameters(Properties initParameters) {
		String widgetSet = (String) ref.getProperty(Application.SERVICE_PROPERY_WIDGETSET);
		if (widgetSet != null && !widgetSet.trim().isEmpty()) {
			initParameters.setProperty(Constants.PARAMETER_WIDGETSET, widgetSet);
		}
		Boolean productionMode = (Boolean) ref.getProperty(Application.SERVICE_PROPERY_PRODUCTION_MODE);
		initParameters.setProperty(SERVLET_PARAMETER_PRODUCTION_MODE,
				productionMode != null ? productionMode.toString() : "false");

		PushMode pushMode = (PushMode) ref.getProperty(Application.SERVICE_PROPERY_PUSH_MODE);
		if (pushMode != null) {
			initParameters.setProperty(SERVLET_PARAMETER_PUSH_MODE, pushMode.toString());
		}

		int cacheTime = (int) ref.getProperty(Application.SERVICE_PROPERY_RESOURCE_CACHE_TIME);
		if (cacheTime >= 0) {
			initParameters.setProperty(SERVLET_PARAMETER_RESOURCE_CACHE_TIME, Integer.toString(cacheTime));
		}
		initParameters.setProperty(SERVLET_PARAMETER_UI_PROVIDER, VaadinApplicationUIProvider.class.getName());
	}

	class LocalVaadinServletService extends VaadinServletService {

		public LocalVaadinServletService(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration)
				throws ServiceException {
			super(servlet, deploymentConfiguration);
		}

		Application<?> getApplication() {
			return application;
		}
	}

	@Override
	protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {
		try {

			LocalVaadinServletService service = new LocalVaadinServletService(this, deploymentConfiguration);
			service.init();
			service.setClassLoader(new ClassLoader() {
				@Override
				public Class<?> loadClass(String name) throws ClassNotFoundException {
					try {
						try {
							Class<?> loadClass = VaadinOSGiServlet.class.getClassLoader().loadClass(name);
							return loadClass;
						} catch (ClassNotFoundException e) {
							return ref.getBundle().loadClass(name);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			});

			return service;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
