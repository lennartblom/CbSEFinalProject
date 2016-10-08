package api;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Die Klasse Navigation stellt Funktionen bereit, die zur Weiterleitung auf den
 * xhtml-Seiten dienen.
 *
 * @author Miriam
 */

@Named("navigation")
@RequestScoped
public class Navigation implements Serializable {

    /**
     * Erstellt eine Navigation ohne Eigenschaften.
     */
    public Navigation() {

    }

    /**
     * Gibt einen Pfad als String zum Index zurueck.
     *
     * @return Indexpfad
     */
    public static String goToIndex() {
        return "./index.xhtml";
    }

    /**
     * Gibt einen Pfad als String zur Ausleihseite zurueck.
     *
     * @return Ausleihpfad
     */
    public static String goToLendMaterial() {
        return "./lending.xhtml";
    }

    /**
     * Gibt einen Pfad als String zur Uebersicht zurueck.
     *
     * @return Uebersichtpfad
     */
    public static String goToOverview() {
        return "./overview.xhtml";
    }

    /**
     * Gibt einen Pfad als String zum Material hinzufuegen zurueck.
     *
     * @return Material hinzufuegen Pfad
     */
    public static String goToAddMaterial() {
        return "./adding.xhtml";
    }

    /**
     * Gibt einen Pfad als String zur Seite aus, die die erfolgreiche Ausleihe
     * anzeigt.
     *
     * @return Ausleihe erfolgreich Pfad
     */
    public static String goToSuccessPage() {
        return "./lendingSuccess.xhtml";
    }

    /**
     * Gibt einen Pfad als String zur Uebersicht der Ausleihdetails zurueck.
     *
     * @return Ausleihdetailspfad
     */
    public static String goToLendingConfirmation() {
        return "./lendingConfirmation.xhtml";
    }

    /**
     * Gibt einen Pfad als String zur Bearbeitung des Materials zurueck.
     *
     * @return Material bearbeiten Pfad
     */
    public static String goToEditMaterial() {
        return "./editMaterial.xhtml";
    }
}
