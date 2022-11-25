package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionServiceTest {

    @Test
    void conversionServiceTest(){
        // 등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());

        // 사용
        Integer intResult = conversionService.convert("10", Integer.class);
        assertThat(intResult).isEqualTo(10);
        String stringResult = conversionService.convert(10, String.class);
        assertThat(stringResult).isEqualTo("10");
        IpPort portResult = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(portResult).isEqualTo(new IpPort("127.0.0.1",8080));
        String stringPort = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(stringPort).isEqualTo("127.0.0.1:8080");

    }
}
