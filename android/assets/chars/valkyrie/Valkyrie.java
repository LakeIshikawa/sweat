package chars.valkyrie;

import shared.Fighter;

/**
 * Valkyrie fighter
 */
public class Valkyrie extends Fighter {

    // Initialization
    public Valkyrie(){
        scale = 0.8f;
        loadAnimationPack("chars/valkyrie/valkyrie.anm");

        // Middle punch
        shmpunchHit.damage = 30;
    }
}