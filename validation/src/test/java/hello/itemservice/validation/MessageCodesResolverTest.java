package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.validation.*;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    public void messageCodesResolveObject() throws Exception {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
    }

    @Test
    public void messageCodesResolveFields() throws Exception {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
    }

    @Test
    public void bindingResultTest(){
        BindingResult bindingResult = new BeanPropertyBindingResult(new Item("김치찜", Integer.valueOf(50000), Integer.valueOf(6000)), "item");
        bindingResult.addError(new FieldError("item", "price", new Object[]{"스트링으로가격들어옴"}
                , true, new String[]{"required.item.price", "required"}, null, null));
    }
}
