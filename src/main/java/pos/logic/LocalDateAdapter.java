package pos.logic;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate>
{
    // Formato de fecha para serialización y deserialización
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        // Convierte de String a LocalDate
        return LocalDate.parse(v, dateFormatter);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        // Convierte de LocalDate a String
        return v.format(dateFormatter);
    }
}
