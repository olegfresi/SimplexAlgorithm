package Entry;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import IO.*;
import Parser.Visitor.*;
import Parser.Generated.*;
import Model.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.IOException;
import java.util.List;


public class Window extends javax.swing.JFrame {

    private JButton loadButton;
    private JButton solveButton;
    private JLabel fileLabel;
    private File selectedFile;

    public Window() {
        initComponents();
    }

    private void initComponents() {

        loadButton = new JButton("Load TXT");
        solveButton = new JButton("Solve");
        fileLabel = new JLabel("No file selected");

        Dimension buttonSize = new Dimension(200, 45);

        loadButton.setPreferredSize(buttonSize);
        solveButton.setPreferredSize(buttonSize);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Linear Programming Solver");
        setSize(800, 600);
        setLocationRelativeTo(null);

        loadButton.addActionListener(e -> openFileDialog());

        solveButton.addActionListener(e -> {

            if (selectedFile == null) {

                JOptionPane.showMessageDialog(this,
                        "Please load a TXT file first.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            try {

                CharStream input = CharStreams.fromPath(selectedFile.toPath());

                ConstraintsGrammarLexer lexer = new ConstraintsGrammarLexer(input);
                lexer.removeErrorListeners();
                lexer.addErrorListener(new ErrorListener());

                CommonTokenStream tokens = new CommonTokenStream(lexer);

                ConstraintsGrammarParser parser = new ConstraintsGrammarParser(tokens);
                parser.removeErrorListeners();
                parser.addErrorListener(new ErrorListener());

                ParseTree tree = parser.file();

                ConstraintLoaderVisitor loader = new ConstraintLoaderVisitor();
                loader.visit(tree);

                List<LinearConstraint> constraintList = loader.getConstraints();

                for (LinearConstraint c : constraintList) {
                    System.out.println(c);
                }

                JOptionPane.showMessageDialog(this, "Constraints loaded successfully!");

            }
            catch (RuntimeException ex) {
                if (ex.getCause() instanceof ParseException) {
                    ParseException parseException = (ParseException) ex.getCause();
                    System.err.println("CRITICAL PARSING FAILURE -> " + parseException.getMessage());
                    System.err.println("Problem identified on file row index: " + parseException.getLine());
                } else
                    ex.printStackTrace();
            }catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        fileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        solveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(fileLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(loadButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(solveButton);
        panel.add(Box.createVerticalGlue());

        add(panel);
    }

    private void openFileDialog() {

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();

            try {

                InputValidator.validate(file);

                selectedFile = file;

                fileLabel.setText("Selected: " + file.getName());

                JOptionPane.showMessageDialog(this, "File loaded correctly!");

            } catch (InputException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}