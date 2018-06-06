package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.Message;

public interface CustomizedMessageRepository {
    Message storeAndReturnMessage(Message message);
}
