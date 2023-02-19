package com.adservio.osgi.impl;

import com.adservio.osgi.definition.Greeting;
import org.osgi.framework.*;

public class HelloWorldBundle implements BundleActivator, ServiceListener {

    private BundleContext context;
    private ServiceReference serviceRef;

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("%s started".formatted(this.getClass().getName()));
        this.context = bundleContext;
        bundleContext.addServiceListener(this, "(objectClass=%s)".formatted(Greeting.class.getName()));
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("%s stopped".formatted(this.getClass().getName()));
        if (this.serviceRef != null) this.context.ungetService(this.serviceRef);
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        switch (serviceEvent.getType()) {
            case ServiceEvent.REGISTERED -> {
                this.serviceRef = serviceEvent.getServiceReference();
                System.out.println(
                        ((Greeting) this.context.getService(this.serviceRef)).sayHello("NOUNI EL Bachir")
                );
                System.out.println("Greeting Service registered !");
            }
            case ServiceEvent.UNREGISTERING -> {
                System.out.println("Greeting Service unregistered");
                this.context.ungetService(this.serviceRef);
            }
        }
    }
}
