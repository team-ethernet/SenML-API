package teamethernet.senmlapi;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

public class JsonFormatter implements Formatter {

    private static final ObjectMapper MAPPER = new ObjectMapper(new JsonFactory());

    private final JsonNode RECORDS;

    JsonFormatter() {
        RECORDS = MAPPER.createArrayNode();
    }

    JsonFormatter(final byte[] jsonData) throws IOException {
        RECORDS = MAPPER.readTree(jsonData);
    }

    public ObjectMapper getMapper() {
        return MAPPER;
    }

    public JsonNode getRecords() {
        return RECORDS;
    }

    public String getStringValue(Label<String> label, JsonNode record) {
        return record.get(label.getFormattedLabel(this.getClass())).asText();
    }

    public Integer getIntegerValue(Label<Integer> label, JsonNode record) {
        return record.get(label.getFormattedLabel(this.getClass())).intValue();
    }

    public Double getDoubleValue(Label<Double> label, JsonNode record) {
        return record.get(label.getFormattedLabel(this.getClass())).doubleValue();
    }

    public Boolean getBooleanValue(Label<Boolean> label, JsonNode record) {
        return record.get(label.getFormattedLabel(this.getClass())).booleanValue();
    }

    public void addRecord(final byte[] cborData) throws IOException {
        JsonNode record = MAPPER.readTree(cborData);
        ((ArrayNode) RECORDS).add(record);
    }

    public byte[] getSenML(final JsonNode rootNode) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(rootNode);
    }

}
