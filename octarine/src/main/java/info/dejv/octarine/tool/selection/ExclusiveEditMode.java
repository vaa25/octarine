package info.dejv.octarine.tool.selection;

/**
 * Edit mode, that requires exclusive activation (not cooperable with other exclusive edit modes)
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ExclusiveEditMode
        extends EditMode {

    /**
     * Notify the EditMode to install its exclusive activation request handlers
     */
    void installActivationHandlers();


    /**
     * Notify the EditMode to uninstall its exclusive activation request handlers
     */
    void uninstallActivationHandlers();
}
