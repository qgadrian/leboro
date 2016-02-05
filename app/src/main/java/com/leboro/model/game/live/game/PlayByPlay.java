package com.leboro.model.game.live.game;

public class PlayByPlay {

    private static class PlayLine {

        private int order;

        private String text;

        private PlayKind playKind;

        enum PlayKind {
            HOME, AWAY, NEUTRAL
        }

    }
}
