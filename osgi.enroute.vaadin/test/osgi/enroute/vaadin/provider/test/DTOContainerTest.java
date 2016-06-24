package osgi.enroute.vaadin.provider.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vaadin.data.Item;

import osgi.enroute.vaadin.dto.ApplicationConfigDTO;
import osgi.enroute.vaadin.runtime.data.DTOContainer;

public class DTOContainerTest {

	@Test
	public void test() {
		DTOContainer<ApplicationConfigDTO> container = new DTOContainer<>(ApplicationConfigDTO.class);
		Item foo = container.addItem(createDto("foo"));
		Item bar = container.addItem(createDto("bar"));

		assertEquals("foo", foo.getItemProperty("alias"));
		assertEquals("bar", bar.getItemProperty("alias"));
	}

	private ApplicationConfigDTO createDto(String alias) {
		ApplicationConfigDTO dto = new ApplicationConfigDTO();
		dto.alias=alias;
		return dto;
	}
}
