import com.spreedly.client.models.SpreedlySecureOpaqueString;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpreedlySecureOpaqueStringTest {

    @Test
    public void CanCreateString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        assertNotNull(string);
    }

    @Test
    public void CanClearString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        string.clear();
        assertEquals(0, string.length);
    }

    @Test
    public void CanAppendString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        assertEquals(4, string.length);
    }

    @Test
    public void CanRemoveChar(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        string.removeLastCharacter();
        assertEquals(3, string.length);
    }

    @Test
    public void CanRemoveCharIfEmpty(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.removeLastCharacter();
        assertEquals(0, string.length);
    }

    @Test
    public void CanEncodeString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        JSONObject sampleObject = new JSONObject();
        sampleObject = string.encode(sampleObject, "key");
        assertEquals("{\"key\":\"test\"}", sampleObject.toString());
    }
}
