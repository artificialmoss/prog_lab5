package app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import app.collection.Person;
import app.exceptions.CannotInitializeCollectionException;
import app.exceptions.NoFileException;

import java.io.*;
import java.util.Vector;

/**
 * Class for parsing json files into collections and vice versa
 */
public class JsonParser implements GetFile {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Method for parsing a json string into a collection
     * @param json String Json string
     * @return CollectionManager Collection manager that manages the collection stored in json string
     * @throws CannotInitializeCollectionException Thrown when the collection can't be initialized
     */
    public CollectionManager jsonStringToCollection(String json) throws CannotInitializeCollectionException {
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Vector<Person> collection = mapper.readValue(json, new TypeReference<Vector<Person>>() {});
            return new CollectionManager().initializeCollection(collection);
        } catch (IOException e) {
            throw new CannotInitializeCollectionException();
        }
    }

    /**
     * Method for converting a collection into a json string
     * @param collection {@literal Vector<Person>} The collection
     * @return String The resulting string
     * @throws JsonProcessingException Thrown when the collection can't be processed into json
     */
    public String collectionToJsonString(Vector<Person> collection) throws JsonProcessingException {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(collection);
    }

    /**
     * Method for converting the contents of the file into a string
     * @param filename String The filepath
     * @return String The resulting string
     */
    public String fileToString(String filename) {
        try {
            getReadableFile(filename);
            StringBuilder fileContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line = br.readLine();
            while (line != null) {
                fileContent.append(line);
                line = br.readLine();
            }
            br.close();
            return fileContent.toString();
        } catch (NoFileException | IOException e) {
            throw new CannotInitializeCollectionException();
        }
    }

    /**
     * Method for writing a string to a file
     * @param filename The filepath
     * @param defaultName The default filepath in case the first one doesn't result in a writable file
     * @param s String The string
     * @return String The absolute path of the file the string has been written into
     * @throws NoFileException Thrown when neither the first nor the second filepath result in a writable file
     */
    public String writeStringToFile(String filename, String defaultName, String s) throws NoFileException {
        try {
            File file = getWritableFile(filename, defaultName);
            PrintWriter p = new PrintWriter(file);
            p.print(s);
            p.close();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new NoFileException();
        }
    }

    /**
     * Method for converting a json file into a collection
     * @param filename String The filepath
     * @return CollectionManager Collection manager for the resulting collection
     */
    public CollectionManager fileToCollection(String filename) {
        try {
            return jsonStringToCollection(fileToString(filename));
        } catch (CannotInitializeCollectionException e) {
            System.out.println("Cannot initialize collection" + ((filename == null)? " — enviroment variable LAB5_PATH is not set" :
                    (" stored in "+ filename)) +". The collection will be empty.");
            return new CollectionManager();
        }
    }

    /**
     * Method for saving the collection to a file in a json format
     * @param filename String The filepath
     * @param defaultName String The second filepath in case the first one doesn't result in a writable file
     * @param collection {@literal Vector<Person>} The collection
     * @return String The result
     */
    public String writeCollectionToFile(String filename, String defaultName, Vector<Person> collection) {
        try {
            String filepath = writeStringToFile(filename, defaultName, collectionToJsonString(collection));
            return "The collection has been saved to " + filepath + ".";
        } catch (JsonProcessingException e) {
            return "Unknown error: can't convert this collection to json.";
        } catch (NoFileException e) {
            return "Can't create/open/write in the destination file.";
        }
    }
}
