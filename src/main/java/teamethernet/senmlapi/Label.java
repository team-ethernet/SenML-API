package teamethernet.senmlapi;

import java.util.HashMap;
import java.util.Map;

public abstract class Label<T> {

    private final Class<T> type;
    private final String label;
    private final String cborLabel;

    private Label(final Class<T> type, final String label, final String cborLabel) {
        this.type = type;
        this.label = label;
        this.cborLabel = cborLabel;
    }

    Class<T> getClassType() {
        return type;
    }

    public Pair attachValue(final T value) {
        return new Pair(value);
    }

    public <S extends Formatter> String getFormattedLabel(final Class<S> type) {
        if (type.isAssignableFrom(JsonFormatter.class)) {
            return label;
        } else if (type.isAssignableFrom(CborFormatter.class)) {
            return cborLabel;
        } else {
            throw new UnsupportedOperationException("This formatter is not supported yet");
        }
    }

    public final static Label<String> BASE_NAME = new Label<String>(String.class, "bn", "-2") {
    };
    public final static Label<Double> BASE_TIME = new Label<Double>(Double.class, "bt", "-3") {
    };
    public final static Label<String> BASE_UNIT = new Label<String>(String.class, "bu", "-4") {
    };
    public final static Label<Double> BASE_VALUE = new Label<Double>(Double.class, "bv", "-5") {
    };
    public final static Label<Double> BASE_SUM = new Label<Double>(Double.class, "bs", "-6") {
    };
    public final static Label<Integer> BASE_VERSION = new Label<Integer>(Integer.class, "bver", "-1") {
    };
    public final static Label<String> NAME = new Label<String>(String.class, "n", "0") {
    };
    public final static Label<String> UNIT = new Label<String>(String.class, "u", "1") {
    };
    public final static Label<Double> VALUE = new Label<Double>(Double.class, "v", "2") {
    };
    public final static Label<String> STRING_VALUE = new Label<String>(String.class, "vs", "3") {
    };
    public final static Label<Boolean> BOOLEAN_VALUE = new Label<Boolean>(Boolean.class, "vb", "4") {
    };
    public final static Label<String> DATA_VALUE = new Label<String>(String.class, "vd", "8") {
    };
    public final static Label<Double> SUM = new Label<Double>(Double.class, "s", "5") {
    };
    public final static Label<Double> TIME = new Label<Double>(Double.class, "t", "6") {
    };
    public final static Label<Double> UPDATE_TIME = new Label<Double>(Double.class, "ut", "7") {
    };

    public final static <S extends Formatter>  Map<String, Label> getNameToValueMap(final Class<S> type) {
        return new HashMap<String, Label>() {{
            put(BASE_NAME.getFormattedLabel(type), BASE_NAME);
            put(BASE_TIME.getFormattedLabel(type), BASE_TIME);
            put(BASE_UNIT.getFormattedLabel(type), BASE_UNIT);
            put(BASE_VALUE.getFormattedLabel(type), BASE_VALUE);
            put(BASE_SUM.getFormattedLabel(type), BASE_SUM);
            put(BASE_VERSION.getFormattedLabel(type), BASE_NAME);
            put(NAME.getFormattedLabel(type), NAME);
            put(UNIT.getFormattedLabel(type), UNIT);
            put(VALUE.getFormattedLabel(type), VALUE);
            put(STRING_VALUE.getFormattedLabel(type), STRING_VALUE);
            put(BOOLEAN_VALUE.getFormattedLabel(type), BOOLEAN_VALUE);
            put(DATA_VALUE.getFormattedLabel(type), DATA_VALUE);
            put(SUM.getFormattedLabel(type), SUM);
            put(TIME.getFormattedLabel(type), TIME);
            put(UPDATE_TIME.getFormattedLabel(type), UPDATE_TIME);
        }};
    }

    public class Pair {

        private T value;

        private Pair(final T value) {
            this.value = value;
        }

        public Label<T> getLabel() {
            return Label.this;
        }

        public T getValue() {
            return value;
        }

    }

}
