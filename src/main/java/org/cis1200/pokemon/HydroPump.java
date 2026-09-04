package org.cis1200.pokemon;

public class HydroPump extends Attack {
    public HydroPump(int sx, int sy, int courtWidth, int courtHeight,
                     Enemy target, Tower owner, boolean megaAttack) {
        super(sx, sy, 65, 65, courtWidth,
                courtHeight, 10, 30, target, owner, megaAttack);
        if (!megaAttack) {
            loadSprite("files/waterSphere1.png");
        } else {
            loadSprite("files/waterGun.gif");
            setDamage(60);
        }
    }

}

