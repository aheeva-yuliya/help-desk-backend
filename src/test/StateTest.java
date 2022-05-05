import com.javamaster.entity.enums.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void valueOf() {
        var a = State.PROGRESS.getValue();
        assertEquals("IN PROGRESS", a);
    }
}