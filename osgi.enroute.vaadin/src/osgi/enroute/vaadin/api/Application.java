package osgi.enroute.vaadin.api;

import org.osgi.annotation.versioning.ConsumerType;

import com.vaadin.server.UICreateEvent;
import com.vaadin.ui.UI;

import osgi.enroute.vaadin.dto.ApplicationConfigDTO;
import osgi.enroute.vaadin.example.addressbook.application.Configuration;

/**
 * A service interface for bundles that want to create a Vaadin application. The
 * Vaadin application must be configured by config admin. See
 * {@link Configuration} annotation for this use and the default definitions
 * there.
 * <p>
 * Normally in Vaadin the instances are created by Vaadin but in this case the
 * application can setup the instances.
 * 
 * @param <T>
 *            the UI type this application supports
 */
@ConsumerType
public interface Application<T extends UI> {
	/**
	 * Service property for the alias
	 */
	String SERVICE_PROPERY_ALIAS = "alias";

	/**
	 * A relative ThemeResource path to the favIcon
	 */
	String SERVICE_PROPERY_FAV_ICON = "favIcon";

	/**
	 * Service property for the widgetset to be used.
	 */
	String SERVICE_PROPERY_WIDGETSET = "widgetset";

	/**
	 * Service property for the title
	 */
	String SERVICE_PROPERY_TITLE = "title";

	/**
	 * Service property for the production mode
	 */
	String SERVICE_PROPERY_PRODUCTION_MODE = "productionMode";

	/**
	 * Service property for the resourceCacheTime
	 */
	String SERVICE_PROPERY_RESOURCE_CACHE_TIME = "resourceCacheTime";

	/**
	 * Service property for the pushMode
	 */
	String SERVICE_PROPERY_PUSH_MODE = "pushMode";

	/**
	 * Service property for the pushTransport
	 */
	String SERVICE_PROPERY_PUSH_TRANSPORT = "pushTransport";

	/**
	 * Return the class to be used for the UI. This class can contain the @Theme
	 * annotation
	 * 
	 * @return the class
	 */
	Class<T> getUIClass();

	/**
	 * Create an instance for a new session. This class must extend the class
	 * returned from {@link #getUIClass()}
	 * 
	 * @param event
	 *            the create event
	 * @return
	 */
	T getInstance(UICreateEvent event);

	/**
	 * Returns the application config DTO.
	 * 
	 * @return
	 */
	ApplicationConfigDTO getApplicationConfigDto();

}
