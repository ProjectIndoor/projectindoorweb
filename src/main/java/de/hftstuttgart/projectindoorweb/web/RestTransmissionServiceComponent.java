package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.inputHandler.InputHandler;

/**
 *  Rest Transmission component class to initialize and dispose of the Rest Transmission service.
 *  Do not use the <class>{@link RestTransmissionServiceImpl}</class> class implementation directly. Only initialize
 *  this class once through @initComponent on startup and access the object through the @getRestTransmissionServiceInstance method.
 */
public class RestTransmissionServiceComponent {
    private static RestTransmissionService instance;

    public static void initComponent(InputHandler inputHandler)
    {
        instance = new RestTransmissionServiceImpl(inputHandler);
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

    public static RestTransmissionService getRestTransmissionServiceInstance()
    {
        if (instance != null)
        {
            return instance;
        }

        throw new IllegalStateException ("REST transmission service is not initialized.");
    }
}
