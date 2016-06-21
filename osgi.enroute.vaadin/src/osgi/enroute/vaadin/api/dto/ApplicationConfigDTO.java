package osgi.enroute.vaadin.api.dto;

import org.osgi.dto.DTO;

import osgi.enroute.vaadin.api.Configuration;
import osgi.enroute.vaadin.api.PushMode;
import osgi.enroute.vaadin.api.PushTransport;

/**
 * This DTO contains information about the current configuration of the
 * application.
 */
public class ApplicationConfigDTO extends DTO {

	/**
	 * A general description about the vaadin application.
	 */
	public String description;

	/**
	 * The alias where the web application can be accessed by its url.
	 */
	public String alias;

	/**
	 * The name of the widgetset that should be used for the current
	 * application.
	 */
	public String widgetset;

	/**
	 * The page title of the application.
	 */
	public String pageTitle;

	/**
	 * The theme which should be used for the application.
	 */
	public String theme;

	/**
	 * True if the production mode is active. False otherwise.
	 */
	public boolean productionMode;

	/**
	 * The number of seconds between heartbeat requests of a UI, or a
	 * non-positive number if heartbeat is disabled. Zero means Vaadin default.
	 */
	public int heartbeatInterval;

	/**
	 * The time resources can be cached in the browsers, in seconds. -1 means
	 * Vaadin default.
	 */
	public int resourceCacheTime;

	/**
	 * The mode of bidirectional ("push") communication that is in use.
	 */
	public PushMode pushMode;

	/**
	 * Transport mode for Push.
	 */
	public PushTransport pushTransport;

	public ApplicationConfigDTO() {

	}

	public ApplicationConfigDTO(Configuration config) {
		this.alias = config.alias();
		this.description = config.description();
		this.heartbeatInterval = config.heartbeatInterval();
		this.productionMode = config.productionMode();
		this.pushMode = config.pushMode();
		this.pushTransport = config.pushTransport();
		this.resourceCacheTime = config.resourceCacheTime();
		this.theme = config.theme();
		this.widgetset = config.widgetset();
	}

}
