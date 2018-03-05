package lemonstream.product;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ProductSerializer extends StdSerializer<Product> {
    public ProductSerializer() {
        this(null);
    }

    public ProductSerializer(Class<Product> t) {
        super(t);
    }

    @Override
    public void serialize(
            Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", product.getId());
        jsonGenerator.writeStringField("subject", product.getSubject());
        jsonGenerator.writeStringField("description", product.getDescription());
        jsonGenerator.writeNumberField("owner", product.getOwner().getId());
        jsonGenerator.writeBooleanField("published", product.getPublished());
        jsonGenerator.writeObjectField("imageList", product.getImageList());
        jsonGenerator.writeEndObject();
    }
}
