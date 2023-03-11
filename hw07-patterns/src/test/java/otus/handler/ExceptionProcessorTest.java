package otus.handler;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ExceptionProcessor;
import ru.otus.processor.homework.SecondProvider;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class ExceptionProcessorTest {

    Message message;
    ExceptionProcessor exceptionProcessor;
    SecondProvider secondProvider;


    @BeforeEach
    void prepare(){
        Message message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(new ObjectForMessage())
                .build();
        secondProvider = Mockito.mock(SecondProvider.class);
        exceptionProcessor = new ExceptionProcessor(secondProvider);

    }


    @Test
    public void ExceptionThrownTest(){
        when(secondProvider.provideSecond()).thenReturn(2);
        assertThatThrownBy(() -> exceptionProcessor.process(message)).isInstanceOf(RuntimeException.class)
                .hasMessage("ExceptionProcessor throws exception");
    }

    @Test
    public void ExceptionNotThrownTest(){
        when(secondProvider.provideSecond()).thenReturn(3);
        Assertions.assertEquals(message,exceptionProcessor.process(message));
    }


}
