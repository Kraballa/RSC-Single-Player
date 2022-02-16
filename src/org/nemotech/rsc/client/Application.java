package org.nemotech.rsc.client;

import org.nemotech.rsc.Constants;

import java.awt.*;
import java.awt.event.*;

public class Application extends Frame {

    private Shell shell;

    public Application(Shell shell) {
        this.shell = shell;
        setTitle(Constants.APPLICATION_TITLE);
        setBackground(Color.BLACK);
        setResizable(true);
        pack();
        toFront();
        setVisible(true);
        setSize(Constants.APPLICATION_WIDTH, Constants.APPLICATION_HEIGHT);
        setMinimumSize(new Dimension(512, 344));
        setMaximumSize(new Dimension(1535, 1800));
        setLocationRelativeTo(null); // center the frame
        addListeners();
    }
    
    private void addListeners() {
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                shell.handleMouseScroll(rotation);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shell.closeProgram();
            }
        });
        if(Constants.APPLICATION_RESIZABLE) {
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int width = getInnerWidth();
                    int height = getInnerHeight();
                    shell.doResize(width, height);
                }
            });
        }
    }

    @Override
    public Graphics getGraphics() {
        Graphics graphics = super.getGraphics();
        Insets insets = getInsets();
        graphics.translate(insets.left, insets.top);
        return graphics;
    }

    @Override
    public void setSize(int x, int y) {
        Insets insets = getInsets();
        super.setSize(x - insets.left - insets.right, y - insets.top - insets.bottom);
    }

    @Override
    protected void processEvent(AWTEvent e) {
        if (e instanceof MouseEvent) {
            if(e instanceof MouseWheelEvent) {
                MouseWheelEvent evt = (MouseWheelEvent) e;
                int evtX = evt.getX() - getInsets().left;
                int evtY = evt.getY() - getInsets().top;
                e = new MouseWheelEvent(evt.getComponent(), evt.getID(), evt.getWhen(), evt.getModifiers(), evtX, evtY, evt.getClickCount(), evt.isPopupTrigger(), evt.getScrollType(), evt.getScrollAmount(), evt.getWheelRotation());
            } else {
                MouseEvent evt = (MouseEvent) e;
                int evtX = evt.getX() - getInsets().left;
                int evtY = evt.getY() - getInsets().top;
                e = new MouseEvent(evt.getComponent(), evt.getID(), evt.getWhen(), evt.getModifiers(),evtX, evtY, evt.getClickCount(), evt.isPopupTrigger());
            }
        }
        super.processEvent(e);
    }

    @Override
    public void paint(Graphics g) {
        shell.paint(g);
    }

    public int getLeft(){
        return getInsets().left;
    }

    public int getInnerWidth(){
        Insets insets = getInsets();
        return getWidth() - insets.left - insets.right;
    }

    public int getInnerHeight(){
        Insets insets = getInsets();
        return getHeight() - insets.top - insets.bottom;
    }
}
