package teamethernet.senmlapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CborFormatter implements Formatter {

    private static final ObjectMapper MAPPER = new ObjectMapper(new CBORFactory());

    private final JsonNode RECORDS;

    CborFormatter() {
        RECORDS = MAPPER.createArrayNode();
    }

    CborFormatter(final byte[] cborData) throws IOException {
        RECORDS = MAPPER.readValue(cborData, JsonNode.class);
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
        final JsonNode record = MAPPER.readValue(cborData, JsonNode.class);
        ((ArrayNode) RECORDS).add(record);
    }

    public byte[] getSenML(final JsonNode rootNode) throws IOException {
        return MAPPER.writeValueAsBytes(rootNode);
    }

}
