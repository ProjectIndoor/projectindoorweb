package de.hftstuttgart.projectindoorweb.web;

/**
 *  Rest Transmission component class to initialize and dispose of the Rest Transmission service.
 *  Do not use the <class>{@link RestTransmissionServiceImpl}</class> class implementation directly. Only initialize
 *  this class once through @initComponent on startup and access the object through the @getDeviceAccessor method.
 */
public class RestTransmissionServiceComponent {
    private static RestTransmissionService instance;

    public static void initComponent()
    {
        instance = new RestTransmissionServiceImpl();
    }

    public static void disposeComponent()
    {
        if (instance != null)
        {
            instance = null;
        }
        else
        {
            System.out.print("REST transmission service was not initialised when trying to dispose component.");
        }

    }

    public static RestTransmissionService getDeviceAccessor()
    {
        if (instance != null)
        {
            return instance;
        }

        throw new IllegalStateException ("REST transmission service is not initialized.");
    }
}
