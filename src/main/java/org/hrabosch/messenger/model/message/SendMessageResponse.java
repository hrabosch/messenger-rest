package org.hrabosch.messenger.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

import org.hrabosch.messenger.entity.UserMessage;

@Data
@AllArgsConstructor
public class SendMessageResponse {
    private UserMessage message;
    private Date timestamp;
}
