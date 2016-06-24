# OSGI ENROUTE VAADIN

${Bundle-Description}

The API and providers allow you to create Vaadin applications on a very simple way. It is completely integrated with the config admin.   
An annotation for MetaType info is available. So you may use the webconsole to configure your application.

The component is providing metatype so the configuration can be edited in WebConsole. See `osgi.enroute.vaadin.api.Configuration` for details.

## Example
For an example see the package `osgi.enroute.vaadin.example`. It contains a fully implemented sample which can be configured by config admin.   

## Restrictions
For now only one Vaadin application configuration is supported.   
Each VaadinServlet needs to be registered by its own ServletContext. And support for the configuration of many servlet contexts has not been added to this project yet.

## References

## Credits
This project was created form ["Github: enRoute Vaadin Example"](http://osgi.github.com/osgi.enroute.vaadin.example "Github: enRoute Vaadin Example").  
So code was copied from there to this repo, renamed and extended. To get a list about all people worked on this project, checkout the committers in the enRoute Vaadin Example Github repo. 
