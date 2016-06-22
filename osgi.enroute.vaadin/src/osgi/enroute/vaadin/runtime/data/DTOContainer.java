package osgi.enroute.vaadin.runtime.data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.osgi.dto.DTO;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;

/**
 * Vaadin container to be used with {@link DTO OSGi DTOs}.
 */
public class DTOContainer<T> extends IndexedContainer {
	private static final long serialVersionUID = 1L;
	private final Field[] fields;
	private final T defaults;

	private LogService logger;
	
	/**
	 * If c has an default constructor, the default values from the instance
	 * will be used, as {@link Property} default values.
	 * 
	 * @param c
	 */
	public DTOContainer(Class<T> c) {
		installLogService();
		
		this.fields = c.getFields();
		Arrays.sort(this.fields, (a, b) -> a.getName().compareTo(b.getName()));

		T d = null;
		try {
			d = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// that's ok
		}
		defaults = d;

		for (Field f : fields)
			try {
				addContainerProperty(f.getName(), f.getType(), defaults != null ? f.get(defaults) : null);
			} catch (Exception e) {

			}
	}

	private void installLogService() {
		ServiceTracker<LogService, LogService> tracker = new ServiceTracker<>(FrameworkUtil.getBundle(getClass()).getBundleContext(), LogService.class, null);
		tracker.open();
		try {
			logger = tracker.waitForService(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addAll(List<T> all) {
		for (T c : all) {
			add(c);
		}
	}

	protected Item add(T c) {
		Item item = addItem(c);
		for (Field f : fields) {
			try {
				@SuppressWarnings("unchecked")
				Property<Object> p = item.getItemProperty(f.getName());
				p.setValue(f.get(c));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return item;
	}
}
