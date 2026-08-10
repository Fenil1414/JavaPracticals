class PasswordChecker {
    public static boolean hasMinimumLength(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean hasUppercase(String password) {
        if (password == null) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDigit(String password) {
        if (password == null) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpecialCharacter(String password) {
        if (password == null) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                return true;
            }
        }
        return false;
    }

    public static String strength(String password) {
        int score = 0;
        if (hasMinimumLength(password)) {
            score++;
        }
        if (hasUppercase(password)) {
            score++;
        }
        if (hasDigit(password)) {
            score++;
        }
        if (hasSpecialCharacter(password)) {
            score++;
        }

        if (score >= 4) {
            return "Strong";
        } else if (score >= 2) {
            return "Medium";
        } else {
            return "Weak";
        }
    }
}

public class Driver {
public static void main(String[] args) {
String[] passwords = { "abc",
"abcdefghi", "Abcdefgh", "Abcd1234", "Abcd1234!"
};
for (String pw : passwords) {
System.out.println("Password: " + pw);
System.out.println("Length >= 8: "
+ PasswordChecker.hasMinimumLength(pw));
System.out.println("Uppercase letter: "
+ PasswordChecker.hasUppercase(pw));
System.out.println("Digit: "
+ PasswordChecker.hasDigit(pw));
System.out.println("Special character: "
+ PasswordChecker.hasSpecialCharacter(pw));
System.out.println("Strength: "
+ PasswordChecker.strength(pw));
System.out.println("	");
}
}
}
