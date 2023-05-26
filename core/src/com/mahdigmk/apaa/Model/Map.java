package com.mahdigmk.apaa.Model;

import com.badlogic.gdx.graphics.Color;

public enum Map {
    GRASS_LANDS(3,
            new Color(.5f, .5f, .2f, 1),
            new Color(.7f, .9f, .2f, 1),
            new Color(.8f, .8f, .8f, 1)),
    DRY_LANDS(6,
            new Color(.6f, .3f, .1f, 1),
            new Color(.8f, .6f, .2f, 1),
            new Color(.3f, .2f, .1f, 1)),
    FIRE_LANDS(10,
            new Color(.8f, .4f, .1f, 1),
            new Color(1f, .3f, .1f, 1),
            new Color(1f, .7f, 0, 1));

    private final int initialBallCount;
    private final Color innerColor;
    private final Color outerColor;
    private final Color detailColor;

    private Map(int initialCount, Color innerColor, Color outerColor, Color detailColor) {
        this.initialBallCount = initialCount;
        this.innerColor = innerColor;
        this.outerColor = outerColor;
        this.detailColor = detailColor;
    }

    public Color getDetailColor() {
        return detailColor;
    }

    public Color getInnerColor() {
        return innerColor;
    }

    public Color getOuterColor() {
        return outerColor;
    }

    public int getInitialBallCount() {
        return initialBallCount;
    }
}
