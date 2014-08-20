package app.dejv.octarine.tool.editmode;

/**
 * Edit mode, that requires exclusive activation (not cooperable with other exclusive edit modes)
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ExclusiveEditMode
        extends EditMode {

    /**
     * Set the edit mode exclusivity enforcer.
     *
     * @param exclusivityCoordinator Exclusivity enforcer
     */
    void setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator);


    /**
     * Notify the EditMode to install its exclusive activation request handlers.
     */
    void installActivationHandlers();


    /**
     * Notify the EditMode to uninstall its exclusive activation request handlers.
     */
    void uninstallActivationHandlers();
}
