package app.utils;

import app.collection.*;
import app.exceptions.NullElementException;
import app.exceptions.WrongArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

public class PersonReader {
    private final int MAX_ERROR_COUNT;
    private final Scanner s = new Scanner(System.in);

    public PersonReader(int maxErrorCount) {
        this.MAX_ERROR_COUNT = maxErrorCount;
    }

    public String readString() throws WrongArgumentException {
        int errorCount = 0;
        String res;
        while (errorCount != MAX_ERROR_COUNT) {
            res = s.nextLine().trim();
            if (!res.isEmpty()) return res;
            errorCount++;
            if (errorCount != MAX_ERROR_COUNT) {
                System.out.print("This argument cannot be empty, try again:");
            }
        }
        throw new WrongArgumentException("This argument cannot be empty.");
    }

    public String readName() throws WrongArgumentException {
        System.out.print("Type their name (cannot be empty): ");
        return readString();
    }

    public Coordinates readCoordinates() throws WrongArgumentException {
        int errorCount = 0;
        double x = 213.0;
        Float y = null;

        System.out.print("Type first coordinate (double, max value = 212.0): ");
        while (errorCount != MAX_ERROR_COUNT) {
            try {
                x = Double.parseDouble(s.nextLine());
                if (x > 212) {
                    errorCount++;
                    System.out.print("Invalid argument, try again (double, max value = 212.0): ");
                } else break;
            } catch (InputMismatchException | NumberFormatException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument, try again (double, max value = 212.0): ");
            }
        }
        if (x > 212) {
            throw new WrongArgumentException("Invalid argument.");
        }

        errorCount = 0;
        System.out.print("Type second coordinate (float): ");
        while (errorCount != MAX_ERROR_COUNT) {
            try {
                y = Float.parseFloat(s.nextLine());
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument, try again (float): ");
            }
        }
        if (y == null) {
            throw new WrongArgumentException("Invalid argument.");
        }

        return new Coordinates(x, y);
    }

    public Long readLong(LongPredicate p, String requirements) throws WrongArgumentException {
        long res;
        int errorCount = 0;
        while (errorCount < MAX_ERROR_COUNT) {
            try {
                res = Long.parseLong(s.nextLine());
                if (p.test(res)) {
                    return res;
                }
                else {
                    errorCount++;
                    if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (" + requirements + "), try again: ");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (" + requirements + "), try again: ");
            }
        }
        throw new WrongArgumentException("Invalid argument.");
    }

    public Long readHeight() throws WrongArgumentException {
        String requirements = "long, must be positive";
        System.out.print("Type height (long, must be positive): ");
        return readLong(x-> x > 0, requirements);
    }

    public int readInt(IntPredicate p, String requirements, boolean nullAllowed) throws WrongArgumentException {
        int errorCount = 0;
        int res;
        while (errorCount < MAX_ERROR_COUNT) {
            try {
                String line = s.nextLine().trim();
                if (nullAllowed && line.isEmpty()) {
                    throw new NullElementException();
                }
                res = Integer.parseInt(line);
                if (p.test(res)) {
                    return res;
                } else {
                    errorCount++;
                    if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (" + requirements + "). Try again: ");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (" + requirements + "). Try again: ");
            }
        }
        throw new WrongArgumentException("This argument cannot be empty");
    }

    public LocalDateTime readBirthday() throws WrongArgumentException {
        LocalDateTime birthday;
        String yearRequirements = "must be an integer greater than " + LocalDateTime.MIN.getYear() + " and less than " + LocalDateTime.MAX.getYear()
                + ", input an empty line if you don't want to specify this element's birthday";
        System.out.print("Type the year of their birth (" + yearRequirements + "): ");
        try {
            int year = readInt(x -> (x >= LocalDateTime.MIN.getYear()) && (x <= LocalDateTime.MAX.getYear()), yearRequirements, true);
            String monthRequirements = "must be an integer between 1 and 12"
                    + ", input an empty line if you don't want to specify this element's birthday";
            System.out.print("Type the month of their birth (" + monthRequirements + "): ");
            Month month = Month.of(readInt(x -> (x >= 1) && (x <= 12), monthRequirements, true));
            final int maxDay = month.length(LocalDate.of(year, month, 1).isLeapYear());
            String dayRequirements = "must be an integer between 1 and " + maxDay
                    + ", input an empty line if you don't want to specify this element's birthday";
            System.out.print("Type the day of their birth (" + dayRequirements + "): ");
            int day = readInt(x -> (x>= 1) && (x <= maxDay), dayRequirements, true);
            String hourRequirements = "must be an integer between 0 and 23, if unsure, type 0"
                    + ", input an empty line if you don't want to specify this element's birthday";
            System.out.print("Type the hour of their birth (" + hourRequirements + "): ");
            int hour = readInt(x -> (x >= 0) && (x <= 23), hourRequirements, true);
            String minuteRequirements = "must be an integer between 0 and 59, if unsure, type 0"
                    + ", input an empty line if you don't want to specify this element's birthday";
            System.out.print("Type the minute of their birth (" + minuteRequirements + "): ");
            int minute = readInt(x -> (x >= 0) && (x <= 59), minuteRequirements, true);
            birthday = LocalDateTime.of(year, month, day, hour, minute);
            return birthday;
        } catch (NullElementException e) {
            return null;
        }
    }

    public Color readColor() throws WrongArgumentException {
        int errorCount = 0;
        String color;
        System.out.print("Type their hair color (must be one of the following: RED, BLACK, YELLOW, WHITE, BROWN): ");
        while (errorCount < MAX_ERROR_COUNT) {
            try {
                color = s.nextLine().trim().toUpperCase();
                return Color.valueOf(color);
            } catch (IllegalArgumentException | InputMismatchException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (must be one of the following: RED, BLACK, YELLOW, WHITE, BROWN).\nTry again: ");
            }
        }
        throw new WrongArgumentException("This argument cannot be empty.");
    }

    public Country readCountry() throws WrongArgumentException {
        int errorCount = 0;
        String country;
        System.out.print("Type their nationality (must be one of the following: RUSSIA, SPAIN, VATICAN, ITALY, " +
                "input an empty line if you don't want to specify this element's nationality): ");
        while (errorCount < MAX_ERROR_COUNT) {
            try {
                country = s.nextLine().trim().toUpperCase();
                if (country.isEmpty()) return null;
                return Country.valueOf(country);
            } catch (IllegalArgumentException | InputMismatchException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (must be one of the following: RUSSIA, SPAIN, VATICAN, ITALY).\nTry again: ");
            }
        }
        throw new WrongArgumentException("This argument cannot be empty.");
    }

    public Location readLocation() throws WrongArgumentException {
        System.out.print("Location input: type first coordinate (any integer): ");
        int x = readInt(x1 -> true, "must be an integer", false);

        int errorCount = 0;
        Double y = null;
        System.out.print("Type y coordinate (any double): ");
        while (errorCount < MAX_ERROR_COUNT) {
            try {
                y = Double.parseDouble(s.nextLine());
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                errorCount++;
                if (errorCount != MAX_ERROR_COUNT) System.out.print("Invalid argument (must be a double value). Try again: ");
            }
        }
        if (y == null) {
            throw new WrongArgumentException("This argument cannot be empty");
        }

        System.out.print("Type their location (can be blank): ");
        String name = s.nextLine();
        return new Location(x, y, name);
    }

    public Person readPerson(Long id) throws WrongArgumentException {
        try {
            System.out.println("You've got " + MAX_ERROR_COUNT + " tries for each input parameter, if you fail all of them, you will need to call your command again. " +
                    "Some parameters (birthday, nationality) can be unspecified, the location name can be blank.");
            String name = readName();
            Coordinates coordinates = readCoordinates();
            Long height = readHeight();
            LocalDateTime birthday = readBirthday();
            Color hairColor = readColor();
            Country nationality = readCountry();
            Location location = readLocation();
            return new Person(id, name, coordinates, height, birthday, hairColor, nationality, location);
        } catch (NoSuchElementException e) {
            throw new WrongArgumentException();
        }
    }
}
