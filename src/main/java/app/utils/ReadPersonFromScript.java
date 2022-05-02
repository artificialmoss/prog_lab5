package app.utils;

import app.collection.*;
import app.exceptions.NullElementException;
import app.exceptions.WrongArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

/**
 * Interface for reading the element from script
 */
public interface ReadPersonFromScript {
    /**
     * Method for reading a non-empty string
     * @param s Scanner The script scanner
     * @return String The result
     * @throws WrongArgumentException Thrown when the string is empty
     */
    default String readNonEmptyStringFromScript(Scanner s) throws WrongArgumentException {
        String res = s.nextLine().trim();
        if (res.isEmpty()) {
            throw new WrongArgumentException("This argument cannot be empty.");
        }
        return res;
    }

    /**
     * Method for reading coordinates
     * @param s Scanner The script scanner
     * @return Coordinates The result
     * @throws WrongArgumentException Thrown when the coordinates from the script are invalid
     */
    default Coordinates readCoordinatesFromScript (Scanner s) throws WrongArgumentException {
        double x;
        float y;
        try {
            x = Double.parseDouble(s.nextLine().trim());
            y = Float.parseFloat(s.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new WrongArgumentException();
        }
        return new Coordinates(x, y);
    }

    /**
     * Method for reading long values
     * @param s Scanner The script scanner
     * @param p LongPredicate The predicate for more specific requirements
     * @return Long The resulting value
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default Long readLongFromScript(Scanner s, LongPredicate p) throws WrongArgumentException {
        long res = Long.parseLong(s.nextLine().trim());
        if (p.test(res)) {
            return res;
        }
        throw new WrongArgumentException();
    }

    /**
     * Mothod for reading height
     * @param s Scanner The script scanner
     * @return Long Height
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default Long readHeightFromScript(Scanner s) throws WrongArgumentException {
        return readLongFromScript(s, x-> x > 0);
    }

    /**
     * Method for reading integer values
     * @param s Scanner The script scanner
     * @param p IntPredicate The predicate for more specific requirements
     * @return int The resulting value
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default int readIntFromScript(Scanner s, IntPredicate p) throws NullElementException {
        String line = s.nextLine().trim();
        if (line.isEmpty()) throw new NullElementException();
        int res = Integer.parseInt(line);
        if (p.test(res)) {
            return res;
        }
        throw new WrongArgumentException();
    }

    /**
     * Mothod for reading the date and time of birth
     * @param s Scanner The script scanner
     * @return LocalDateTime Birthday
     * @throws WrongArgumentException Thrown when the values from the script are invalid
     */
    default LocalDateTime readBirthdayFromScript(Scanner s) throws WrongArgumentException {
        try {
            int year = readIntFromScript(s, x -> (x >= LocalDateTime.MIN.getYear()) && (x <= LocalDateTime.MAX.getYear()));
            Month month = Month.of(readIntFromScript(s, x -> (x >= 1) && (x <= 12)));
            final int maxDay = month.length(LocalDate.of(year, month, 1).isLeapYear());
            int day = readIntFromScript(s, x -> (x>= 1) && (x <= maxDay));
            int hour = readIntFromScript(s, x -> (x >= 0) && (x <= 23));
            int minute = readIntFromScript(s, x -> (x >= 0) && (x <= 59));
            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (NullElementException e) {
            return null;
        }

    }

    /**
     * Mothod for reading the hair color
     * @param s Scanner The script scanner
     * @return Color The resulting color
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default Color readColorFromScript(Scanner s) throws WrongArgumentException {
        try {
            return Color.valueOf(s.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WrongArgumentException();
        }
    }

    /**
     * Mothod for reading the nationality
     * @param s Scanner The script scanner
     * @return Country The resulting nationality
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default Country readCountryFromScript(Scanner s) throws WrongArgumentException {
        try {
            String line = s.nextLine().trim().toUpperCase();
            if (line.isEmpty()) return null;
            return Country.valueOf(line);
        } catch (IllegalArgumentException e) {
            throw new WrongArgumentException();
        }
    }

    /**
     * Mothod for reading the location
     * @param s Scanner The script scanner
     * @return Location The resulting location
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default Location readLocationFromScript(Scanner s) throws WrongArgumentException {
        try {
            int x = Integer.parseInt(s.nextLine().trim());
            Double y = Double.parseDouble(s.nextLine().trim());
            String name = s.nextLine();
            return new Location(x, y, name);
        } catch (NumberFormatException e) {
            throw new WrongArgumentException();
        }
    }

    /**
     * Mothod for reading the entire element
     * @param s Scanner The script scanner
     * @return Person The resulting element
     * @throws WrongArgumentException Thrown when the value from the script is invalid
     */
    default Person readPersonFromScript(Scanner s, Long id) throws WrongArgumentException {
        String name = readNonEmptyStringFromScript(s);
        Coordinates coordinates = readCoordinatesFromScript(s);
        Long height = readHeightFromScript(s);
        LocalDateTime birthday = readBirthdayFromScript(s);
        Color hairColor = readColorFromScript(s);
        Country nationality = readCountryFromScript(s);
        Location location = readLocationFromScript(s);
        return new Person(id, name, coordinates, height, birthday, hairColor, nationality, location);
    }

}
