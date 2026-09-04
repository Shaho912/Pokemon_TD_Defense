package org.cis1200.pokemon;

public class FireBlast extends Attack {
    public FireBlast(int sx, int sy, int courtWidth, int courtHeight,
                     Enemy target, Tower owner, boolean megaAttack) {
        super(sx, sy, 65, 65, courtWidth,
                courtHeight, 10, 32, target, owner, megaAttack);
        if (!megaAttack) {
            loadSprite("files/flameGIF.gif");
        } else {
            setDamage(100);
            loadSprite("files/blueflameGIF.gif");
        }
    }

}

