package lemonstream.user;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeObjectField("productList", user.getProductList());
        jsonGenerator.writeFieldName("friends");
        jsonGenerator.writeStartArray();
        for (User friend : user.getFriends()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", friend.getId());
            jsonGenerator.writeStringField("username", friend.getUsername());
            jsonGenerator.writeStringField("name", friend.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
