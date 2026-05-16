package IO;

import java.io.File;

public class InputValidator {


    public static void validate(File file) throws InputException {

        if (file == null)
            throw new InputException("No file selected.");

        if (!file.getName().toLowerCase().endsWith(".txt"))
            throw new InputException("Invalid file extension. Only .txt files are allowed.");

        if (file.length() == 0)
            throw new InputException("The selected file is empty.");
    }
}