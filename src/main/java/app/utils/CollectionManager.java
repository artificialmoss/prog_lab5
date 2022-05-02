package app.utils;

import app.collection.Person;
import app.exceptions.FullCollectionException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager implements ReadPersonFromScript {
    private Vector<Person> collection;
    private final ZonedDateTime initializationDate;
    private Long nextId = 1L;
    private final PersonReader personReader = new PersonReader(3);

    public CollectionManager() {
        collection = new Vector<>();
        initializationDate = ZonedDateTime.now();
    }

    public Person getById(Long id) {
        for (Person p : collection) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void generateNextId() {
        Long start = nextId;
        while (getById(nextId) != null) {
            nextId = (nextId.equals(Long.MAX_VALUE)) ? 1L : nextId + 1;
            if (nextId.equals(start)) {
                throw new FullCollectionException("The collection is full. New id cannot be generated.");
            }
        }
    }

    public String add(Person p) {
        Long id = p.getId();
        if (getById(id) == null) {
            collection.add(p);
            return "The element has been added to the collection.";
        } else {
            return "Cannot add this element: a person with this id already exists.";
        }
    }

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

    public String clear() {
        if (getSize() == 0) return "The collection is already empty.";
        collection.clear();
        return "The collection has been cleared.";
    }

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

    public void updateById(Long id, Person p) {
        int length = collection.size();
        for (int i = 0; i < length; i++) {
            if(collection.get(i).getId().equals(id)) {
                collection.setElementAt(p, i);
                break;
            }
        }
    }

    public int getSize() {
        return collection.size();
    }

    public String getType() {
        return "Vector";
    }

    public String getDate() {
        return initializationDate.toString();
    }

    public String getElementType() {
        return "Person";
    }

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

    public String countBirthday(LocalDate birthday) {
        int count = 0;
        for (Person p : collection) {
            if (p.getBirthdayDate() != null && p.getBirthdayDate().equals(birthday)) {
                count++;
            }
        }
        return count + " people in the collection were born on " + birthday.toString() + ".";
    }

    public void shuffle() {
        Collections.shuffle(collection);
    }

    public Person readPerson() {
        generateNextId();
        return personReader.readPerson(nextId);
    }

    public Person readPerson(Long id) {
        return personReader.readPerson(id);
    }

    public Person readPersonFromScript(Scanner s) {
        generateNextId();
        return readPersonFromScript(s, nextId);
    }

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

    public CollectionManager initializeCollection(Vector<Person> collection) {
        if (collection != null) {
            this.collection = collection;
            checkAndRemove();
        }
        return this;
    }

    public String save(JsonParser parser, String filepath) {
        String defaultFilepath = "saved_collection_default.json";
        return parser.writeCollectionToFile(filepath, defaultFilepath, collection);
    }
}
