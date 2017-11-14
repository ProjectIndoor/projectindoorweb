package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.SimpleResponse;

public class RestTransmissionServiceImpl implements RestTransmissionService{

    @Override
    public SimpleResponse createSimpleResponse(String content) {
        return new SimpleResponse(content);
    }
}
