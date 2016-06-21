package osgi.enroute.vaadin.example;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.vaadin.server.UICreateEvent;

import osgi.enroute.configurer.api.RequireConfigurerExtender;
import osgi.enroute.vaadin.api.Application;
import osgi.enroute.vaadin.api.Configuration;
import osgi.enroute.vaadin.api.dto.ApplicationConfigDTO;

@Designate(ocd = Configuration.class)
@RequireConfigurerExtender
@Component(name = "osgi.enroute.example.vaadin.addressbook", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class AddressbookApplication implements Application<AddressbookUI> {

	@Reference
	ContactService contacts;

	Configuration config;

	@Activate
	protected void activate(Configuration config) {
		this.config = config;
	}

	@Deactivate
	protected void deactivate(Configuration config) {
		this.config = null;
	}

	@Override
	public Class<AddressbookUI> getUIClass() {
		return AddressbookUI.class;
	}

	@Override
	public AddressbookUI getInstance(UICreateEvent event) {
		return new AddressbookUI(contacts);
	}

	@Override
	public ApplicationConfigDTO getApplicationConfigDto() {
		return new ApplicationConfigDTO(config);
	}
}
