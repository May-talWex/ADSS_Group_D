package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    @JsonCreator
    public Employee JSONtoClass() {
        return null;
    }

    public void JSONtoClass(String pathToJson) {
        try {
            File file = new File(pathToJson);
            // Read the entire JSON file as a single String
            String jsonContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Employee.class, new EmployeeDeserializer());


            mapper.registerModule(module);

            JsonNode rootNode = mapper.readTree(jsonContent);
            List<String> jsonStrings = new ArrayList<>();

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    jsonStrings.add(node.toString());
                }
            }

            List<Employee> employees = new ArrayList<>();
            for (String jsonString : jsonStrings) {
                Employee employee = mapper.readValue(jsonString, Employee.class);
                employees.add(employee);
                System.out.println(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
