package Entry;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import IO.*;
import Parser.Visitor.*;
import Parser.Generated.*;
import Model.*;
import Model.SimplexResult;
import Solver.SimplexSolver;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Window extends javax.swing.JFrame {

    private JLabel fileLabel;
    private File selectedFile;

    public Window() {
        initComponents();
    }

    private void initComponents() {

        JButton loadButton = new JButton("Load File");
        JButton solveButton = new JButton("Solve");
        fileLabel = new JLabel("No file selected");

        Logger logger = Logger.getLogger(Window.class.getName());

        Dimension buttonSize = new Dimension(200, 45);

        loadButton.setPreferredSize(buttonSize);
        solveButton.setPreferredSize(buttonSize);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Linear Programming Solver");
        setSize(800, 600);
        setLocationRelativeTo(null);

        loadButton.addActionListener(_ -> openFileDialog());

        solveButton.addActionListener(_ -> {

            if (selectedFile == null) {

                JOptionPane.showMessageDialog(this,
                        "Please load a TXT file first.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE,
                        null);

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

                String message = getString(loader);

                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Simplex Solver Results",
                        JOptionPane.INFORMATION_MESSAGE,
                        null
                );
            }
            catch (RuntimeException ex) {
                if (ex.getCause() instanceof ParseException parseException) {
                    logger.severe("CRITICAL PARSING FAILURE -> " + parseException.getMessage());
                    logger.severe("Problem identified on file row index:: " + parseException.getLine());
                } else
                    logger.log(Level.SEVERE, "Error during execution", ex);
            }catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "Error during execution", ex);
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

    private static String getString(ConstraintLoaderVisitor loader) {
        List<LinearConstraint> constraintList = loader.getConstraints();

        SimplexSolver solver = new SimplexSolver(constraintList, OrderingStrategy.STANDARD);
        SimplexResult result = solver.solve();

        String status = result.isSatisfiable()
                ? "SATISFIABLE (SAT) -> A valid solution exists."
                : "UNSATISFIABLE (UNSAT) -> Constraints are contradictory.";

        StringBuilder sb = new StringBuilder();
        sb.append("================ SIMPLEX SOLVER RESULTS ================\n\n");
        sb.append("Result: ").append(status).append("\n\n");
        sb.append("Total Pivot Operations Performed: ").append(result.getPivotCount()).append("\n\n");
        sb.append("Initial Variable Ordering: ").append(result.getUsedOrdering()).append("\n\n");

        sb.append("Variable Assignments:\n");
        if (result.isSatisfiable() && result.getModel() != null)
            sb.append(result.modelToString());
        else
            sb.append("  No variable assignments available (Problem Unsatisfiable).\n");

        sb.append("\n========================================================");

        return sb.toString();
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
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE, null);
            }
        }
    }
}