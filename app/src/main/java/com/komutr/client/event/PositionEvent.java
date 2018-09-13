package com.komutr.client.event;

import com.komutr.client.been.Position;

public class PositionEvent {

    //选择位置
    public static final int CHOOSE_POSITION_SUCCESS = 1;

    private int stateType;

    private Position position;


    public PositionEvent(int stateType) {
        this.stateType = stateType;
    }

    public PositionEvent(int stateType,Position position) {
        this.stateType = stateType;
        this.position = position;
    }

    public int getStateType() {
        return stateType;
    }

    public Position getPosition() {
        return position;
    }
}
