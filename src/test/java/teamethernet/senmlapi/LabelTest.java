package teamethernet.senmlapi;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LabelTest {

    @Test
    public void nameToValueMap_containsAllFields() {
        final List<Field> labelFields = Arrays.stream(Label.class.getDeclaredFields()).filter(
                field -> field.getType().equals(Label.class)).collect(Collectors.toList());

        assertEquals("You have probably added a new teamethernet.senmlapi.Label without updating the NAME_TO_VALUE_MAP with this value",
                labelFields.size(), Label.getNameToValueMap(JsonFormatter.class).size());
    }

}