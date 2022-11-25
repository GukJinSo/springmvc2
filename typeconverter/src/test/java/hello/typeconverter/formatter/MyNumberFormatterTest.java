package hello.typeconverter.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Number number = formatter.parse("1,000", Locale.KOREA);
        assertThat(number).isEqualTo(1000L); // 실제로는 Long 타입

    }

    @Test
    void print() {
        String print = formatter.print(1000, Locale.KOREA);
        assertThat(print).isEqualTo("1,000");
    }
}