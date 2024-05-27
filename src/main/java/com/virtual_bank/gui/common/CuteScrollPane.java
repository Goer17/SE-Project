package com.virtual_bank.gui.common;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CuteScrollPane extends JScrollPane {

    // 滚动面板
    public CuteScrollPane(Component view) {
        super(view);
        setBorder(BorderFactory.createEmptyBorder());
        // 边框为空
        setViewportBorder(BorderFactory.createEmptyBorder());

        // 视口边框为空
        getVerticalScrollBar().setUI(new CuteScrollBarUI());
        // 垂直滚动条
        getHorizontalScrollBar().setUI(new CuteScrollBarUI());
        // 水平滚动条
    }

    // 滚动条UI
    private static class CuteScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(204, 187, 77, 255);
            // 滚动条滑块颜色
            this.trackColor = new Color(194, 177, 105, 116);
            // 滚动条轨道颜色
        }

        // 减少按钮
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        // 增加按钮
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        // 0按钮
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        // 滚动条滑块
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 抗锯齿
            g2.setColor(thumbColor);
            // 颜色
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            // 圆角
            g2.dispose();
        }

        // 滚动条轨道
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 抗锯齿
            g2.setColor(trackColor);
            // 颜色
            g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10);
            // 圆角
            g2.dispose();
        }
    }
}
