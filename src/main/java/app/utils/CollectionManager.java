package app.utils;

import app.data.Person;
import app.exceptions.FullCollectionException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Class that manages the collection
 */
public class CollectionManager {
    private Vector<Person> collection;
    private final ZonedDateTime initializationDate;
    private Long nextId = 1L;

    /**
     * Constructor, creates an empty collection and sets initialization date
     */
    public CollectionManager() {
        collection = new Vector<>();
        initializationDate = ZonedDateTime.now();
    }

    /**
     * Gets the element with the specified id
     * @param id Long id
     * @return Person The element with the specified id or null if it doesn't exist
     */
    public Person getById(Long id) {
        for (Person p : collection) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Generates an available id
     * @return Long the next available id
     * @throws FullCollectionException Thrown when a new id can't be generated
     */
    public Long generateNextId() throws FullCollectionException {
        nextId = (getMaxId().equals(Long.MAX_VALUE)) ? 1L : getMaxId() + 1;
        Long start = nextId;
        while (getById(nextId) != null) {
            nextId = (nextId.equals(Long.MAX_VALUE)) ? 1L : nextId + 1;
            if (nextId.equals(start)) {
                throw new FullCollectionException("The collection is full. New id cannot be generated.");
            }
        }
        return nextId;
    }

    private Long getMaxId() {
        Long id = 0L;
        for (Person p: collection) {
            if (p.getId() > id) {
                id = p.getId();
            }
        }
        return id;
    }

    /**
     * Adds a new element
     * @param p Person Element to add
     * @return String Result
     */
    public String add(Person p) {
        Long id = p.getId();
        if (getById(id) == null) {
            collection.add(p);
            return "The element has been added to the collection.";
        } else {
            return "Cannot add this element: a person with this id already exists.";
        }
    }

    /**
     * Removes the element with the specified id from the collection
     * @param id Long id
     * @return String Result
     */
    public String removeById(Long id) {
        Iterator<Person> i = collection.iterator();
        while (i.hasNext()) {
            Person p = i.next();
            if (p.getId().equals(id)) {
                i.remove();
                return "The element with this id has been removed.";
            }
        }
        return "The element with this id doesn't exist.";
    }

    /**
     * Clears the collection
     * @return String Result
     */
    public String clear() {
        if (getSize() == 0) return "The collection is already empty.";
        collection.clear();
        return "The collection has been cleared.";
    }

    /**
     * Shows the contents of the collection
     * @return String Result
     */
    public String show() {
        int length = collection.size();
        if (length == 0) {
            return "The collection is empty.";
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++) {
            res.append(collection.elementAt(i).toString());
            res.append("\n");
        }
        return res.toString();
    }

    /**
     * Replaces the element with the specified id with another one
     * @param id Long id
     * @param p Person The replacement
     */
    public void updateById(Long id, Person p) {
        int length = collection.size();
        for (int i = 0; i < length; i++) {
            if(collection.get(i).getId().equals(id)) {
                collection.setElementAt(p, i);
                break;
            }
        }
    }

    /**
     * Returns the current size of the collection
     * @return int Number of elements
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * Returns the type of the collection
     * @return String Type
     */
    public String getType() {
        return collection.getClass().getSimpleName();
    }

    /**
     * Returns the date the collection has been initialized in String format
     * @return String date
     */
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return initializationDate.format(formatter);
    }

    /**
     * Returns the element type
     * @return String the element type
     */
    public String getElementType() {
        return Person.class.getSimpleName();
    }

    /**
     * Returns the maximal element of the collection according to the elements' natural order
     * @return Person The maximal element
     */
    public Person getMax() {
        int length = collection.size();
        if (length == 0){
            return null;
        }
        Person max = collection.get(0);
        for (Person currentP : collection) {
            if (currentP.compareTo(max) > 0) {
                max = currentP;
            }
        }
        return max;
    }

    /**
     * Returns the minimal element of the collection according to the elements' natural order
     * @return Person The minimal element
     */
    public Person getMin() {
        int length = collection.size();
        if (length == 0){
            return null;
        }
        Person min = collection.get(0);
        for (Person currentP : collection) {
            if (currentP.compareTo(min) > 0) {
                min = currentP;
            }
        }
        return min;
    }

    /**
     * Groups the elements by their height and showing the size of each group
     * @return String Result
     */
    public String groupByHeight() {
        HashMap<Long, HashSet<Person>> groups = new HashMap<>();
        for (Person p : collection) {
            if (!groups.containsKey(p.getHeight())) {
                groups.put(p.getHeight(), new HashSet<>());
            }
            groups.get(p.getHeight()).add(p);
        }
        Set<Long> keys = groups.keySet();
        StringBuilder groupSizes = new StringBuilder();
        for (Long i : keys)
            groupSizes.append("Height: ").append(i).append(", number of people with this height: ").append(groups.get(i).size()).append("\n");
        if (keys.size() == 0) {
            return "Cannot group by height, the collection is empty.";
        }
        return groupSizes.toString();
    }

    /**
     * Returns a list of birthdays of all elements stored in the collection
     * @return String Result
     */
    public String descendingBirthdays() {
        TreeSet<LocalDate> birthdays = new TreeSet<>();
        for (Person p : collection) {
            if (p.getBirthdayDate() != null) birthdays.add(p.getBirthdayDate());
        }
        if (birthdays.size() == 0) {
            return "The collection is empty.";
        }
        return birthdays.descendingSet().toString();
    }

    /**
     * Counts all elements with the specified birthday
     * @param birthday LocalDate Birthday
     * @return String Result
     */
    public String countBirthday(LocalDate birthday) {
        int count = 0;
        for (Person p : collection) {
            if (p.getBirthdayDate() != null && p.getBirthdayDate().equals(birthday)) {
                count++;
            }
        }
        return count + " people in the collection were born on " + birthday.toString() + ".";
    }

    /**
     * Shuffles the collection
     */
    public void shuffle() {
        Collections.shuffle(collection);
    }

    /**
     * Removes elements of the collection that don't meet the requirements
     */
    public void checkAndRemove() {
        Set<Long> ids = new HashSet<>();
        Iterator<Person> i = collection.iterator();
        while (i.hasNext()) {
            Person p = i.next();
            if (!p.check() || ids.contains(p.getId())) {
                i.remove();
            } else {
                ids.add(p.getId());
            }
        }
    }

    /**
     * Method for initializing a collection
     * @param collection Vector&lt;Person&gt; Collection
     * @return CollectionManager The resulting CollectionManager
     */
    public CollectionManager initializeCollection(Vector<Person> collection) {
        if (collection != null) {
            this.collection = collection;
            checkAndRemove();
        }
        return this;
    }

    /**
     * Method for saving the collection to a file in json format
     * @param parser JsonParser Parser
     * @param filepath Filepath Filepath
     * @return String Result
     */
    public String save(JsonParser parser, String filepath) {
        String defaultFilepath = "saved_collection_default.json";
        return parser.writeCollectionToFile(filepath, defaultFilepath, collection);
    }
}
