package kata.mockito;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class MockitoTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_verify_some_behaviour() {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void how_about_some_stubbing() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        assertEquals("first", mockedList.get(0));

        //following throws runtime exception
        thrown.expect(RuntimeException.class);
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        assertEquals(null, mockedList.get(999));

        //Although it is possible to verify a stubbed invocation,
        //  usually it's just redundant
        //If your code cares what get(0) returns, then something
        //  else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it
        //  should not be stubbed.
        verify(mockedList).get(0);
    }

    @Test
    public void spying_on_real_objects() {
        List spy = spy(new LinkedList());

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        assertEquals("one", spy.get(0));

        //size() method was stubbed - 100 is printed
        assertEquals(100, spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }
}
