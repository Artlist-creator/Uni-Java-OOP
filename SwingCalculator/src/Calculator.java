import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {

    private float res = 0;
    private String intermittent = "0", operator = null;

    private JButton[] buttons = new JButton[16];
    private JLabel display = new JLabel(intermittent);
    private final int WIDTH = 350, HEIGHT = 450, BUTTON_WIDTH = (int) (WIDTH * 0.25), BOTTOM = HEIGHT - (int) (BUTTON_WIDTH * 1.35);

    public Calculator() {
        __init__();
    }

    private void __init__() {
        JFrame canvas = new JFrame("Simple Swing Calculator");
        canvas.setLayout(null);
        canvas.setSize(WIDTH, HEIGHT);
        canvas.setIconImage(new ImageIcon("data/favicon-32x32.png").getImage());
        canvas.setBackground(Color.black);

        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton(String.valueOf(i));
            buttons[i].setBackground(new Color(216, 217, 219));
        }

        buttons[10] = new JButton(".");
        buttons[11] = new JButton("=");
        buttons[12] = new JButton("+");
        buttons[13] = new JButton("-");
        buttons[14] = new JButton("×");
        buttons[15] = new JButton("÷");
        for (int i = 10; i < 16; i++) {
            if (i == 10 || i == 11) {
                buttons[i].setBackground(new Color(216, 217, 219));
                continue;
            }
            buttons[i].setBackground(new Color(223, 151, 76));
        }

        Font buttonFont = new Font(buttons[0].getFont().getName(), buttons[0].getFont().getStyle(), 20);
        for (JButton button : buttons) {
            canvas.add(button);
            button.setSize(BUTTON_WIDTH, BUTTON_WIDTH);
            button.setFont(buttonFont);
        }

        canvas.add(display);
        display.setSize(WIDTH, (int) (0.165 * HEIGHT));
        display.setLocation(0, 0);
//        display.setBorder(BorderFactory.createLineBorder(Color.black));
        display.setFont(new Font(display.getFont().getName(), display.getFont().getStyle(), 30));


        buttons[0].setLocation(0, BOTTOM);
        buttons[3].setLocation(0, BOTTOM - BUTTON_WIDTH);
        buttons[6].setLocation(0, BOTTOM - BUTTON_WIDTH * 2);
        buttons[9].setLocation(0, BOTTOM - BUTTON_WIDTH * 3);

        buttons[10].setLocation(BUTTON_WIDTH, BOTTOM);
        buttons[2].setLocation(BUTTON_WIDTH, BOTTOM - BUTTON_WIDTH);
        buttons[5].setLocation(BUTTON_WIDTH, BOTTOM - BUTTON_WIDTH * 2);
        buttons[8].setLocation(BUTTON_WIDTH, BOTTOM - BUTTON_WIDTH * 3);

        buttons[11].setLocation(BUTTON_WIDTH * 2, BOTTOM);
        buttons[1].setLocation(BUTTON_WIDTH * 2, BOTTOM - BUTTON_WIDTH);
        buttons[4].setLocation(BUTTON_WIDTH * 2, BOTTOM - BUTTON_WIDTH * 2);
        buttons[7].setLocation(BUTTON_WIDTH * 2, BOTTOM - BUTTON_WIDTH * 3);

        buttons[12].setLocation(BUTTON_WIDTH * 3, BOTTOM);
        buttons[13].setLocation(BUTTON_WIDTH * 3, BOTTOM - BUTTON_WIDTH);
        buttons[14].setLocation(BUTTON_WIDTH * 3, BOTTOM - BUTTON_WIDTH * 2);
        buttons[15].setLocation(BUTTON_WIDTH * 3, BOTTOM - BUTTON_WIDTH * 3);

        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        for (JButton button : buttons) {
            button.addActionListener(this);
        }
        canvas.setVisible(true);

    }

    private void __proc__(String value) {
        if (Character.isDigit(value.charAt(0))) {
            handleNumbers(value);
        } else {
            handleSymbol(value);
        }
        setDisplay();
    }


    private void setDisplay() {
        display.setText(intermittent);
    }

    private void handleNumbers(String number) {
        if (!intermittent.equals("0")) {
            intermittent += number;
        } else {
            if (!number.equals("0")) {
                intermittent = number;
            }
        }
    }

    private void handleSymbol(String symbol) {
        switch (symbol) {
            case ".":
                if (!hasDot(intermittent)) {
                    intermittent += ".";
                }
                break;
            case "=":
                if (operator != null) {
                    intermittent = intermittent.equals("") ? "0" : intermittent;
                    intermittent = String.valueOf(mathUp());
                    operator = null;
                    res = 0;
                }
                break;
            default:
                //Operator is null => res = 0
                if (!intermittent.equals("")) {
                    res = operator == null ? Float.parseFloat(intermittent) : mathUp();
                    intermittent = "";
                    operator = symbol;
                }
        }
    }

    private float mathUp() {
        if (operator.equals("+")) {
            return Float.parseFloat(intermittent) + res;
        } else if (operator.equals("-")) {
            return Float.parseFloat(intermittent) - res;
        } else if (operator.equals("×")) {
            return Float.parseFloat(intermittent) * res;
        } else {
            return Float.parseFloat(intermittent) / res;
        }
    }

    private boolean hasDot(String buffer) {
        for (char c : buffer.toCharArray()) {
            if (c == '.') {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComponent event = (JComponent) actionEvent.getSource();
        if (event instanceof JButton) {
            __proc__(((JButton) event).getText());
        }
    }
}