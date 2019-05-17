/*
Copyright (c) 2019,
Anton Bothin,
Erik Flink,
Nelly Friman,
Jacob Klasmark,
Valter Lundegårdh,
Isak Olsson,
Andreas Sjödin,
Carina Wickström.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The names of the authors may not be used to endorse or promote
   products derived from this software without specific prior
   written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHORS ``AS IS'' AND ANY EXPRESS
OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

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
