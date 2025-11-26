package com.anonym.astran.api.swiff.sdf;

public enum SDFType {
    CIRCLE(0),
    PENTAGON(1),
    BOX(2),
    RHOMBUS(3),
    PENTAGRAM(4),
    OCTOGON(5),
    HEXAGRAM(6);

    public int id;

    SDFType(int id) {
        this.id = id;
    }
}
