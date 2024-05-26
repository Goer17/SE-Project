package com.virtual_bank.gui.common;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CuteScrollPane extends JScrollPane {

    // 构造函数，创建带自定义滚动条的滚动面板
    public CuteScrollPane(Component view) {
        super(view);
        setBorder(BorderFactory.createEmptyBorder());  // 设置边框为空
        setViewportBorder(BorderFactory.createEmptyBorder());  // 设置视口边框为空
        getVerticalScrollBar().setUI(new CuteScrollBarUI());  // 设置垂直滚动条的UI
        getHorizontalScrollBar().setUI(new CuteScrollBarUI());  // 设置水平滚动条的UI
    }

    // 内部静态类，自定义滚动条的UI
    private static class CuteScrollBarUI extends BasicScrollBarUI {

        // 配置滚动条的颜色
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(204, 187, 77, 255); // 滚动条滑块的颜色
            this.trackColor = new Color(194, 177, 105, 116); // 滚动条轨道的颜色
        }

        // 创建减少按钮，将其大小设置为0
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        // 创建增加按钮，将其大小设置为0
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        // 创建一个大小为0的按钮
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        // 绘制滚动条滑块
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // 启用抗锯齿
            g2.setColor(thumbColor);  // 设置滑块颜色
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);  // 绘制圆角矩形滑块
            g2.dispose();
        }

        // 绘制滚动条轨道
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // 启用抗锯齿
            g2.setColor(trackColor);  // 设置轨道颜色
            g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10);  // 绘制圆角矩形轨道
            g2.dispose();
        }
    }
}
