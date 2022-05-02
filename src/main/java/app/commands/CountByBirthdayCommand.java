package app.commands;

import app.exceptions.NonexistentDateException;
import app.exceptions.WrongAmountOfArgumentsException;
import app.exceptions.WrongArgumentException;
import app.utils.CollectionManager;

import java.time.LocalDate;
import java.time.Month;

/**
 * Command for counting the elements of the collection with the specified birthday
 */
public class CountByBirthdayCommand extends Command {
    private final CollectionManager collectionManager;
    private LocalDate birthday;

    public CountByBirthdayCommand(CollectionManager collectionManager) {
        super("count_by_birthday birthday", "count elements with the specified birthday");
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(boolean scriptMode) {
        return collectionManager.countBirthday(birthday);
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length == 4) {
            try {
                int day = Integer.parseInt(input[1]);
                int month = Integer.parseInt(input[2]);
                int year = Integer.parseInt(input[3]);
                if ((year < LocalDate.MIN.getYear() || year > LocalDate.now().getYear()) ||
                        (month < 1 || month > 12)) {
                    throw new NonexistentDateException("This date doesn't exist.");
                }
                int maxDay = Month.of(month).length(LocalDate.of(year, month, 1).isLeapYear());
                if (day < 1 || day > maxDay) {
                    throw new NonexistentDateException("This date doesn't exist.");
                }
                birthday = LocalDate.of(year, month, day);
            } catch (NumberFormatException | NonexistentDateException e) {
                throw new WrongArgumentException(e);
            }
        } else {
            throw new WrongAmountOfArgumentsException();
        }
        return this;
    }
}
