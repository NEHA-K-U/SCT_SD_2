import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame {
    private final int secretNumber;
    private int attempts = 0;

    private final JTextField guessField;
    private final JButton guessButton;
    private final JButton restartButton;
    private final JLabel messageLabel;
    private final JLabel attemptsLabel;

    public GuessTheNumberGUI() {
        super("Guess the Number");
        secretNumber = new Random().nextInt(100) + 1; // random number from 1 to 100

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel("Guess the Number (1 - 100)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Center panel: message and attempts
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        messageLabel = new JLabel("I've picked a number. Make your first guess!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        attemptsLabel = new JLabel("Attempts: 0", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        centerPanel.add(messageLabel);
        centerPanel.add(attemptsLabel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel: input and buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        guessField = new JTextField(8);
        guessField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        guessButton = new JButton("Guess");
        restartButton = new JButton("New Game");
        restartButton.setEnabled(false);

        bottomPanel.add(new JLabel("Your guess:"));
        bottomPanel.add(guessField);
        bottomPanel.add(guessButton);
        bottomPanel.add(restartButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Pressing Enter in the text field also submits the guess
        ActionListener guessAction = e -> handleGuess();
        guessButton.addActionListener(guessAction);
        guessField.addActionListener(guessAction);

        restartButton.addActionListener(e -> restartGame());

        pack();
        setMinimumSize(new Dimension(420, 240));
        setLocationRelativeTo(null); // center on screen
        setVisible(true);
    }

    private void handleGuess() {
        String input = guessField.getText().trim();
        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid whole number.");
            return;
        }

        if (guess < 1 || guess > 100) {
            messageLabel.setText("Your guess must be between 1 and 100.");
            return;
        }

        attempts++;
        attemptsLabel.setText("Attempts: " + attempts);

        if (guess < secretNumber) {
            messageLabel.setText("Too low! Try a higher number.");
        } else if (guess > secretNumber) {
            messageLabel.setText("Too high! Try a lower number.");
        } else {
            messageLabel.setText("Correct! The number was " + secretNumber + ". You got it in " + attempts + " attempt(s)!");
            guessField.setEnabled(false);
            guessButton.setEnabled(false);
            restartButton.setEnabled(true);
        }

        guessField.setText("");
        guessField.requestFocusInWindow();
    }

    private void restartGame() {
        // Start a fresh round with a new secret number
        new GuessTheNumberGUI();
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessTheNumberGUI::new);
    }
}