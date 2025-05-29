package com.mycompany.octobereats1;

import View.FrameMain;
import javax.swing.SwingUtilities;

public class OctoberEats1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrameMain();
        });
    }
}
