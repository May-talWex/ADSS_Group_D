package HR.Domain;

import HR.Domain.EmployeeTypes.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class EmployeeDeserializer extends JsonDeserializer<Employee> {

    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(jsonParser, Employee.class);

        // Manually set possible positions
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode possiblePositionsNode = node.get("possiblePositions");
        if (possiblePositionsNode != null && possiblePositionsNode.isArray()) {
            for (JsonNode positionNode : possiblePositionsNode) {
                String position = positionNode.asText();
                switch (position) {
                    case "HRManager":
                        employee.addPossiblePosition(new HRManager());
                        break;
                    case "Cashier":
                        employee.addPossiblePosition(new Cashier());
                        break;
                    case "DeliveryPerson":
                        employee.addPossiblePosition(new DeliveryPerson());
                        break;
                    case "ShiftManager":

                        employee.addPossiblePosition(new ShiftManager());
                        break;
                    case "StorageEmployee":
                        employee.addPossiblePosition(new StorageEmployee());
                        break;

                    // Add other cases as needed
                }
            }
        }

        return employee;
    }
}
