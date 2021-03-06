package osgi.enroute.vaadin.example.addressbook.application;

import java.util.List;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import osgi.enroute.vaadin.runtime.data.DTOContainer;

/**
 * User Interface written in Java.
 * <p>
 * Define the user interface shown on the Vaadin generated web page by extending
 * the UI class. By default, a new UI instance is automatically created when the
 * page is loaded. To reuse the same instance, add @PreserveOnRefresh.
 */
@Title("Addressbook") // fallback if configuration is not set
@Theme("valo") // fallback if configuration is not set
public class AddressbookUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Hundreds of widgets. Vaadin's user interface components are just Java
	 * objects that encapsulate and handle cross-browser support and
	 * client-server communication. The default Vaadin components are in the
	 * com.vaadin.ui package and there are over 500 more in
	 * vaadin.com/directory.
	 */
	TextField filter = new TextField();
	Grid contactList = new Grid();
	Button newContact = new Button("New contact");

	// ContactForm is an example of a custom component class
	ContactForm contactForm = new ContactForm();

	// ContactService is a in-memory mock DAO that mimics
	// a real-world datasource. Typically implemented for
	// example as EJB or Spring Data based service.
	ContactService service;

	// private AddressbookApplication addressbookApplication;

	public AddressbookUI(ContactService contacts) {
		this.service = contacts;
	}

	// Only there for Vaadin who checks if it is a valid class not realizing
	// that you can also created it correctly
	public AddressbookUI() {
		throw new UnsupportedOperationException();
	}

	/*
	 * The "Main method".
	 *
	 * This is the entry point method executed to initialize and configure the
	 * visible user interface. Executed on every browser reload because a new
	 * instance is created for each web page loaded.
	 */
	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		/*
		 * Synchronous event handling.
		 *
		 * Receive user interaction events on the server-side. This allows you
		 * to synchronously handle those events. Vaadin automatically sends only
		 * the needed changes to the web page without loading a new page.
		 */
		newContact.addClickListener(e -> contactForm.edit(new Contact()));

		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		DTOContainer<Contact> container = new DTOContainer<>(Contact.class);

		contactList.setContainerDataSource(container);
		contactList.setColumnOrder("firstName", "lastName", "email");
		contactList.removeColumn("id");
		contactList.removeColumn("birthDate");
		contactList.removeColumn("phone");
		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		// contactList.addSelectionListener(e -> contactForm.edit((Contact)
		// contactList.getSelectedRow()));
		// creates error, not finding contact
		refreshContacts();
	}

	/*
	 * Robust layouts.
	 *
	 * Layouts are components that contain other components. HorizontalLayout
	 * contains TextField and Button. It is wrapped with a Grid into
	 * VerticalLayout for the left side of the screen. Allow user to resize the
	 * components with a SplitPanel.
	 *
	 * In addition to programmatically building layout in Java, you may also
	 * choose to setup layout declaratively with Vaadin Designer, CSS and HTML.
	 */
	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter, newContact);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, contactList);
		left.setSizeFull();
		contactList.setSizeFull();
		left.setExpandRatio(contactList, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		setContent(mainLayout);
	}

	/*
	 * Choose the design patterns you like.
	 *
	 * It is good practice to have separate data access methods that handle the
	 * back-end access and/or the user interface updates. You can further split
	 * your code into classes to easier maintenance. With Vaadin you can follow
	 * MVC, MVP or any other design pattern you choose.
	 */
	void refreshContacts() {
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		List<Contact> all = service.findAll(stringFilter);
		DTOContainer<Contact> container = new DTOContainer<>(Contact.class);
		container.addAll(all);
		contactList.setContainerDataSource(container);
		contactForm.setVisible(false);
	}

}
