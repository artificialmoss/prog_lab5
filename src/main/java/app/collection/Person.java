package app.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Person implements Comparable<Person> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(){}

    public Person(Long id, String name, Coordinates coordinates, Long height, LocalDateTime birthday,
        Color hairColor, Country nationality, Location location)
    {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        creationDate = ZonedDateTime.now();
    }

    public boolean check() {
        return (id != null && id > 0) && (name != null && !name.trim().isEmpty()) && (coordinates!= null && coordinates.check()) &&
                creationDate != null && (height != null && height > 0) && hairColor != null && (location != null && location.check());
    }

    public Long getId() { return id; }

    public String getName() { return this.name; }

    public Coordinates getCoordinates() { return this.coordinates; }

    public ZonedDateTime getCreationDate() { return this.creationDate; }

    public Long getHeight() { return this.height; }

    public LocalDateTime getBirthday() { return birthday; }

    @JsonIgnore
    public LocalDate getBirthdayDate() {
        if (birthday == null) return null;
        return birthday.toLocalDate();
    }

    public Color getHairColor() { return this.hairColor; }

    public Country getNationality() { return this.nationality; }

    public Location getLocation() { return this.location; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Person p = (Person) o;
        return (this.id.equals(p.getId()) && this.name.equals(p.getName()) && this.coordinates.equals(p.getCoordinates())
                && this.creationDate.equals(p.getCreationDate()) && this.height.equals(p.getHeight()) && this.birthday.equals(p.getBirthday())
                && this.hairColor.equals(p.getHairColor()) && this.nationality.equals(p.getNationality()) &&
                this.location.equals(p.getLocation()));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Person p) {
        return this.height.compareTo(p.getHeight());
    }

    @Override
    public String toString() {
        return "Person:\n\tid: " + id.toString() + "\n\tname: " + name + "\n\tcoordinates: " + coordinates.toString()
                + "\n\tcreation date: " + creationDate.toString() + "\n\theight: " + height.toString() + "\n\tbirthday: "
                + ((birthday == null) ? "—" : birthday.toString()) + "\n\thair color: " + hairColor.toString()
                + "\n\tnationality: " + ((nationality == null) ? "—": nationality.toString()) + "\n\tlocation: " + location.toString();
    }
}
