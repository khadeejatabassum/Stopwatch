import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JFrame {
    private Timer timer;
    private long startTime;
    private boolean running;

    private JLabel timeLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;

    public Stopwatch() {
        setTitle("Stopwatch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(timeLabel);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        add(stopButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        add(resetButton);

        pack();
        setLocationRelativeTo(null);
    }

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    updateDisplay(elapsedTime);
                }
            });
            timer.start();

            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    public void stop() {
        if (running) {
            timer.stop();
            running = false;

            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    public void reset() {
        timer.stop();
        running = false;

        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        updateDisplay(0);
    }

    private void updateDisplay(long elapsedTime) {
        long hours = elapsedTime / (1000 * 60 * 60);
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long seconds = (elapsedTime / 1000) % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(timeString);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.setVisible(true);
            }
        });
    }
}
