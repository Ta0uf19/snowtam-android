package com.snowtam.io.utils.snowtam;

/**
 * Author : Taoufik Tribki
 * @Ta0uf19
 */
public enum  RunwayStates {
    /*
     * * F CONDITIONS SUR TOUTE LA LONGUEUR DE LA PISTE
     * Observées sur chaque tiers de la piste à partir du seuil qui porte le numéro d'identification de piste le plus faible :
     * 0 - PISTE DÉBLAYÉE ET SÈCHE (CLEAR AND DRY)
     * 1 - HUMIDE (DAMP)
     * 2 - MOUILLÉE OU FLAQUES D'EAU (WET or WATER PATCHES)
     * 3 - GIVRE OU GELÉE BLANCHE - épaisseur normalement moins de 1 mm - (RIME OR FROSTCOVERED)
     * 4 - NEIGE SÈCHE (DRY SNOW)
     * 5 - NEIGE MOUILLÉE (WET SNOW)
     * 6 - NEIGE FONDANTE (SLUSH)
     * 7 - GLACE (ICE)
     * 8 - NEIGE COMPACTÉE (COMPACTED OR ROLLED SNOW)
     * 9 - ORNIÈRES ET ARÊTES (FROZEN RUTS OR RIDGES)
     * Si plus d’un dépôt est présent sur une même portion de la piste, il est reporté en séquence de 1 a
     * 9. Les cas particuliers peuvent être reportés dans le champ T en langage clair.
     *
     */

    CLEAR,
    DAMP,
    WET,
    RIME,
    DRYSNOW,
    WETSNOW,
    SLUSH,
    ICE,
    COMPACTED,
    FROZENRUTS;

    private static RunwayStates[] list = RunwayStates.values();

    public static RunwayStates getState(int i) {
        return list[i];
    }
    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
