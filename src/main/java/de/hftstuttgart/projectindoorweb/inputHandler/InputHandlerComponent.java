package de.hftstuttgart.projectindoorweb.inputHandler;

import java.util.concurrent.ExecutorService;

/**
 *  Input handler component class to initialize and dispose of the Eva File input handler.
 *  Do not use the <class>{@link EvaalFileInputHandler}</class> class implementation directly. Only initialize
 *  this class once through @initComponent on startup and access the object through the @getInputHandler method.
 */
public class InputHandlerComponent {

    private static InputHandler instance;

    public static void initComponent(ExecutorService executorService)
    {
        instance = new EvaalFileInputHandler(executorService);
    }

    public static void disposeComponent()
    {
        if (instance != null)
        {
            instance = null;
        }
        else
        {
            System.out.print("Input handler was not initialised when trying to dispose component.");
        }

    }

    public static InputHandler getInputHandler()
    {
        if (instance != null)
        {
            return instance;
        }

        throw new IllegalStateException ("Input handler is not initialised.");
    }
}
