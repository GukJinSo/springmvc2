package hello.typeconverter.formatter;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIntegerConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {

    @Test
    public void formattingConversionService() throws Exception {
        DefaultFormattingConversionService service = new DefaultFormattingConversionService();

        service.addConverter(new StringToIpPortConverter());
        service.addConverter(new IpPortToStringConverter());

        service.addFormatter(new MyNumberFormatter());

        Long result = service.convert("1,000", Long.class);
        assertThat("1,000").isEqualTo(service.convert(1000, String.class));
        assertThat(1000L).isEqualTo(service.convert("1,000", Number.class));

    }
}
