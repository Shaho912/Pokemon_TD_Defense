package org.cis1200.pokemon;

public class ElectroBall extends Attack {
    public ElectroBall(int sx, int sy, int courtWidth, int courtHeight,
                       Enemy target, Tower owner, boolean megaAttack) {
        super(sx, sy, 65, 65, courtWidth,
                courtHeight, 10, 20, target, owner, megaAttack);
        loadSprite("files/ElectroBall.png");
    }

}

