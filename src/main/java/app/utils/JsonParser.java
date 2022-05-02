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

public class JsonParser implements GetFile {
    private final ObjectMapper mapper = new ObjectMapper();

    public CollectionManager jsonStringToCollection(String json) {
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Vector<Person> collection = mapper.readValue(json, new TypeReference<Vector<Person>>() {});
            return new CollectionManager().initializeCollection(collection);
        } catch (IOException e) {
            throw new CannotInitializeCollectionException();
        }
    }

    public String collectionToJsonString(Vector<Person> collection) throws JsonProcessingException {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(collection);
    }

    public String fileToString(String filename) {
        try {
            if (!getReadableFile(filename)) throw new CannotInitializeCollectionException();
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

    public CollectionManager fileToCollection(String filename) {
        try {
            return jsonStringToCollection(fileToString(filename));
        } catch (CannotInitializeCollectionException e) {
            System.out.println("Cannot initialize collection stored in " + filename +". The collection will be empty.");
            return new CollectionManager();
        }
    }

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
