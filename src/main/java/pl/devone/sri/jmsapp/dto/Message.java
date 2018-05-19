package pl.devone.sri.jmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    public enum Type {
        INFO,
        REQUEST_PIT_STOP,
        WARNING,
        FAILURE
    }
    private Type type;
    private String content;
}
