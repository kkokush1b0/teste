package com.testelemontech.solicitacoes.config;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import javax.xml.datatype.XMLGregorianCalendar;

public class ConversorData {


    public static LocalDateTime convertXMLGregorianCalendarToLocalDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar != null) {
            GregorianCalendar gregorianCalendar = xmlGregorianCalendar.toGregorianCalendar();
            return gregorianCalendar.toZonedDateTime().toLocalDateTime();
        }
        return null;
    }
}
