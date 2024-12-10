package a01183994.database.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

/**
 * Currently, it includes functionality to validate email addresses.
 * 
 * This class is designed to be extended in the future for additional validation needs.
 * 
 * @author Samson James Ordonez
 * @version 1.0
 */
public class Validator {

	private static final Pattern BCIT_ID_PATTERN = Pattern.compile("^A0\\d{7}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]+(?: [A-Za-z]+)*$");
	
    /**
     * Validates that the given string is not null, empty, or only whitespace.
     * If the string is valid, it returns the trimmed string. Otherwise, it throws an
     * ApplicationException with an appropriate error message.
     *
     * @param input The string to validate
     * @return The trimmed string if valid
     * @throws ApplicationException If the string is null, empty, or only whitespace
     */
    public static String validateString(String input) throws ApplicationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ApplicationException("Input cannot be empty or only whitespace");
        }
        return input.trim();
    }
    
    /**
     * Validates that the given string matches the BCIT ID format.
     * Valid format: Start with 'A' followed by a zero and 7 digits.
     *
     * @param id The ID string to validate
     * @return The validated ID if it matches the format
     * @throws ApplicationException If the ID does not match the required format
     */
    public static String validateId(String id) throws ApplicationException {
        String trimmedId = validateString(id);
        if (!BCIT_ID_PATTERN.matcher(trimmedId).matches()) {
            throw new ApplicationException("Invalid BCIT ID format. Must start with 'A0' followed by 7 digits.");
        }
        return trimmedId;
    }
    
    public static boolean isAgeValid(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        Period age = Period.between(dob, today);
        return age.getYears() >= 18;
    }

    /**
     * Validates that the name starts with a letter, allows spaces (one per word),
     * and contains only letters.
     *
     * @param name The input name to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Trim the input and validate against the pattern
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Formats a name to capitalize the first letter of each word and make the rest lowercase.
     *
     * @param name The input name to format.
     * @return The formatted name.
     */
    public static String formatName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        String[] words = name.trim().split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1).toLowerCase())
                         .append(" ");
        }
        return formattedName.toString().trim();
    }
   
}