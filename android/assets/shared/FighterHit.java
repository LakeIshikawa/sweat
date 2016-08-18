package shared;

/**
 * Fighter hit information
 */
public class FighterHit {
    public enum DamageAnimType {
        LIGHT("L"),
        MEDIUM("M"),
        HEAVY("H");

        public String idchar;
        DamageAnimType(String idchar){
            this.idchar = idchar;
        }
    };

    public enum DamageAnimHeight {
        HIGH("H"),
        LOW("L");

        public String idchar;
        DamageAnimHeight(String idchar){
            this.idchar = idchar;
        }
    }

    // Fields
    public int damage;
    public String guardflags;
    public int pausetime;
    public DamageAnimType damageAnimType;
    public DamageAnimHeight damageAnimHeight;
    public boolean fall;
    public float ground_velocity;
    public int ground_slidetime;
    public float air_velocity;
}
