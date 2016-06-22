package osgi.enroute.vaadin.provider;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.UI;

import osgi.enroute.vaadin.provider.VaadinOSGiServlet.LocalVaadinServletService;

@SuppressWarnings("serial")
public class VaadinApplicationUIProvider extends UIProvider {

	public UI createInstance(UICreateEvent event) {
		LocalVaadinServletService service = (LocalVaadinServletService) event.getRequest().getService();
		UI ui = service.getApplication().getInstance(event);

		applyPageTitle(service, ui);
		
		return ui;
	}

	private void applyPageTitle(LocalVaadinServletService service, UI ui) {
		String title = service.getApplication().getApplicationConfigDto().pageTitle;
		if (title != null && !title.equals("")) {
			ui.getPage().setTitle(title);
		}
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		LocalVaadinServletService service = (LocalVaadinServletService) event.getRequest().getService();
		return service.getApplication().getUIClass();
	}

	@Override
	public String getTheme(UICreateEvent event) {
		LocalVaadinServletService service = (LocalVaadinServletService) event.getRequest().getService();
		String theme = service.getApplication().getApplicationConfigDto().theme;
		if (theme == null || theme.equals("")) {
			theme = super.getTheme(event);
		}
		return theme;
	}

	@Override
	public String getPageTitle(UICreateEvent event) {
		LocalVaadinServletService service = (LocalVaadinServletService) event.getRequest().getService();
		String title = service.getApplication().getApplicationConfigDto().pageTitle;
		if (title == null || title.equals("")) {
			title = super.getPageTitle(event);
		}
		return title;
	}

	@Override
	public PushMode getPushMode(UICreateEvent event) {
		LocalVaadinServletService service = (LocalVaadinServletService) event.getRequest().getService();
		osgi.enroute.vaadin.api.PushMode pushMode = service.getApplication().getApplicationConfigDto().pushMode;
		if (pushMode == null) {
			return super.getPushMode(event);
		}
		return PushMode.valueOf(pushMode.name());
	}

	@Override
	public Transport getPushTransport(UICreateEvent event) {
		LocalVaadinServletService service = (LocalVaadinServletService) event.getRequest().getService();
		osgi.enroute.vaadin.api.PushTransport pushTransport = service.getApplication()
				.getApplicationConfigDto().pushTransport;
		if (pushTransport == null) {
			return super.getPushTransport(event);
		}
		return Transport.valueOf(pushTransport.name());
	}

}